package ClientSpecific;

import ClientSpecific.Handlers.InputHandler;
import ClientSpecific.Handlers.OutputHandler;
import Models.GameState;
import processing.core.PApplet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientMain {

    private static final String ip = "127.0.0.1";
    private static final int PORT = 9754;
    private static OutputHandler outputHandler;
    private static InputHandler inputHandler;
    private static final GameState gameState = new GameState();

    public static void main(String args[]){
        try{
            initClient();
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        (new Thread(outputHandler)).start();
        (new Thread(inputHandler)).start();

    }

    private static void initClient() throws IOException {
        Socket socket = new Socket(ip,PORT);
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        outputHandler = new OutputHandler(dOut);
        inputHandler = new InputHandler(dIn);
    }

}
