package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity

public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;
    @Transient
    private List<String> possibleTypes = new LinkedList<String>(Arrays.asList("cruiser", "destroyer", "battleship"));

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column (name="location")
    private Set<String> locations;

    public Ship(){
    }

    public Ship(GamePlayer gamePlayer, String type , Set<String>locations) throws Exception {
        if(this.possibleTypes.contains(type)){
            this.gamePlayer = gamePlayer;
            this.type = type;
            this.locations=locations;
        }
        else {
            throw new Exception();
        }
    }

    public GamePlayer getGamePlayer () {return gamePlayer;}

    public String getType () {return type;}

    public Set<String> getLocations () {return locations;}
}



