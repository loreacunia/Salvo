package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity

public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private int turn;


    @ElementCollection
    @Column(name = "locations")
    private List<String> salvosLocation;

    public Salvo() {

    }

    public Salvo(GamePlayer gamePlayer, int turn, List<String> salvosLocation) {
        this.gamePlayer = gamePlayer;
        this.turn = turn;
        this.salvosLocation = salvosLocation;
    }

    public long getId() {
        return id;
    }

    public GamePlayer getGamePlayers() {
        return gamePlayer;
    }

    public int getTurn() {
        return turn;
    }

    public List<String> getSalvosLocation() {
        return salvosLocation;
    }

    public Map<String, Object> getDto() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("turn", this.turn);
        dto.put("player", getGamePlayers().getPlayer().getId());
        dto.put("salvoLocations", this.salvosLocation);
        return dto;
    }

    public List<String> getSalvoLocations() {
        return salvosLocation;
    }

    public void setTurn(int i) {
        this.turn = turn;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}


