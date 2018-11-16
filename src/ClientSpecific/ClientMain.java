package ClientSpecific;

import ClientSpecific.Handlers.ClientPhysicsHandler;
import ClientSpecific.Handlers.InputHandler;
import ClientSpecific.Handlers.OutputHandler;
import ClientSpecific.Managers.GameStateManager;
//import ClientSpecific.Managers.UIManager;
import Events.KeyboardEvent;
import Models.PanelButton;
import Utils.UniversalConstants;
import processing.core.PApplet;
import processing.event.KeyEvent;

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
    private static ClientPhysicsHandler clientPhysicsHandler;
    //    private static UIManager uiManager;
    private static PApplet pApplet;

    public static void main(String args[]) {
        PApplet.main("ClientSpecific.ClientMain");
        try {
            initClient();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        (new Thread(outputHandler)).start();
        (new Thread(inputHandler)).start();
        (new Thread(clientPhysicsHandler)).start();
//        (new Thread(uiManager)).start();
    }

    @Override
    public void settings() {
        size(UniversalConstants.PAPPLET_WIDTH, UniversalConstants.PAPPLET_HEIGHT);
        pApplet = this;
    }

    @Override
    public void setup() {
        frameRate(UniversalConstants.DEFAULT_FRAME_RATE);
    }

    private static void initClient() throws IOException {
        gameStateManager = new GameStateManager(pApplet);
        Socket socket = new Socket(ip, PORT);
        outputHandler = new OutputHandler(new DataOutputStream(socket.getOutputStream()));
        inputHandler = new InputHandler(new DataInputStream(socket.getInputStream()), gameStateManager);
        clientPhysicsHandler = new ClientPhysicsHandler(gameStateManager);
//        uiManager = new UIManager(pApplet,gameStateManager);
    }

    @Override
    public void draw() {
        background(100);
        try {
            gameStateManager.drawCurrentState();
        } catch (Exception e) {
            //TODO remove this printStackTrace.
            e.printStackTrace();
        }
        drawBottomPanel();
    }

    private void drawBottomPanel() {
        pApplet.fill(255, 255, 255);
        pApplet.rect(0,
                UniversalConstants.GAMESCREEN_HEIGHT + 1,
                UniversalConstants.PAPPLET_WIDTH,
                UniversalConstants.PAPPLET_HEIGHT - UniversalConstants.GAMESCREEN_HEIGHT);
        //pause button
        int buttonWidth = 80;
        int buttonHeight = 30;
        int panelTop = UniversalConstants.GAMESCREEN_HEIGHT + 1;
        PanelButton pauseButton = new PanelButton("Pause",
                pApplet,
                50,
                panelTop + 30,
                buttonWidth,
                buttonHeight);
        pauseButton.draw();
    }


    @Override
    public void keyPressed(KeyEvent event) {
//        super.keyPressed(event);
        System.out.println("Some event");
        if (key == CODED) {
            if (keyCode == UP) {
                try {
                    outputHandler.sendObject(new KeyboardEvent(UniversalConstants.EVENT_KB_JUMP));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyCode == LEFT) {
                try {
                    outputHandler.sendObject(new KeyboardEvent(UniversalConstants.EVENT_KB_LEFT));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyCode == RIGHT) {
                try {
                    outputHandler.sendObject(new KeyboardEvent(UniversalConstants.EVENT_KB_RIGHT));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyCode == DOWN) {
                try {
                    outputHandler.sendObject(new KeyboardEvent(UniversalConstants.EVENT_KB_DOWN));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
