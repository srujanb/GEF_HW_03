package Models;

import java.util.HashMap;

public class GameRecording {

    public static final int RECORDING = 428;
    public static final int STOPPED = 465;
    public static final int REPLAYING = 107;

    public static int currentState;
    private long recordingStartTime;
    private long recordingStopTime;

    private HashMap<Long,GameState> gameStateMap = new HashMap<>();

    public void addGameState(Long time,GameState gameState){
        gameStateMap.put(time,gameState);
    }

    public Boolean hasGameState(long key){
        if (gameStateMap.containsKey(key)) return true;
        else return false;
    }

    public GameState getGameState(long time){
        return gameStateMap.get(time);
    }
    public long getRecordingStartTime() {
        return recordingStartTime;
    }

    public void setRecordingStartTime(long recordingStartTime) {
        this.recordingStartTime = recordingStartTime;
    }

    public long getRecordingStopTime() {
        return recordingStopTime;
    }

    public void setRecordingStopTime(long recordingStopTime) {
        this.recordingStopTime = recordingStopTime;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public HashMap<Long, GameState> getGameStateMap() {
        return gameStateMap;
    }
}
