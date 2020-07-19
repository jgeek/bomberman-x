package ir.ac.kntu.data;

import java.io.Serializable;

public class User implements Serializable {

    private static int ID_GENERATOR = 1;
    private int id;
    private String name;
    private int score;
    private int games;
    private int victories;
    private int defeats;

    public User(String name) {
        this.name = name;
        id = ID_GENERATOR++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }
}
