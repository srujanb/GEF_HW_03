package Events;

import java.io.Serializable;

public class PanelEvent implements Serializable{

    private int eventType;

    public PanelEvent(int eventType){
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

}
