package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;


    public Player() { }

    public Player(String email) {
        this.userName = email;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }


    public String toString() {
        return userName;
    }

    public Map<String, Object> getDto() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",getId());
        dto.put("email",getUserName());
        return dto;
    }
}

