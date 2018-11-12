package ServerSpecific.Handlers;

import ServerSpecific.Models.Client;
import Utils.ObjectUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class OutputHandler implements Runnable{

    private final Client client;
    private DataOutputStream dOut;

    public OutputHandler(Client client) {
        this.client = client;
        try {
            dOut = new DataOutputStream(client.getSocket().getOutputStream());
        } catch (IOException e) {
            System.out.println("Error creating server's output stream.");
            e.printStackTrace();
        }
    }

    @Override
    public void run() { }

    public void sendObject(Object object) throws IOException {
        if (null == dOut){
            System.out.println("Cannot send data, dOut is null");
            return;
        }
        dOut.writeUTF(ObjectUtil.convertToString((Serializable) object));
        dOut.flush();
    }
}
