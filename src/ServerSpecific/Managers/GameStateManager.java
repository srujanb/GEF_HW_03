package ServerSpecific.Managers;

import Events.KeyboardEvent;
import Events.PanelEvent;
import Models.ClientCharacter;
import Models.GameState;
import Models.Platform;
import ServerSpecific.Timeline;
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

        handleKeyboardEvents();
        handlePanelEvents();

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
                if (clientCharacter.getUpperBound() > UniversalConstants.GAMESCREEN_HEIGHT){
                    currentGameState.setHasUpdates(true);
                    clientCharacter.respawn();
                }
                //add in the previousClientCharactersList
                previousClientCharacter.add(clientCharacter);
            }
        }
    }

    private void handlePanelEvents() {
        PanelEvent panelEvent = eventsManager.getPanelEvent();
        if (null == panelEvent) return;
        if (UniversalConstants.BUTTON_PAUSE == panelEvent.getEventType()){
            Timeline.setSpeed((float) 0);
        } else if (UniversalConstants.BUTTON_PLAY == panelEvent.getEventType()){
            Timeline.setSpeed(1);
        } else if (UniversalConstants.BUTTON_RECORD_START == panelEvent.getEventType()){
            System.out.println("RECORDING");
        } else if (UniversalConstants.BUTTON_RECORD_STOP == panelEvent.getEventType()){
            System.out.println("RECORDING STOPPED");
        } else if (UniversalConstants.BUTTON_REPLAY == panelEvent.getEventType()){
            System.out.println("REPLAY");
        }
        eventsManager.resetPanelEvent();
    }

    private void handleKeyboardEvents() {
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
        currentGameState.setHasUpdates(true);
    }

    public void setEventsManager(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }
}
