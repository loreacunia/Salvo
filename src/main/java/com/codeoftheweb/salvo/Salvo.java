package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
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
    private GamePlayer gamePlayers;

    private int turn;


    @ElementCollection
    @Column(name = "locations")
    private Set<String> salvosLocation;

    public Salvo() {

    }

    public Salvo(GamePlayer gamePlayers, int turn, Set<String> salvosLocation) {
        this.gamePlayers = gamePlayers;
        this.turn = turn;
        this.salvosLocation = salvosLocation;
    }

    public long getId() {
        return id;
    }

    public GamePlayer getGamePlayers() {
        return gamePlayers;
    }

    public int getTurn() {
        return turn;
    }

    public Set<String> getSalvosLocation() {
        return salvosLocation;
    }

    public Map<String, Object> getDto() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("turn", this.turn);
        dto.put("player", getGamePlayers().getPlayer().getId());
        dto.put("salvoLocations", this.salvosLocation);
        return dto;
    }
}


