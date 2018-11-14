package ClientSpecific.Managers;

import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ClientSpecific file.
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

    public static void setCurrentGameState(GameState currentGameState) {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.setpApplet(pApplet);
        }
        GameStateManager.currentGameState = currentGameState;
    }

    public void drawCurrentState(){
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.draw();
        }
    }
}
