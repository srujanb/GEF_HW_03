package ServerSpecific.Managers;

import Events.KeyboardEvent;
import Events.PanelEvent;
import ServerSpecific.Timeline;
import Utils.UniversalConstants;

import java.util.ArrayList;

public class EventsManager {

    private ArrayList<KeyboardEvent> keyboardEvents = new ArrayList<>();
    private PanelEvent panelEvent;

    public PanelEvent getPanelEvent() {
        return panelEvent;
    }

    public void setPanelEvent(PanelEvent panelEvent) {
        if (panelEvent.getEventType() == UniversalConstants.BUTTON_PAUSE){
            if (Timeline.getTickSize() >= 1000) return;
        }
        this.panelEvent = panelEvent;
    }

    public void resetPanelEvent(){
        panelEvent = null;
    }

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
