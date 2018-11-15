package ClientSpecific.Handlers;

import ClientSpecific.Managers.GameStateManager;
import ClientSpecific.Timeline;


public class ClientPhysicsHandler implements Runnable{

    private final GameStateManager gameStateManager;

    public ClientPhysicsHandler(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void run() {
        applyPhysics();
    }

    private void applyPhysics() {
        while (true) {
            try {
                long ticks = Timeline.getLocalGameLagAndReset();
                System.out.println("ticks = " + ticks);
                while (ticks-- > 0){
                    gameStateManager.calculateNextState();
                }
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
