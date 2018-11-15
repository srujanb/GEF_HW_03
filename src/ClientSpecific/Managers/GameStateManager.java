package ClientSpecific.Managers;

import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ClientSpecific file.
public class GameStateManager {

    private GameState currentGameState;
    private PApplet pApplet;

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
        this.currentGameState = currentGameState;
    }

    public void drawCurrentState(){
        if (currentGameState == null) return;
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.draw();
        }
    }

    public void calculateNextState() {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.calculateNewPosition();
        }
    }
}
