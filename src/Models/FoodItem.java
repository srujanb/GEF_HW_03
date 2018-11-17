package Models;

import Interfaces.Collidable;
import Interfaces.ProcRenderable;
import ServerSpecific.ServerMain;
import Utils.UniversalConstants;
import processing.core.PApplet;

import java.io.Serializable;

public class FoodItem extends GeneralShape implements Serializable, ProcRenderable, Collidable{


    private int iR = 5;
    private int oR = 15;
    private transient PApplet pApplet;

    public FoodItem(PApplet pApplet){
        this.pApplet = pApplet;
        setNewPosition();
    }

    private void setNewPosition() {
        posX = (int) pApplet.random(UniversalConstants.GAMESCREEN_WIDTH);
        posY = (int) pApplet.random(UniversalConstants.GAMESCREEN_HEIGHT);

        clr = new int[3];
        clr[0] = 255;
        clr[1] = 255;
        clr[2] = 0;
    }

    public void respawn(){
        setNewPosition();
    }

    void star(float x, float y, float radius1, float radius2, int npoints) {
        float angle = pApplet.TWO_PI / npoints;
        float halfAngle = (float) (angle/2.0);
        pApplet.beginShape();
        for (float a = 0; a < pApplet.TWO_PI; a += angle) {
            float sx = x + pApplet.cos(a) * radius2;
            float sy = y + pApplet.sin(a) * radius2;
            pApplet.vertex(sx, sy);
            sx = x + pApplet.cos(a+halfAngle) * radius1;
            sy = y + pApplet.sin(a+halfAngle) * radius1;
            pApplet.vertex(sx, sy);
        }
        pApplet.endShape(pApplet.CLOSE);
    }

    @Override
    public PApplet getpApplet() {
        return pApplet;
    }

    @Override
    public void setpApplet(PApplet pApplet) {
        this.pApplet = pApplet;
    }

    @Override
    public void draw() {
        if (pApplet != null) {
            pApplet.fill(clr[0],clr[1],clr[2]);
            pApplet.pushMatrix();
            pApplet.translate(posX, posY);
            pApplet.rotate((float) (30 / -100.0));
            star(0, 0, iR, oR, 5);
            pApplet.popMatrix();
        } else {
            pApplet = ServerMain.getPapplet();
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
        return posX - oR;
    }

    @Override
    public int getRightBound() {
        return posX + oR;
    }

    @Override
    public int getUpperBound() {
        return posY - oR;
    }

    @Override
    public int getLowerBound() {
        return posY + oR;
    }
}
