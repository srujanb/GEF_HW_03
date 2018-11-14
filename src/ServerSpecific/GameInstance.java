package ServerSpecific;

import Models.GameObject;
import Models.GameState;
import ServerSpecific.Managers.GameStateManager;
import ServerSpecific.Models.Client;
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
        gameState.initPlatforms();
        gameStateManager = new GameStateManager(currentPappletInstance);
        gameStateManager.setCurrentGameState(gameState);
        System.out.println("GameInstance started with GUID: " + GUID);
    }

    @Override
    public void run() {
        GameLoop();
    }

    private void GameLoop() {
        long currentTime;

        ArrayList<Client> clientsList;
        while (true) {
            currentTime = System.currentTimeMillis();
            System.out.println("Game loop Difference in millis: " + (currentTime - lastLoopTime));
            lastLoopTime = currentTime;
            try {
                drawCurrentState();
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public void drawCurrentState() {
        gameStateManager.drawCurrentState();
    }
}
