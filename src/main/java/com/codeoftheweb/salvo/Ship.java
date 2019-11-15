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
    private List<String> possibleTypes = new LinkedList<String>(Arrays.asList("Submarine", "Destroyer", "Patrol Boat", "Carrier", "Battleship"));

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column (name="location")
    private List<String> shipLocation;

    public Ship(){
    }

    public Ship(GamePlayer gamePlayer, String type , List<String>shipLocation) throws Exception {
        if(this.possibleTypes.contains(type)){
            this.gamePlayer = gamePlayer;
            this.type = type;
            this.shipLocation=shipLocation;
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

    public List<String> getShipLocation() {
        return shipLocation;
    }

    public Map<String,Object> getDto(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("shipType", getType());
        dto.put("shipLocation", getShipLocation());
        return dto;
    }


    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}




