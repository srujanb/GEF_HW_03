package ServerSpecific.Managers;

import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ServerSpecific file.
public class GameStateManager {

    private GameState currentGameState;
    private PApplet pApplet;

    public GameStateManager(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        System.out.println("Setting current game state. ");
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        System.out.println("Platforms size: " + platforms.size());
        for (Platform platform : platforms) {
            platform.setpApplet(pApplet);
        }
        this.currentGameState = currentGameState;
    }

    public PApplet getpApplet() {
        return pApplet;
    }

    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public void calculateNextState() {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.calculateNewPosition();
        }
    }

    public void drawCurrentState() {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform : platforms) {
            platform.draw();
        }
    }
}
