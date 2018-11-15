package Events;

import Models.GameObject;
import Models.GameState;

import java.io.Serializable;

public class InitialGameData extends GameObject implements Serializable{

    private long currentGameTime;
    private GameState gameState;

    public long getCurrentGameTime() {
        return currentGameTime;
    }

    public void setCurrentGameTime(long currentGameTime) {
        this.currentGameTime = currentGameTime;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
