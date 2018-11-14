package ClientSpecific.Handlers;

import ClientSpecific.Managers.GameStateManager;
import Models.GameState;
import Utils.ObjectUtil;

import java.io.DataInputStream;

public class InputHandler implements Runnable {

    private final DataInputStream dIn;
    private GameStateManager gameStateManager;

    public InputHandler(DataInputStream dIn) {
        this.dIn = dIn;
        gameStateManager = new GameStateManager();
    }

    @Override
    public void run() {
        listenToInputs();
    }

    private void listenToInputs() {
        while (true){
            try{
                String string = dIn.readUTF();
                Object obj = ObjectUtil.convertFromString(string);
                if (obj instanceof String){
                    System.out.println("From server" + obj);
                } else if (obj instanceof GameState){
                    System.out.println("Setting gameState to gameStateManager");
                    gameStateManager.setCurrentGameState((GameState) obj);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
