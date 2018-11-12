package ServerSpecific.Handlers;

import ServerSpecific.Models.Client;
import Utils.ObjectUtil;

import java.io.DataInputStream;
import java.io.IOException;

public class InputHandler extends Thread {

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

    public void run(){
        super.run();
        if (dIn != null){
            listenToInputs();
        }
    }

    private void listenToInputs() {
        while (true){
            try {
                String string = dIn.readUTF();
                Object object = ObjectUtil.convertFromString(string);
                if (object instanceof String){
                    System.out.println("Server received string " + object + " from client: " + client.getGUID());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException){
                classNotFoundException.printStackTrace();
            }
        }
    }

}
