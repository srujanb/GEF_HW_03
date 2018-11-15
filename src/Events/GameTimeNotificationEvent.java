package Events;

import Models.GameObject;

import java.io.Serializable;

public class GameTimeNotificationEvent extends GameObject implements Serializable{

    private long gameTime;

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }
}
