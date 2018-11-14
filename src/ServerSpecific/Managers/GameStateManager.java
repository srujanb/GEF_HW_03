package ServerSpecific.Managers;

import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ServerSpecific file.
public class GameStateManager {

    private static GameState currentGameState;
    private static PApplet pApplet;

    public GameStateManager(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public static void setCurrentGameState(GameState currentGameState) {
        System.out.println("Setting current game state. ");
        if (pApplet == null) {
            System.out.println("Yes papplet is null");
        } else {
            System.out.println("No papplet is not null");
        }
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        System.out.println("Platforms size: " + platforms.size());
        for (Platform platform : platforms) {
            platform.setpApplet(pApplet);
        }
        GameStateManager.currentGameState = currentGameState;
    }

    public void drawCurrentState() {
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform : platforms) {
            platform.draw();
        }
    }
}
