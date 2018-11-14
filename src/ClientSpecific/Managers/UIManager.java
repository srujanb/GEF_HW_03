package ClientSpecific.Managers;

import processing.core.PApplet;

public class UIManager implements Runnable{

    private PApplet pApplet;
    private final GameStateManager gameStateManager;


    public UIManager(PApplet pApplet, GameStateManager gameStateManager){
        this.pApplet = pApplet;
        this.gameStateManager = gameStateManager;
    }


    @Override
    public void run() {
        displayLoop();
    }

    private void displayLoop() {
        while (true){
            try {
                gameStateManager.drawCurrentState();
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
