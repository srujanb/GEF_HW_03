package ServerSpecific;

import Models.GameObject;
import ServerSpecific.Models.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance extends GameObject implements Runnable{

    //Clients with GUID as key and client objects as values.
    private HashMap<Long,Client> clients;
    private Boolean gameStillOn;

    public GameInstance(){
        clients = new HashMap<>();
        System.out.println("GameInstance started with GUID: " + GUID);
    }

    @Override
    public void run() {
        GameLoop();
    }

    private void GameLoop(){
        ArrayList<Client> clientsList;
        while (true) {
            clientsList = getClientList();
            for (Client client : clientsList) {
                try {
                    client.sendObject("<Message from server>");
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList<Client> getClientList(){
        ArrayList<Client> list = new ArrayList<>();
        System.out.println("keyset size: " + clients.keySet().size());
        for (long key: clients.keySet()){
            list.add(clients.get(key));
        }
        return list;
    }

    public void addClient(Client client){
        clients.put(client.getGUID(),client);
    }

    public void removeClient(long GUID){
        clients.remove(GUID);
    }

    public void removeClient(Client client){
        removeClient(client.getGUID());
    }
}
