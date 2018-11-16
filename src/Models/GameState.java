package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable{

    //Remember, if you add anything here, you also need to add it in GameStateManager.
    private ArrayList<Platform> platforms;
    private ArrayList<ClientCharacter> clientCharacters = new ArrayList<>();

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<Platform> platforms) {
        this.platforms = platforms;
    }

    public void addClient(ClientCharacter clientCharacter){
        clientCharacters.add(clientCharacter);
    }

    public ArrayList<ClientCharacter> getClientCharacters() {
        return clientCharacters;
    }

    public void updatePlatform(Platform platform){

    }
}
