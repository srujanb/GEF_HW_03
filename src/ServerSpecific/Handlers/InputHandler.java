package ServerSpecific.Handlers;

import Events.KeyboardEvent;
import Events.PanelEvent;
import ServerSpecific.Models.Client;
import Utils.ObjectUtil;
import Utils.UniversalConstants;
import javafx.scene.layout.Pane;

import java.io.DataInputStream;
import java.io.IOException;

public class InputHandler implements Runnable {

    private final Client client;
    private DataInputStream dIn;

    public InputHandler(Client client) {
        this.client = client;
        try {
            dIn = new DataInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            System.out.println("Error creating server's input stream.");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (dIn != null) {
            listenToInputs();
        }
    }

    private void listenToInputs() {
        while (true) {
            try {
                String string = dIn.readUTF();
                Object object = ObjectUtil.convertFromString(string);
                if (object instanceof String) {
                    System.out.println("Server received string " + object + " from client: " + client.getGUID());
                } else if (object instanceof KeyboardEvent) {
                    KeyboardEvent keyboardEvent = (KeyboardEvent) object;
                    keyboardEvent.setClientGUID(client.getGUID());
                    client.getGameInstance().keyboardInputEvent(keyboardEvent);
                } else if (object instanceof PanelEvent) {
                    handlePanelEvent((PanelEvent) object);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }

    private void handlePanelEvent(PanelEvent panelEvent) {
        client.getGameInstance().panelEvent(panelEvent);
    }
}
