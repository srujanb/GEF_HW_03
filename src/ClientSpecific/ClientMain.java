package ClientSpecific;

import ClientSpecific.Handlers.InputHandler;
import ClientSpecific.Handlers.OutputHandler;
import ClientSpecific.Managers.GameStateManager;
import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientMain extends PApplet {

    private static final String ip = "127.0.0.1";
    private static final int PORT = 9754;
    private static OutputHandler outputHandler;
    private static InputHandler inputHandler;
    private static GameStateManager gameStateManager;
    private static PApplet pApplet;

    public static void main(String args[]){
        PApplet.main("ClientSpecific.ClientMain");
        try{
            initClient();
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        (new Thread(outputHandler)).start();
        (new Thread(inputHandler)).start();

    }

    @Override
    public void settings() {
        size(500,500);
        pApplet = this;
    }

    @Override
    public void setup() {
    }

    private static void initClient() throws IOException {
        Socket socket = new Socket(ip,PORT);
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        outputHandler = new OutputHandler(dOut);
        inputHandler = new InputHandler(dIn);

        gameStateManager = new GameStateManager(pApplet);
    }

    @Override
    public void draw() {
        try {
            gameStateManager.drawCurrentState();
        } catch (Exception e){
            //TODO remove this printStackTrace.
            e.printStackTrace();
        }
    }
}
