package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date gameTime = new Date();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    public Game() { }

    public Game(int seconds) {
        if (seconds >= 0 ) {
            Date newDate = new Date();
            this.gameTime = Date.from(newDate.toInstant().plusSeconds(seconds));
        } else {
            this.gameTime = new Date();
        }
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return gameTime;
    }

    public  Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Map<String, Object> getDto() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", getId());
        dto.put("created", getDate().getTime());
        dto.put("gamePlayers", getGamePlayers().stream().map(gamePlayer -> gamePlayer.getDto()));
        return dto;
    }
    }

