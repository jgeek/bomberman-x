package ir.ac.kntu.data;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static int ID_GENERATOR = 1;
    private int id;
    private String name;
    private int score;
    private int games;
    private int victories;
    private int defeats;
    private int draws;

    public User(String name) {
        this.name = name;
//        id = ID_GENERATOR++;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void incVictories() {
        this.victories += 1;
    }

    public void incDefeats() {
        this.defeats += 1;
    }

    public void incDraws() {
        this.draws += 1;
    }

    public void incGames() {
        this.games += 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setDefeats(int defeats) {


        this.defeats = defeats;
    }
}
