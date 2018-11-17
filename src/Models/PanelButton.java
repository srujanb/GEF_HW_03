package Models;

import Interfaces.ProcRenderable;
import processing.core.PApplet;

public class PanelButton {

    String buttonText;
    PApplet pApplet;
    int x;    // top left corner x position
    int y;    // top left corner y position
    int w;    // width of button
    int h;    // height of button

    public PanelButton(String text, PApplet pApplet, int xpos, int ypos, int widthB, int heightB) {
        buttonText = text;
        this.pApplet = pApplet;
        x = xpos;
        y = ypos;
        w = widthB;
        h = heightB;
    }

    public void draw() {
        pApplet.textSize(12);
        pApplet.fill(218);
        pApplet.rect(x, y, w, h, 10);
        pApplet.textAlign(pApplet.CENTER, pApplet.CENTER);
        pApplet.fill(0);
        pApplet.text(buttonText, x + (w / 2), y + (h / 2));
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        if (mouseX > x && mouseX < (x + w) && mouseY > y && mouseY < (y + h)) {
            return true;
        }
        return false;
    }

}
