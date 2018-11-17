package Events;

import java.io.Serializable;

public class SpeedChangeEvent implements Serializable{

    private float newSpeed;

    public SpeedChangeEvent(float newSpeed){
        this.newSpeed = newSpeed;
    }

    public float getNewSpeed() {
        return newSpeed;
    }

    public void setNewSpeed(float newSpeed) {
        this.newSpeed = newSpeed;
    }
}
