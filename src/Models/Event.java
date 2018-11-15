package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Event extends GameObject implements Serializable{

    long gameTime;
    ArrayList<Platform> affectedPlatforms;
//    ArrayList<GeneralShape> affectedCharacters;
//    ArrayList<GeneralShape> affectedFood;
    GameState newGameState;

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public ArrayList<Platform> getAffectedPlatforms() {
        return affectedPlatforms;
    }

    public void addAffectedPlatform(Platform platform){
        if (null == affectedPlatforms) affectedPlatforms = new ArrayList<>();
        affectedPlatforms.add(platform);
    }

    public GameState getNewGameState() {
        return newGameState;
    }

    public void setNewGameState(GameState newGameState) {
        this.newGameState = newGameState;
    }
}
