package ServerSpecific.Models;

import Models.GameObject;
import ServerSpecific.Handlers.ClientHandler;

import java.io.IOException;
import java.net.Socket;

public class Client extends GameObject{

    private Socket socket;
    private ClientHandler clientHandler;

    public Client(){ }

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

}
