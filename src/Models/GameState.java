package Models;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState implements Serializable{

    //Remember, if you add anything here, you also need to add it in GameStateManager.
    private HashMap<Long,Platform> platforms;
    private HashMap<Long,ClientCharacter> clientCharacters = new HashMap<>();
    private boolean hasUpdates = false;

    public ArrayList<Platform> getPlatforms() {
        ArrayList<Platform> platformsList = new ArrayList<>();
        for (long key: platforms.keySet()){
            platformsList.add(platforms.get(key));
        }
        return platformsList;
    }

    public void setPlatforms(ArrayList<Platform> platformsList) {
        if (null == platforms) platforms = new HashMap<>();
        else platforms.clear();

        for (Platform platform: platformsList){
            platforms.put(platform.getGUID(),platform);
        }

    }

    public void addClient(ClientCharacter clientCharacter){
        clientCharacters.put(clientCharacter.getClientGUID(),clientCharacter);
    }

    public ArrayList<ClientCharacter> getClientCharacters() {
        ArrayList<ClientCharacter> clientCharactersList = new ArrayList<>();
        for (long key: clientCharacters.keySet()){
            clientCharactersList.add(clientCharacters.get(key));
        }
        return clientCharactersList;
    }

    public void updatePlatform(Platform platform){
        platforms.put(platform.getGUID(),platform);
    }

    public void updateClientCharacter(ClientCharacter clientCharacter){
        clientCharacters.put(clientCharacter.getGUID(),clientCharacter);
    }

    public void characterJump(long GUID){
        clientCharacters.get(GUID).jump();
    }

    public void characterLeft(long GUID){
        clientCharacters.get(GUID).goLeft();
    }
    public void characterRight(long GUID){
        clientCharacters.get(GUID).goRight();
    }
    public void characterDown(long GUID){
        clientCharacters.get(GUID).goDown();
    }

    public Boolean hasUpdates(){
        return hasUpdates;
    }

    public void setHasUpdates(boolean b) {
        this.hasUpdates = b;
    }
}
