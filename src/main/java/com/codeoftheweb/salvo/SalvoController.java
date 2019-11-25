package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping ("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ShipRepository shipRepository;


    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map<String, Object> dto = new HashMap<>();
        if (Guest(authentication)) {
            dto.put("player", "guest");
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.getPlayerDto());
        }
        dto.put("games", gameRepository.findAll()
                .stream()
                .map(Game::getDto)
                .collect(toList()));
        return dto;
    }

    private boolean Guest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {

        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.BAD_REQUEST);
        } else if (playerRepository.findByUserName(username) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Player player = new Player(username, password);
        playerRepository.save(player);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/game_view/{id}")
    public Map<String, Object> getGameView(@PathVariable long id) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        Map<String, Object> dto = gamePlayer.getGame().getDto();
        dto.put("ships", getShipList(gamePlayer.getShips()));
        Set<GamePlayer> gamePlayers = gamePlayer.getGame().getGamePlayers();
        Set<Salvo> salvos = gamePlayers.stream()
                .flatMap(gp -> gp.getSalvos()
                        .stream())
                .collect(toSet());
        dto.put("salvos", salvos.stream().map(salvo -> salvo.getDto()));
        return dto;
    }

    // CREAR JUEGO

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        Player player = playerRepository.findByUserName(authentication.getName());
        Map<String, Object> dto = new LinkedHashMap<>();

        if (Guest(authentication)) {
            return new ResponseEntity<>(MakeMap("error", "No player logged in"), HttpStatus.FORBIDDEN);
        }
        else {
            Game game = gameRepository.save(new Game(0));
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game));
            return new ResponseEntity<>(MakeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);

        }







    }


    //Unirse al juego

    @RequestMapping(path = "/games/{id}/players", method = RequestMethod.POST)

    public ResponseEntity<Map<String, Object>> joinGame (Authentication authentication, @PathVariable long id) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            return new ResponseEntity<>(MakeMap("error", "That game doesn't exist"), HttpStatus.FORBIDDEN);
        }

        if (Guest(authentication)) {
            return new ResponseEntity<>(MakeMap("error", "No player logged in"), HttpStatus.FORBIDDEN);
        }

        if  (game.getGamePlayers().stream().count()>1){
            return new ResponseEntity<>(MakeMap("error", "Game full"), HttpStatus.FORBIDDEN);
        }

        if (game.getGamePlayers().stream().map(gamePlayer -> gamePlayer.getPlayer().getUserName()).collect(Collectors.toList()).contains(authentication.getName())){
            return new ResponseEntity<>(MakeMap("error", "You can't join your game"),HttpStatus.FORBIDDEN);
        }

        Player player = playerRepository.findByUserName(authentication.getName());
        GamePlayer gamePlayer = new GamePlayer(player, game);

        gamePlayerRepository.save(gamePlayer);

        return new ResponseEntity<>(MakeMap("gpId", gamePlayer.getId()), HttpStatus.ACCEPTED);
    }



    @RequestMapping("/leaderboard")
    public List<Map<String,Object>> getPLayers() {
        return playerRepository.findAll()
                .stream()
                .map(Player::getLeaderBoardDto)
                .collect(toList());
    }



    private List<Map<String, Object>> getShipList(Set<Ship> ships) {
        return ships
                .stream()
                .map(ship -> ship.getDto())
                .collect(toList());
    }

    public Map<String,Object> MakeMap (String key, Object value){
        Map<String, Object> crearMapa = new LinkedHashMap<>();
        crearMapa.put (key, value);
        return crearMapa;
    }

    //ships

    @RequestMapping("/games/players/{gpId}/ships")
    public ResponseEntity<Map<String,Object>> addShip(@PathVariable long gpId, Authentication authentication, @RequestBody List<Ship> ships) {


        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("Error", "You are a guest"), HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByUserName(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);

        if (player == null) {

            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer == null) {

            return new ResponseEntity<>(makeMap("error", "Unauthorized"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getPlayer().getId() != player.getId()) {

            return new ResponseEntity<>(makeMap("error", "Player doesn't exist"), HttpStatus.FORBIDDEN);
        }

        if (!gamePlayer.getShips().isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "Unauthorized, already have ships"), HttpStatus.UNAUTHORIZED);
        }

        ships.forEach(ship -> {
            ship.setGamePlayer(gamePlayer);
            shipRepository.save(ship);
        });

        return new ResponseEntity<>(makeMap("OK", "Ship created"), HttpStatus.CREATED);
    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}



