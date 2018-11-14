package Models;

import java.io.Serializable;

public class GeneralShape extends GameObject implements Serializable {

    int posX;
    int posY;
    int w;
    int h;
    int[] clr;

    public GeneralShape(){
        clr = new int[3];
        clr[0] = 50;
        clr[1] = 50;
        clr[2] = 50;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[] getClr() {
        return clr;
    }

    public void setClr(int r, int g, int b) {
        int[] clr = new int[3];
        clr[0] = r;
        clr[1] = g;
        clr[2] = b;
        this.clr = clr;
    }

}
