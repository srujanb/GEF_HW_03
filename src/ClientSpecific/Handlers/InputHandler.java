package ClientSpecific.Handlers;

import ClientSpecific.Managers.GameStateManager;
import ClientSpecific.Timeline;
import Events.GameTimeNotificationEvent;
import Events.GameStateUpdateEvent;
import Models.GameState;
import Utils.ObjectUtil;

import java.io.DataInputStream;

public class InputHandler implements Runnable {

    private final DataInputStream dIn;
    private GameStateManager gameStateManager;

    public InputHandler(DataInputStream dIn, GameStateManager gameStateManager) {
        this.dIn = dIn;
        this.gameStateManager = gameStateManager;
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
                } else if (obj instanceof GameStateUpdateEvent){
                    System.out.println("Received initial game data");
                    GameStateUpdateEvent gameStateUpdateEvent = (GameStateUpdateEvent) obj;
                    Timeline.setLatestServerGameTime(gameStateUpdateEvent.getCurrentGameTime());
                    Timeline.setLatestLocalGameTime(gameStateUpdateEvent.getCurrentGameTime());
                    gameStateManager.setCurrentGameState(gameStateUpdateEvent.getGameState());
                } else if (obj instanceof GameTimeNotificationEvent){
                    GameTimeNotificationEvent gameTimeNotificationEvent = (GameTimeNotificationEvent) obj;
                    Timeline.setLatestServerGameTime(gameTimeNotificationEvent.getGameTime());
                } else if (obj instanceof GameState){
                    gameStateManager.setCurrentGameState((GameState) obj);
                }
            } catch (Exception e){
                //TODO remove this later.
                e.printStackTrace();
            }
        }
    }
}
