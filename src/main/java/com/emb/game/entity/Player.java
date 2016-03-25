package com.emb.game.entity;

import java.util.Objects;
import javax.enterprise.inject.Model;

/**
 *
 * @author bu_000
 */
@Model
public class Player {

    private String mark;
    private String name;
    private int score;
    private int starts;

    public Player(String mark, String name) {
        this.mark = mark;
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public int getStarts() {
        return starts;
    }

    public void setStarts(int starts) {
        this.starts = starts;
    }

    public void incrementStart() {
        starts = starts + 1;
    }

    public void incrementScore() {
        score = score + 1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.mark);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return Objects.equals(this.mark, other.mark);
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder;
        toStringBuilder = new StringBuilder("Player: ");
        toStringBuilder.append("mark=").append(this.mark);
        toStringBuilder.append(", name=").append(this.name);
        toStringBuilder.append(", score=").append(this.score);
        toStringBuilder.append(", starts=").append(this.starts);
        return toStringBuilder.toString();
    }

}
