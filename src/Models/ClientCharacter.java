package Models;

import Interfaces.Collidable;
import Interfaces.ProcRenderable;
import Utils.UniversalConstants;
import processing.core.PApplet;
import java.io.Serializable;

public class ClientCharacter extends GeneralShape implements Serializable, ProcRenderable, Collidable {

    //velocity
    private float vX = 0;
    private float vY = 0;
    //acceleration
    private float aX = 0;
    private float aY = (float) 0.3;

    private transient PApplet pApplet;
    private long clientGUID;

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

        vX += aX;
        vY += aY;
        System.out.println("new vY:" + vY);

        if (vX > UniversalConstants.CLIENT_MAX_VELOCITY) vX = UniversalConstants.CLIENT_MAX_VELOCITY;
        else if (vX < -1*UniversalConstants.CLIENT_MAX_VELOCITY) vX = -1*UniversalConstants.CLIENT_MAX_VELOCITY;
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

    public boolean isAbove(Collidable obj) {
        if (getLowerBound() < obj.getLowerBound()) return true;
        else return false;
    }

    public void sitOnTopOf(Collidable obj) {
        System.out.println("sitOnTopOf");
        vY = 0;
        posY = obj.getUpperBound() - h;
    }

    public void sitUnder(Collidable obj) {
        System.out.println("sitUnder");

        vY = 0;
        posY = obj.getLowerBound();
    }

    public void jump() {
        vY -= 10;
    }
}
