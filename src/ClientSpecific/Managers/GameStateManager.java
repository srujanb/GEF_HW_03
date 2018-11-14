package ClientSpecific.Managers;

import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

public class GameStateManager {

    private static GameState currentGameState;
    private static PApplet pApplet;

    public GameStateManager(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public GameStateManager() { }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.setpApplet(pApplet);
        }
        GameStateManager.currentGameState = currentGameState;
    }
}
