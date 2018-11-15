package Models;

import Utils.UniversalConstants;
import processing.core.PApplet;

import java.io.Serializable;

public class Platform extends GeneralShape implements Serializable{

    //velocity
    private int vX = 0;
    private int vY = 0;
    //acceleration
    private int aX = 0;
    private int aY = 0;

    //initial position values
    private int ipX = 0;
    private int ipY = 0;

    //other properties
    private Boolean shouldOscillate = false;
    private int oscillationRange = UniversalConstants.platformOscRange;

    private transient PApplet pApplet;



    public Platform(PApplet pApplet, int x, int y, int w, int h){
        this.pApplet = pApplet;
        posX = x;
        ipX = x;
        posY = y;
        ipY = y;
        this.w = w;
        this.h = h;
    }

    public void moveTo(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public int getvX() {
        return vX;
    }

    public void setvX(int vX) {
        this.vX = vX;
    }

    public int getvY() {
        return vY;
    }

    public void setvY(int vY) {
        this.vY = vY;
    }

    public int getaX() {
        return aX;
    }

    public void setaX(int aX) {
        this.aX = aX;
    }

    public int getaY() {
        return aY;
    }

    public void setaY(int aY) {
        this.aY = aY;
    }

    public PApplet getpApplet() {
        return pApplet;
    }

    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    public int getOscillationRange() {
        return oscillationRange;
    }

    public void setOscillationRange(int oscillationRange) {
        this.oscillationRange = oscillationRange;
    }

    public Boolean getShouldOscillate() {
        return shouldOscillate;
    }

    public void setShouldOscillate(Boolean shouldOscillate) {
        this.shouldOscillate = shouldOscillate;
    }

    public void calculateNewPosition(){
        if (shouldOscillate){
            changeDirIfOutOfOscRange();
        }
        posX = posX + vX;
        posY = posY + vY;
    }

    //change the direction if platform exceeds oscillation range
    private void changeDirIfOutOfOscRange() {
        if (posX > ipX + oscillationRange && vX > 0){
            vX = -vX;
        } else if (posX < ipX - oscillationRange && vX < 0) {
            vX = -vX;
        }

        if (posY > ipY + oscillationRange && vY > 0){
            vY = -vY;
        } else if (posY < ipY - oscillationRange && vY < 0) {
            vY = -vY;
        }
    }

    public void draw(){
        if (pApplet != null) {
            pApplet.fill(clr[0],clr[1],clr[2]);
            pApplet.rect(posX, posY, w, h);
        } else {
            System.out.println("papplet is null");
        }
    }
}
