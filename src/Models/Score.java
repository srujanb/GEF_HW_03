package Models;

import Interfaces.ProcRenderable;
import processing.core.PApplet;

import java.io.Serializable;

public class Score extends GeneralShape implements ProcRenderable, Serializable {

    private transient PApplet pApplet;
    private String text;

    public Score(PApplet pApplet){
        this.pApplet = pApplet;
        posX = 250;
        posY = 30;
        setScore(0);

        clr = new int[3];
        clr[0] = 204;
        clr[1] = 255;
        clr[2] = 153;
    }

    public void setScore(int score){
        text = "Your score = " + score;
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
        pApplet.textSize(20);
        pApplet.fill(clr[0],clr[1],clr[2]);
        pApplet.text(text, posX, posY);
    }
}
