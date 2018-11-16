package ServerSpecific.Managers;

import Events.KeyboardEvent;
import Models.ClientCharacter;
import Models.GameState;
import Models.Platform;
import Utils.UniversalConstants;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ServerSpecific file.
public class GameStateManager {

    private GameState currentGameState;
    private PApplet pApplet;
    private EventsManager eventsManager;

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

        handleEvents();

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            for (Platform platform : platforms) {
                platform.calculateNewPosition();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            ArrayList<ClientCharacter> previousClientCharacter = new ArrayList<>();
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.calculateNewPosition();
                //Check client platform collision.
                for (Platform platform: platforms){
                    if (clientCharacter.isCollidingWith(platform)){
                        if (clientCharacter.isAbove(platform)){
                            clientCharacter.sitOnTopOf(platform);
                        } else {
                            clientCharacter.sitUnder(platform);
                        }
                    }
                }
                for (ClientCharacter otherCharacter: previousClientCharacter){
                    if (clientCharacter.isCollidingWith(otherCharacter)){
                        if (clientCharacter.isHorizontallyCollidingWith(otherCharacter)){
                            System.out.println("character collision: Found Hosizontal collision." );
                            if (clientCharacter.isLeftOf(otherCharacter)){
                                clientCharacter.stayLeftOf(otherCharacter);
                            } else {
                                clientCharacter.stayRightOf(otherCharacter);
                            }
                            clientCharacter.exchangeHorizontalVelocitiesWith(otherCharacter);
                        } else if (clientCharacter.isAbove(otherCharacter)){
                            clientCharacter.sitOnTopOf(otherCharacter);
                        } else {
                            otherCharacter.sitOnTopOf(clientCharacter);
                        }
                    }
                }
                //add in the previousClientCharactersList
                previousClientCharacter.add(clientCharacter);
            }
        }
    }

    private void handleEvents() {
        ArrayList<KeyboardEvent> events = eventsManager.getKeyboardEvents();
        if (events.size() > 0) currentGameState.setHasUpdates(true);
        for (KeyboardEvent event: events){
            if (event.getEventType() == UniversalConstants.EVENT_KB_JUMP){
                currentGameState.characterJump(event.getClientGUID());
            } else if (event.getEventType() == UniversalConstants.EVENT_KB_LEFT){
                currentGameState.characterLeft(event.getClientGUID());
            } else if (event.getEventType() == UniversalConstants.EVENT_KB_RIGHT){
                currentGameState.characterRight(event.getClientGUID());
            } else if (event.getEventType() == UniversalConstants.EVENT_KB_DOWN){
                currentGameState.characterDown(event.getClientGUID());
            }
        }
        eventsManager.clearKeyboardEvents();
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

    public void setEventsManager(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }
}
