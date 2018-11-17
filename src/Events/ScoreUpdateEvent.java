package Events;

import Models.GameObject;

import java.io.Serializable;

public class ScoreUpdateEvent extends GameObject implements Serializable {

    private int newScore;

    public ScoreUpdateEvent(int score){
        newScore = score;
    }

    public int getNewScore() {
        return newScore;
    }

    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }
}
