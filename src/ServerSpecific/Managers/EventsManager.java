package ServerSpecific.Managers;

import Events.KeyboardEvent;

import java.util.ArrayList;

public class EventsManager {

    private ArrayList<KeyboardEvent> keyboardEvents = new ArrayList<>();

    public ArrayList<KeyboardEvent> getKeyboardEvents() {
        return keyboardEvents;
    }

    public void addKeyboardEvent(KeyboardEvent keyboardEvent) {
        keyboardEvents.add(keyboardEvent);
    }

    public void clearKeyboardEvents() {
        keyboardEvents.clear();
    }
}
