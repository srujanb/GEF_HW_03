package Events;

import Models.ClientCharacter;
import Models.Platform;

import java.util.HashMap;

public class GameObjectUpdateEvent {

    private long currentgameTime;
    private HashMap<Long, Platform> platforms;
    private HashMap<Long, ClientCharacter> clientCharacters;

    public long getCurrentgameTime() {
        return currentgameTime;
    }

    public void setCurrentgameTime(long currentgameTime) {
        this.currentgameTime = currentgameTime;
    }

    public HashMap<Long, Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(HashMap<Long, Platform> platforms) {
        this.platforms = platforms;
    }

    public HashMap<Long, ClientCharacter> getClientCharacters() {
        return clientCharacters;
    }

    public void setClientCharacters(HashMap<Long, ClientCharacter> clientCharacters) {
        this.clientCharacters = clientCharacters;
    }
}
