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

    private float score;

    private Date finishDate;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "game_id")
    private Player player;

    public Score() {

    }

    public Score (float score, Game game, Player player){
        this.score = score;
        this.game = game;
        this.player = player;
    }

}
