package Models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState implements Serializable,Cloneable{

    //Remember, if you add anything here, you also need to add it in GameStateManager and also to the clone block below.
    private long gameTime;
    private HashMap<Long,Platform> platforms;
    private HashMap<Long,ClientCharacter> clientCharacters = new HashMap<>();
    private FoodItem foodItem;
    private Score scoreView;
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

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public void updatePlatform(Platform platform){
        platforms.put(platform.getGUID(),platform);
    }

    public void updateClientCharacter(ClientCharacter clientCharacter){
        clientCharacters.put(clientCharacter.getGUID(),clientCharacter);
    }

    public void characterJump(long GUID){
        try {
            clientCharacters.get(GUID).jump();
        } catch (Exception e){
            e.printStackTrace();
        }
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

    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
        Gson gson = new Gson();
        GameState gameState = gson.fromJson(gson.toJson(this), GameState.class);
        return gameState;
    }

    public Score getScoreView() {
        return scoreView;
    }

    public void setScoreView(Score scoreView) {
        this.scoreView = scoreView;
    }

    public void setScore(int score) {
        this.scoreView.setScore(score);
    }

    public void removeCharacterWithClientGUID(long clientGUID) {
        hasUpdates = true;
        clientCharacters.remove(clientGUID);
    }
}
