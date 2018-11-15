package ServerSpecific;

import Models.GameObject;
import Models.GameState;
import Models.Platform;
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
    private long lastLoopTime = 0;

    public GameInstance(PApplet currentPappletInstance) {
        this.currentPappletInstance = currentPappletInstance;
        clients = new HashMap<>();
        generateInitialGameState();
    }

    private void generateInitialGameState() {
        GameState gameState = new GameState();
        initPlatforms(gameState);
        gameStateManager = new GameStateManager(currentPappletInstance);
        gameStateManager.setCurrentGameState(gameState);
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
                System.out.println("Ticks occured: " + ticks);
                for (int i = 0; i < ticks; i++) {
                    calculateNextState();
                }
                sendCurrentGameStateToClients();
            } catch (Exception e) {
                System.out.println("");
                e.printStackTrace();
            }
        }
    }

    private void sendCurrentGameStateToClients() {
        for (long key : clients.keySet()){
            Client client = clients.get(key);
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
        System.out.println("keyset size: " + clients.keySet().size());
        for (long key : clients.keySet()) {
            list.add(clients.get(key));
        }
        return list;
    }

    public void addClient(Client client) {
        clients.put(client.getGUID(), client);
        sendInitialGameStateToClient(client);
    }

    private void sendInitialGameStateToClient(Client client) {
        System.out.println("Sending initial gameState to client.");
        try {
            client.sendObject(gameStateManager.getCurrentGameState());
            System.out.println("initial gameState sent.");
        } catch (IOException e) {
            e.printStackTrace();
            sendInitialGameStateToClient(client);
        }
    }

    public void removeClient(long GUID) {
        clients.remove(GUID);
    }

    public void removeClient(Client client) {
        removeClient(client.getGUID());
    }

    private void calculateNextState() {
        gameStateManager.calculateNextState();
    }

    public void drawCurrentState() {
        gameStateManager.drawCurrentState();
    }

    private void initPlatforms(GameState gameState) {
        ArrayList<Platform> platforms = new ArrayList<>();

        Platform platform;

        //green platforms
        platform = new Platform(null,
                (int) (UniversalConstants.PAPPLET_WIDTH * 0.1),
                (int) (UniversalConstants.PAPPLET_HEIGHT * 0.8),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        platform = new Platform(null,
                (int) (UniversalConstants.PAPPLET_WIDTH * 0.5 - UniversalConstants.PLANK_WIDTH / 2),
                (int) (UniversalConstants.PAPPLET_HEIGHT * 0.5),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setvY(1);
        platform.setShouldOscillate(true);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        platform = new Platform(null,
                (int) (UniversalConstants.PAPPLET_WIDTH - UniversalConstants.PLANK_WIDTH - UniversalConstants.PAPPLET_WIDTH * 0.1),
                (int) (UniversalConstants.PAPPLET_HEIGHT * 0.8),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 255, 0);
        platforms.add(platform);

        //blue platforms
        platform = new Platform(null,
                0,
                (int) (UniversalConstants.PAPPLET_HEIGHT * 0.2),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 0, 255);
        platforms.add(platform);

        platform = new Platform(null,
                UniversalConstants.PAPPLET_WIDTH - UniversalConstants.PLANK_WIDTH,
                (int) (UniversalConstants.PAPPLET_HEIGHT * 0.2),
                UniversalConstants.PLANK_WIDTH,
                UniversalConstants.PLANK_HEIGHT);
        platform.setClr(0, 0, 255);
        platforms.add(platform);

        gameState.setPlatforms(platforms);
    }
}
