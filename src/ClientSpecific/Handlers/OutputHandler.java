package ClientSpecific.Handlers;

import Utils.ObjectUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class OutputHandler extends Thread {

    private final DataOutputStream dOut;

    public OutputHandler(DataOutputStream dOut) {
        this.dOut = dOut;
    }

    public void run(){
        super.run();
        sendMessageAtIntervals();
    }

    private void sendMessageAtIntervals() {
        while (true){
            try{
                String string = "<Message from the client>";
                sendObject(string);
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendObject(Object object) throws IOException{
        dOut.writeUTF(ObjectUtil.convertToString((Serializable) object));
        dOut.flush();
    }
}
