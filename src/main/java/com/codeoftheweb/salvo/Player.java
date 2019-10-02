package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;

    private String userName;

    private String name;

    private String password;


    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> score;

    public Player() {
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Set<Score> getScore() {
        return score;
    }

    public String toString() {
        return userName;
    }


    public Set<Score> getWon() {
        return getScore().stream().filter(score -> score.getScore() == 1).collect(Collectors.toSet());
    }


    public Set<Score> getLost(){
        return getScore().stream().filter(score -> score.getScore() == 0).collect(Collectors.toSet());
    }


    public Set<Score> getTies(){
        return getScore().stream().filter(score -> score.getScore() == 0.5).collect(Collectors.toSet());
    }


    public double getTotal() {
        return  getWon().size() + getTies().size() * 0.5;
    }

    public Map<String, Object> getPlayerDto() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", getId());
        dto.put("email", getUserName());
        return dto;
    }

    public Map<String, Object> getLeaderBoardDto() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", getId());
        dto.put("email", getUserName());
        dto.put("games", getScore().size());
        dto.put("total", getTotal());
        dto.put("won", getWon().size());
        dto.put("ties", getTies().size());
        dto.put("lost", getLost().size());
        return dto;
    }


}

