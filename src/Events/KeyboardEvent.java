package Events;

import Models.GameObject;

import java.io.Serializable;

public class KeyboardEvent extends GameObject implements Serializable {

    private int eventType;
    private long clientGUID;

    public KeyboardEvent(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public long getClientGUID() {
        return clientGUID;
    }

    public void setClientGUID(long clientGUID) {
        this.clientGUID = clientGUID;
    }
}
