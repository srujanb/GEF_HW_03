package ClientSpecific;

import ClientSpecific.Handlers.InputHandler;
import ClientSpecific.Handlers.OutputHandler;
import ClientSpecific.Managers.GameStateManager;
//import ClientSpecific.Managers.UIManager;
import Utils.UniversalConstants;
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
//    private static UIManager uiManager;
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
//        (new Thread(uiManager)).start();
    }

    @Override
    public void settings() {
        size(UniversalConstants.pappletWidth,UniversalConstants.pappletHeight);
        pApplet = this;
    }

    @Override
    public void setup() {
    }

    private static void initClient() throws IOException {
        gameStateManager = new GameStateManager(pApplet);
        Socket socket = new Socket(ip,PORT);
        outputHandler = new OutputHandler(new DataOutputStream(socket.getOutputStream()));
        inputHandler = new InputHandler(new DataInputStream(socket.getInputStream()),gameStateManager);
//        uiManager = new UIManager(pApplet,gameStateManager);
    }

    @Override
    public void draw() {
        background(100);
        try {
            gameStateManager.drawCurrentState();
            Thread.sleep(30);
        } catch (Exception e){
            //TODO remove this printStackTrace.
            e.printStackTrace();
        }
    }
}
