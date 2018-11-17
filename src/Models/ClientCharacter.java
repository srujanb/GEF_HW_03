package Models;

import Interfaces.Collidable;
import Interfaces.ProcRenderable;
import Utils.UniversalConstants;
import processing.core.PApplet;
import java.io.Serializable;

public class ClientCharacter extends GeneralShape implements Serializable, ProcRenderable, Collidable, Cloneable {

    //velocity
    private float vX = 0;
    private float vY = 0;
    //acceleration
    private float aX = 0;
    private float aY = (float) 0.3;

    private transient PApplet pApplet;
    private long clientGUID;
    private int score;

    public ClientCharacter(PApplet pApplet,int x, int y){
        posX = x;
        posY = y;
        w = 20;
        h = 20;

        clr = new int[3];
        clr[0] = 255;
        clr[1] = 204;
        clr[2] = 102;
    }

    private void moveTo(int x, int y){
        posX = x;
        posY = y;
    }

    public void calculateNewPosition(){
        posX += vX;
        posY += vY;

        vX *= 0.9; //friction
        vY += aY;

        if (vX > -0.2 && vX < 0.2) vX = 0;

        if (vY < -1*UniversalConstants.CLIENT_MAX_VELOCITY) vY = -1*UniversalConstants.CLIENT_MAX_VELOCITY;

        if (getLeftBound() < 0) posX = 0;
        if (getRightBound() > UniversalConstants.GAMESCREEN_WIDTH) posX = UniversalConstants.GAMESCREEN_WIDTH - w;
        if (getUpperBound() < 0) posY = 0;
        //Check if it didn't go out of bounds

    }


    @Override
    public PApplet getpApplet() {
        return pApplet;
    }

    @Override
    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public long getClientGUID() {
        return clientGUID;
    }

    public void setClientGUID(long clientGUID) {
        this.clientGUID = clientGUID;
    }

    @Override
    public void draw() {
//        System.out.println("Drawing client: " + clientGUID);
        if (pApplet != null) {
            pApplet.fill(clr[0],clr[1],clr[2]);
            pApplet.rect(posX, posY, w, h, 7);
        } else {
            System.out.println("papplet is null");
        }
    }

    @Override
    public Boolean isCollidingWith(Collidable obj) {
        if (getRightBound() < obj.getLeftBound() || getLeftBound() > obj.getRightBound()) return false;
        if (getLowerBound() < obj.getUpperBound() || getUpperBound() > obj.getLowerBound()) return false;

        return true;
    }

    @Override
    public int getLeftBound() {
        return posX;
    }

    @Override
    public int getRightBound() {
        return posX + w;
    }

    @Override
    public int getUpperBound() {
        return posY;
    }

    @Override
    public int getLowerBound() {
        return posY + h;
    }

    public boolean isHorizontallyCollidingWith(Collidable obj) {
        int vDist = Math.min(Math.abs(this.getUpperBound() - obj.getLowerBound()), Math.abs(this.getLowerBound() - obj.getUpperBound()));
        int hDist = Math.min(Math.abs(this.getLeftBound() - obj.getRightBound()), Math.abs(this.getRightBound() - obj.getLeftBound()));

        return hDist < vDist;
    }

    public boolean isLeftOf(Collidable obj){
        if (this.getLeftBound() < obj.getLeftBound()) return true;
        else return false;
    }

    public boolean isAbove(Collidable obj) {
        if (getLowerBound() < obj.getLowerBound()) return true;
        else return false;
    }

    public void stayLeftOf(Collidable obj){
        this.posX = obj.getLeftBound() - h;
    }

    public void stayRightOf(Collidable obj) {
        this.posX = obj.getRightBound();
    }

    public void sitOnTopOf(Collidable obj) {
        vY = 0;
        posY = obj.getUpperBound() - h;
    }

    public void sitUnder(Collidable obj) {
        vY = 1;
        posY = obj.getLowerBound();
    }

    public void exchangeHorizontalVelocitiesWith(ClientCharacter otherCharacter) {
        float x = this.vX;
        this.vX = otherCharacter.vX;
        otherCharacter.vX = x;
    }

    public void jump() {
        vY -= 7;
    }

    public void goLeft() {
        vX -= 5;
    }

    public void goRight() {
        vX += 5;
    }

    public void goDown() {
        vY = UniversalConstants.CLIENT_MAX_VELOCITY;
    }

    public void respawn() {
        score = 0;
        posX = 250;
        posY = 50;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
