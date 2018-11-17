package ServerSpecific;

import Events.GameTimeNotificationEvent;
import Events.GameStateUpdateEvent;
import Events.KeyboardEvent;
import Events.PanelEvent;
import Models.*;
import ServerSpecific.Managers.EventsManager;
import ServerSpecific.Managers.GameStateManager;
import ServerSpecific.Models.Client;
import Utils.UniversalConstants;
import processing.core.PApplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance extends GameObject implements Runnable {

    private final PApplet currentPappletInstance;
    //Clients with GUID as key and client objects as values.
    private HashMap<Long, Client> clients;
    private GameStateManager gameStateManager;
    private EventsManager eventsManager;
    private long lastLoopTime = 0;

    public GameInstance(PApplet currentPappletInstance) {
        this.currentPappletInstance = currentPappletInstance;
        clients = new HashMap<>();
        generateInitialGameState();
    }

    private void generateInitialGameState() {
        eventsManager = new EventsManager();
        GameState gameState = new GameState();
        initPlatforms(gameState);
        gameStateManager = new GameStateManager(currentPappletInstance);
        gameStateManager.setCurrentGameState(gameState);
        gameStateManager.setEventsManager(eventsManager);
        System.out.println("GameInstance started with GUID: " + GUID);
    }

    @Override
    public void run() {
        GameLoop();
    }

    private void GameLoop() {
        ArrayList<Client> clientsList;
        while (true) {
            try {
                int ticks = Timeline.getNextTick();
                while (ticks-- > 0){
                    calculateNextState(false);
                    sendCurrentGameTimeToAllClients();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (gameStateManager.getCurrentGameState().hasUpdates()){
                sendCurrentGameStateToClients();
                gameStateManager.getCurrentGameState().setHasUpdates(false);
            }
        }
    }

    private void sendCurrentGameTimeToAllClients() {
        GameTimeNotificationEvent gameTimeNotificationEventEvent = new GameTimeNotificationEvent();
        gameTimeNotificationEventEvent.setGameTime(Timeline.getServerGameTimeTicks());
        ArrayList<Client> clients = getClientList();
        for (Client client: clients){
            try {
                client.sendObject(gameTimeNotificationEventEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendCurrentGameStateToClients() {
        ArrayList<Client> clients = getClientList();
        for (Client client: clients){
            try {
                client.sendObject(gameStateManager.getCurrentGameState());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearBackground() {
        currentPappletInstance.background(100);
    }

    private ArrayList<Client> getClientList() {
        ArrayList<Client> list = new ArrayList<>();
        for (long key : clients.keySet()) {
            list.add(clients.get(key));
        }
        return list;
    }

    public void addClient(Client client) {
        clients.put(client.getGUID(), client);
        generateCharacterForClient(client);
        sendInitialGameStateToClient(client);
    }

    private void generateCharacterForClient(Client client) {
        ClientCharacter clientCharacter = new ClientCharacter(currentPappletInstance,250,50);
        clientCharacter.setClientGUID(client.getGUID());
        gameStateManager.addClientToGame(clientCharacter);
    }

    private void sendInitialGameStateToClient(Client client) {
        System.out.println("Sending initial gameState to client.");
        try {
            GameStateUpdateEvent gameStateUpdateEvent = new GameStateUpdateEvent();
            gameStateUpdateEvent.setCurrentGameTime(Timeline.getServerGameTimeTicks());
            gameStateUpdateEvent.setGameState(gameStateManager.getCurrentGameState());
            client.sendObject(gameStateUpdateEvent);
            System.out.println("initial gameState sent.");
        } catch (IOException e) {
            e.printStackTrace();
            sendInitialGameStateToClient(client);
        }
    }

    public void keyboardInputEvent(KeyboardEvent event){
        eventsManager.addKeyboardEvent(event);
    }

    public void panelEvent(PanelEvent panelEvent) {
        System.out.println("GameInstance - Got new event");
        eventsManager.setPanelEvent(panelEvent);
    }

    public void removeClient(long GUID) {
        clients.remove(GUID);
    }

    public void removeClient(Client client) {
        removeClient(client.getGUID());
    }

    private void calculateNextState(Boolean justHandleEvents) {
        gameStateManager.calculateNextState(justHandleEvents);
    }

    public void drawCurrentState() {
        gameStateManager.drawCurrentState();
    }

    private void initPlatforms(GameState gameState) {
        ArrayList<Platform> platforms = new ArrayList<>();

        Platform platform;

        //green platforms
        platform = new Platform(null,
                (int) (UniversalConstants.GAMESCREEN_WIDTH * 0.1),
                (int) (UniversalConstants.GAMESCREEN_HEIGHT * 0.8),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        platform = new Platform(null,
                (int) (UniversalConstants.GAMESCREEN_WIDTH * 0.5 - UniversalConstants.PLANK_WIDTH / 2),
                (int) (UniversalConstants.GAMESCREEN_HEIGHT * 0.5),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setvY(2);
        platform.setShouldOscillate(true);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        platform = new Platform(null,
                (int) (UniversalConstants.GAMESCREEN_WIDTH - UniversalConstants.PLANK_WIDTH - UniversalConstants.GAMESCREEN_WIDTH * 0.1),
                (int) (UniversalConstants.GAMESCREEN_HEIGHT * 0.8),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        //blue platforms
        platform = new Platform(null,
                0,
                (int) (UniversalConstants.GAMESCREEN_HEIGHT * 0.2),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setvX(-1);
        platform.setShouldOscillate(true);
        platform.setClr(0, 0, 255);
        platforms.add(platform);

        platform = new Platform(null,
                UniversalConstants.GAMESCREEN_WIDTH - UniversalConstants.PLANK_WIDTH,
                (int) (UniversalConstants.GAMESCREEN_HEIGHT * 0.2),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setvX(1);
        platform.setShouldOscillate(true);
        platform.setClr(0, 0, 255);
        platforms.add(platform);

        gameState.setPlatforms(platforms);
    }

}
