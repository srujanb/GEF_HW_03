package ClientSpecific.Managers;

import Models.ClientCharacter;
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

        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            System.out.println("Platforms: " + platforms.size());
            for (Platform platform : platforms) {
                platform.setpApplet(pApplet);
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            System.out.println("clientCharacters: " + clientCharacters.size());
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.setpApplet(pApplet);
            }
        }


        this.currentGameState = currentGameState;
    }

    public void drawCurrentState(){
        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
//            System.out.println("Platforms: " + platforms.size());
            for (Platform platform : platforms) {
                platform.draw();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
//            System.out.println("clientCharacters: " + clientCharacters.size());
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.draw();
            }
        }
    }

    public void calculateNextState() {
        System.out.println("Calculate next state called");
        if (null == currentGameState) return;
        System.out.println("Current game state is not null");

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            for (Platform platform : platforms) {
                platform.calculateNewPosition();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.calculateNewPosition();
            }
        }

    }

}
