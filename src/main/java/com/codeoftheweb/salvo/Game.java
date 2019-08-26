package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;
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

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }
}
