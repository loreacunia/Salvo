package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private double score;

    private Date finishDate;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "player_id")
    private Player player;

    public Score() {

    }

    public Score (double score, Game game, Player player){
        this.score = score;
        this.game = game;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

}
