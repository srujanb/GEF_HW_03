package ServerSpecific.Handlers;

import ServerSpecific.Models.Client;
import Utils.ObjectUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Client client;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;

    public ClientHandler(Client client) {
        this.client = client;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        outputHandler = new OutputHandler(client);
        outputHandler.start();
        inputHandler = new InputHandler(client);
        inputHandler.start();
    }

    public void run() {
        super.run();
    }

    public void sendObject(Object object) throws IOException {
        outputHandler.sendObject(object);
    }
}
