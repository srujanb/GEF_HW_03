package ServerSpecific.Managers;

import Events.KeyboardEvent;
import Events.PanelEvent;
import Events.ScoreUpdateEvent;
import Models.ClientCharacter;
import Models.GameRecording;
import Models.GameState;
import Models.Platform;
import ServerSpecific.Models.Client;
import ServerSpecific.Timeline;
import Utils.UniversalConstants;
import processing.core.PApplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//This is ServerSpecific file.
public class GameStateManager {

    private GameState currentGameState;
    private PApplet pApplet;
    private EventsManager eventsManager;
    private GameRecording gameRecording;
    private HashMap<Long,Client> clientMap = new HashMap<>();

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

    public void calculateNextState(Boolean justHandleEvents) {
        if (null == currentGameState) return;

        if (gameRecording != null && gameRecording.getCurrentState() == GameRecording.REPLAYING && Timeline.getServerGameTimeTicks() >= gameRecording.getRecordingStopTime()){
            gameRecording.setCurrentState(GameRecording.STOPPED);
            Timeline.setSpeed(0);
        }

        if (gameRecording != null && gameRecording.hasGameState(Timeline.getServerGameTimeTicks())){
            try {
                currentGameState = (GameState) gameRecording.getGameState(Timeline.getServerGameTimeTicks()).clone();
                sendScoreToClients();
//                printTempGameInfo();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            System.out.println("GameStateManager: There is a state for time: " + Timeline.getServerGameTimeTicks());
            return;
        }
        currentGameState.setGameTime(Timeline.getServerGameTimeTicks());

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
                    sendScoreToClientWithCharacter(clientCharacter);
                }
                if (clientCharacter.isCollidingWith(currentGameState.getFoodItem())){
                    currentGameState.getFoodItem().respawn();
                    clientCharacter.ateFood();
                    sendScoreToClientWithCharacter(clientCharacter);
                }
                //add in the previousClientCharactersList
                previousClientCharacter.add(clientCharacter);
            }
        }
    }

    private void sendScoreToClients() {
        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        for (ClientCharacter clientCharacter: clientCharacters){
            sendScoreToClientWithCharacter(clientCharacter);
        }
    }

    private void sendScoreToClientWithCharacter(ClientCharacter clientCharacter) {
        long clientGUID = clientCharacter.getClientGUID();
        Client client = clientMap.get(clientGUID);
        try {
            client.sendObject(new ScoreUpdateEvent(clientCharacter.getScore()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void printTempGameInfo() {
//        System.out.println("GameStateManager: currentGameState time: " + Timeline.getServerGameTimeTicks() + " , " + currentGameState.getGameTime() );
//        System.out.println("GameStateManager: Char PosX" + currentGameState.getClientCharacters().get(0).getPosX());
//    }
//
//    private void printTempHashmapInfo() {
//        System.out.println("GameStateManager: Printing all game states");
//        HashMap<Long,GameState> mymap = gameRecording.getGameStateMap();
//        for (Long key: mymap.keySet()){
//            GameState gameState = mymap.get(key);
//            System.out.println("vX: " + gameState.getClientCharacters().get(0).getPosX());
//        }
//    }

    public void handlePanelEvents() {
        PanelEvent panelEvent = eventsManager.getPanelEvent();
        if (null == panelEvent) return;
        if (UniversalConstants.BUTTON_PAUSE == panelEvent.getEventType()){
            Timeline.setSpeed((float) 0);
        } else if (UniversalConstants.BUTTON_PLAY == panelEvent.getEventType()){
            System.out.println("GameStateManager PLAY CALLED");
            if (gameRecording != null && gameRecording.getCurrentState() == GameRecording.STOPPED) gameRecording = null;
            Timeline.setSpeed(1);
        } else if (UniversalConstants.BUTTON_RECORD_START == panelEvent.getEventType()){
            if (gameRecording == null) {
                gameRecording = new GameRecording();
                gameRecording.setCurrentState(GameRecording.RECORDING);
                gameRecording.setRecordingStartTime(Timeline.getServerGameTimeTicks());
                recordCurrentGameStateIfApplicable();
            }
        } else if (UniversalConstants.BUTTON_RECORD_STOP == panelEvent.getEventType()){
            if (gameRecording != null) {
                gameRecording.setCurrentState(GameRecording.STOPPED);
                gameRecording.setRecordingStopTime(Timeline.getServerGameTimeTicks());
                recordCurrentGameStateIfApplicable();
                Timeline.setSpeed(0);
//                printTempHashmapInfo();
            }
        } else if (UniversalConstants.BUTTON_REPLAY == panelEvent.getEventType()){
            System.out.println("GameStateManager replay called");
            if (gameRecording != null) {
                gameRecording.setCurrentState(GameRecording.REPLAYING);
                System.out.println("GameStateManager replay called not returned");
                Timeline.setServerGameTimeTicks(gameRecording.getRecordingStartTime());
                Timeline.setSpeed(1);
            }
        } else if (UniversalConstants.BUTTON_SPEED_01 == panelEvent.getEventType()){
            changeSpeed(0.25);
        } else if (UniversalConstants.BUTTON_SPEED_02 == panelEvent.getEventType()){
            changeSpeed(0.5);
        } else if (UniversalConstants.BUTTON_SPEED_03 == panelEvent.getEventType()){
            changeSpeed(1);
        } else if (UniversalConstants.BUTTON_SPEED_04 == panelEvent.getEventType()){
            changeSpeed(1.5);
        } else if (UniversalConstants.BUTTON_SPEED_05 == panelEvent.getEventType()){
            changeSpeed(2);
        }
        eventsManager.resetPanelEvent();
    }

    private void changeSpeed(double speed) {
        if (gameRecording == null) return;
        Timeline.setSpeed((float) speed);
    }

    private void handleKeyboardEvents() {
        if (!(gameRecording == null || gameRecording.getCurrentState() == GameRecording.RECORDING)) return;
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
        if (events.size() > 0) recordCurrentGameStateIfApplicable();
        eventsManager.clearKeyboardEvents();
    }

    private void recordCurrentGameStateIfApplicable() {
        System.out.println("GameStateManager recordCurrentGameStateIfApplicable called");
        if (gameRecording != null){
            if (gameRecording.getCurrentState() == GameRecording.RECORDING){
                System.out.println("recording now..");
                try {
                    gameRecording.addGameState(Timeline.getServerGameTimeTicks(), (GameState) currentGameState.clone());
//                    printTempGameInfo();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void drawCurrentState() {
        if (null == currentGameState) return;

//        System.out.println("GameStateManager draw method: " + currentGameState.getGameTime());

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

        currentGameState.getFoodItem().draw();
    }

    public void addClientToGame(Client client, ClientCharacter clientCharacter) {
        clientMap.put(client.getGUID(),client);
        clientCharacter.setpApplet(pApplet);
        currentGameState.addClient(clientCharacter);
        currentGameState.setHasUpdates(true);
    }

    public void setEventsManager(EventsManager eventsManager) {
        this.eventsManager = eventsManager;
    }

    public void removeClientCharacter(long clientGUID) {
        currentGameState.removeCharacterWithClientGUID(clientGUID);
    }
}
