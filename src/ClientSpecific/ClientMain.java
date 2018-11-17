package ClientSpecific;

import ClientSpecific.Handlers.ClientPhysicsHandler;
import ClientSpecific.Handlers.InputHandler;
import ClientSpecific.Handlers.OutputHandler;
import ClientSpecific.Managers.GameStateManager;
//import ClientSpecific.Managers.UIManager;
import Events.KeyboardEvent;
import Events.PanelEvent;
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
    private static int mouseOver;
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
        drawBottomPanel(mouseX,mouseY);
    }

    @Override
    public void mousePressed() {
        try {
            if (mouseOver == UniversalConstants.BUTTON_PAUSE) {
                System.out.println("BUTTON_PAUSE");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_PAUSE));
            } else if (mouseOver == UniversalConstants.BUTTON_PLAY) {
                System.out.println("BUTTON_PLAY");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_PLAY));
            } else if (mouseOver == UniversalConstants.BUTTON_RECORD_START) {
                System.out.println("BUTTON_REC_START");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_RECORD_START));
            } else if (mouseOver == UniversalConstants.BUTTON_RECORD_STOP) {
                System.out.println("BUTTON_REC_STOP");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_RECORD_STOP));
            } else if (mouseOver == UniversalConstants.BUTTON_REPLAY) {
                System.out.println("BUTTON_REPLAY");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_REPLAY));
            } else if (mouseOver == UniversalConstants.BUTTON_SPEED_01) {
                System.out.println("BUTTON_SPEED");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_SPEED_01));
            } else if (mouseOver == UniversalConstants.BUTTON_SPEED_02) {
                System.out.println("BUTTON_SPEED");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_SPEED_02));
            } else if (mouseOver == UniversalConstants.BUTTON_SPEED_03) {
                System.out.println("BUTTON_SPEED");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_SPEED_03));
            } else if (mouseOver == UniversalConstants.BUTTON_SPEED_04) {
                System.out.println("BUTTON_SPEED");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_SPEED_04));
            } else if (mouseOver == UniversalConstants.BUTTON_SPEED_05) {
                System.out.println("BUTTON_SPEED");
                outputHandler.sendObject(new PanelEvent(UniversalConstants.BUTTON_SPEED_05));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void drawBottomPanel(int mouseX, int mouseY) {
        pApplet.fill(255, 255, 255);
        pApplet.rect(0,
                UniversalConstants.GAMESCREEN_HEIGHT + 1,
                UniversalConstants.PAPPLET_WIDTH,
                UniversalConstants.PAPPLET_HEIGHT - UniversalConstants.GAMESCREEN_HEIGHT);
        int buttonWidth = 80;
        int buttonHeight = 30;
        int panelTop = UniversalConstants.GAMESCREEN_HEIGHT + 1;
        //pause button
        PanelButton pauseButton = new PanelButton("Pause",
                pApplet,
                10,
                panelTop + 20,
                buttonWidth,
                buttonHeight);
        pauseButton.draw();
        //play button
        PanelButton playButton = new PanelButton("Play",
                pApplet,
                110,
                panelTop + 20,
                buttonWidth,
                buttonHeight);
        playButton.draw();
        //start record button
        PanelButton startRecordButton = new PanelButton("Start Record",
                pApplet,
                210,
                panelTop + 20,
                buttonWidth,
                buttonHeight);
        startRecordButton.draw();
        //stop record button
        PanelButton stopRecordButton = new PanelButton("stop Record",
                pApplet,
                310,
                panelTop + 20,
                buttonWidth,
                buttonHeight);
        stopRecordButton.draw();
        //replay button
        PanelButton replayButton = new PanelButton("Replay",
                pApplet,
                410,
                panelTop + 20,
                buttonWidth,
                buttonHeight);
        replayButton.draw();

        //speed buttons
        PanelButton speedButton1 = new PanelButton("0.25x",
                pApplet,
                10,
                panelTop + 60,
                buttonWidth,
                buttonHeight);
        speedButton1.draw();

        PanelButton speedButton2 = new PanelButton("0.50x",
                pApplet,
                110,
                panelTop + 60,
                buttonWidth,
                buttonHeight);
        speedButton2.draw();

        PanelButton speedButton3 = new PanelButton("1.00x",
                pApplet,
                210,
                panelTop + 60,
                buttonWidth,
                buttonHeight);
        speedButton3.draw();

        PanelButton speedButton4 = new PanelButton("1.50x",
                pApplet,
                310,
                panelTop + 60,
                buttonWidth,
                buttonHeight);
        speedButton4.draw();

        PanelButton speedButton5 = new PanelButton("2.00x",
                pApplet,
                410,
                panelTop + 60,
                buttonWidth,
                buttonHeight);
        speedButton5.draw();


        if (pauseButton.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_PAUSE;
        } else if (playButton.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_PLAY;
        } else if (startRecordButton.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_RECORD_START;
        } else if (stopRecordButton.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_RECORD_STOP;
        } else if (replayButton.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_REPLAY;
        } else if (speedButton1.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_SPEED_01;
        } else if (speedButton2.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_SPEED_02;
        } else if (speedButton3.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_SPEED_03;
        } else if (speedButton4.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_SPEED_04;
        } else if (speedButton5.isMouseOver(mouseX,mouseY)){
            mouseOver = UniversalConstants.BUTTON_SPEED_05;
        } else {
            mouseOver = UniversalConstants.BUTTON_NONE;
        }


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
