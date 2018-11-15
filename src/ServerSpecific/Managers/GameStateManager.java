package ServerSpecific.Managers;

import Models.ClientCharacter;
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
        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
//            System.out.println("platforms size: " + platforms.size());
            for (Platform platform : platforms) {
                platform.calculateNewPosition();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
//            System.out.println("clientCharacter size: " + clientCharacters.size());
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.calculateNewPosition();
            }
        }
    }

    public void drawCurrentState() {
        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            for (Platform platform : platforms) {
                platform.draw();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.draw();
            }
        }
    }

    public void addClientToGame(ClientCharacter clientCharacter) {
        clientCharacter.setpApplet(pApplet);
        currentGameState.addClient(clientCharacter);
    }

}
