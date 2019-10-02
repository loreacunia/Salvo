package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    @RequestMapping("/games")
    public Map<String, Object> getGames(Authentication authentication) {
        Map <String, Object> dto = new LinkedHashMap<>();
        if (isGuest(authentication)){
            dto.put("player", "Guest");
        }else{
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.getPlayerDto());
        }
                dto.put("games", gameRepository.findAll()
                .stream()
                .map(Game -> Game.getDto())
                .collect(toList()));
                return dto;

    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
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
    @RequestMapping("/leaderboard")
    public List<Map<String, Object>> getPLayers() {
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
}

