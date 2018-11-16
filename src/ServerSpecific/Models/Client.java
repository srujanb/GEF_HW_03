package ServerSpecific.Models;

import Models.ClientCharacter;
import Models.GameObject;
import ServerSpecific.GameInstance;
import ServerSpecific.Handlers.ClientHandler;

import java.io.IOException;
import java.net.Socket;

public class Client extends GameObject{

    private final GameInstance gameInstance;
    private Socket socket;
    private ClientHandler clientHandler;

    public Client(GameInstance gameInstance){
        this.gameInstance = gameInstance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        clientHandler = new ClientHandler(this);
        (new Thread(clientHandler)).start();
    }

    public void sendObject(Object object) throws IOException {
        clientHandler.sendObject(object);
    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }
}
