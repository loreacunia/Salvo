package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity

public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;
    @Transient
    private List<String> possibleTypes = new LinkedList<String>(Arrays.asList("Submarine", "Destroyer", "Patrol Boat"));

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column (name="location")
    private Set<String> Shiplocation;

    public Ship(){
    }

    public Ship(GamePlayer gamePlayer, String type , Set<String>locations) throws Exception {
        if(this.possibleTypes.contains(type)){
            this.gamePlayer = gamePlayer;
            this.type = type;
            this.Shiplocation=locations;
        }
        else {
            throw new Exception("Ship type not found");
        }
    }

    public GamePlayer getGamePlayer () {return gamePlayer;}

    public String getType () {return type;}

    public List<String> getPossibleTypes() {
        return possibleTypes;
    }

    public Set<String> getLocations() {
        return Shiplocation;
    }

    public Map<String,Object> getDto(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("shipType", getType());
        dto.put("shipLocations", getLocations());
        return dto;
    }

}




