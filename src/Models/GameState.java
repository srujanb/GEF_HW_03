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

    public void initPlatforms() {
        platforms = new ArrayList<>();

        Platform platform;

        platform = new Platform(null, 50,50,50,20);
        platform.setClr(0,255,0);
        platforms.add(platform);

        platform = new Platform(null, 50,100,50,20);
        platform.setClr(0,255,0);
        platforms.add(platform);

        platform = new Platform(null, 50,150,50,20);
        platform.setClr(0,255,0);
        platforms.add(platform);

    }
}
