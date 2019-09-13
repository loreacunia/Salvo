package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date joinTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")//Agregar columna con este nombre
    private Game game;

    @OneToMany (mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();


    @OneToMany (mappedBy =  "gamePlayers", fetch = FetchType.EAGER)
    Set <Salvo> Salvos = new HashSet<>();
    public GamePlayer() {
    }

    public GamePlayer(Player player, Game game) {
        this.joinTime = new Date();
        this.player = player;
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public Date getjoinTime() {
        return joinTime;
    }

    @JsonIgnore
    public Player getPlayer() {
        return player;
    }
    @JsonIgnore
    public Game getGame() {
        return game;
    }


    public Set<Ship> getShips() {
        return ships;
    }


    public Set<Salvo> getSalvos() {
        return Salvos;
    }

    public Map<String, Object> getDto() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",getId());
        dto.put("player", getPlayer().getPlayerDto());
        return dto;
    }
}
