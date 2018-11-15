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
        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        for (Platform platform: platforms){
            platform.setpApplet(pApplet);
        }
        this.currentGameState = currentGameState;
    }

    public void drawCurrentState(){
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

    public void calculateNextState() {
        if (null == currentGameState) return;

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

    public void addClientToGame(ClientCharacter clientCharacter) {
        currentGameState.addClient(clientCharacter);
    }
}
