package ClientSpecific.Managers;

import Events.GameObjectUpdateEvent;
import Models.ClientCharacter;
import Models.GameState;
import Models.Platform;
import processing.core.PApplet;

import java.util.ArrayList;

//This is ClientSpecific file.
public class GameStateManager {

    private GameState currentGameState;
    private PApplet pApplet;

    private int myScore;

    public GameStateManager(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public GameStateManager() { }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {

        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            System.out.println("Platforms: " + platforms.size());
            for (Platform platform : platforms) {
                platform.setpApplet(pApplet);
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            System.out.println("clientCharacters: " + clientCharacters.size());
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.setpApplet(pApplet);
            }
        }

        currentGameState.getFoodItem().setpApplet(pApplet);
        currentGameState.getScoreView().setpApplet(pApplet);
        currentGameState.getScoreView().setScore(myScore);


        this.currentGameState = currentGameState;
    }

    public void drawCurrentState(){
        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            for (Platform platform : platforms) {
                platform.draw();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.draw();
            }
        }

        currentGameState.getFoodItem().draw();
        currentGameState.getScoreView().draw();
    }

    public void updatePartialGameState(GameObjectUpdateEvent obj) {
        calculateNextState();
        GameObjectUpdateEvent gameObjectUpdateEvent = obj;
        if (null != obj.getPlatforms()){
            for (long key: obj.getPlatforms().keySet()){
                Platform platform = obj.getPlatforms().get(key);
                currentGameState.updatePlatform(platform);
            }
        }
        if (null != obj.getClientCharacters()){
            for (long key: obj.getClientCharacters().keySet()){
                ClientCharacter clientCharacter = obj.getClientCharacters().get(key);
                currentGameState.updateClientCharacter(clientCharacter);
            }
        }
    }

    public void calculateNextState() {
        if (null == currentGameState) return;

        ArrayList<Platform> platforms = currentGameState.getPlatforms();
        if (null != platforms) {
            for (Platform platform : platforms) {
                platform.calculateNewPosition();
            }
        }

        ArrayList<ClientCharacter> clientCharacters = currentGameState.getClientCharacters();
        if (null != clientCharacters) {
            ArrayList<ClientCharacter> previousClientCharacter = new ArrayList<>();
            for (ClientCharacter clientCharacter: clientCharacters){
                clientCharacter.calculateNewPosition();
                //Check client platform collision.
                for (Platform platform: platforms){
                    if (clientCharacter.isCollidingWith(platform)){
                        if (clientCharacter.isAbove(platform)){
                            clientCharacter.sitOnTopOf(platform);
                        } else {
                            clientCharacter.sitUnder(platform);
                        }
                    }
                }
                for (ClientCharacter otherCharacter: previousClientCharacter){
                    if (clientCharacter.isCollidingWith(otherCharacter)){
                        if (clientCharacter.isHorizontallyCollidingWith(otherCharacter)){
                            System.out.println("character collision: Found Hosizontal collision." );
                            if (clientCharacter.isLeftOf(otherCharacter)){
                                clientCharacter.stayLeftOf(otherCharacter);
                            } else {
                                clientCharacter.stayRightOf(otherCharacter);
                            }
                            clientCharacter.exchangeHorizontalVelocitiesWith(otherCharacter);
                        } else if (clientCharacter.isAbove(otherCharacter)){
                            clientCharacter.sitOnTopOf(otherCharacter);
                        } else {
                            otherCharacter.sitOnTopOf(clientCharacter);
                        }
                    }
                }
                //add in the previousClientCharactersList
                previousClientCharacter.add(clientCharacter);
            }
        }

    }

    public void setScore(int score) {
        myScore = score;
        currentGameState.setScore(score);
    }
}
