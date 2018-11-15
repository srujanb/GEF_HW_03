package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable{

    //Remember, if you add anything here, you also need to add it in GameStateManager.
    ArrayList<Platform> platforms;

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<Platform> platforms) {
        this.platforms = platforms;
    }

}
