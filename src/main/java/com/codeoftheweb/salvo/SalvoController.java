package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @RequestMapping("/games")
    public List<Map<String, Object>> getGames() {
        return gameRepository.findAll()
                .stream()
                .map(Game -> Game.getDto())
                .collect(toList());
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
    private List<Map<String, Object>> getShipList(Set<Ship> ships) {
        return ships
                .stream()
                .map(ship -> ship.getDto())
                .collect(toList());
    }
}

