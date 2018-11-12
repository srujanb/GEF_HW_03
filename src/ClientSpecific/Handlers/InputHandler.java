package ClientSpecific.Handlers;

import Utils.ObjectUtil;

import java.io.DataInputStream;

public class InputHandler extends Thread {

    private final DataInputStream dIn;

    public InputHandler(DataInputStream dIn) {
        this.dIn = dIn;
    }

    public void run(){
        listenToInputs();
    }

    private void listenToInputs() {
        while (true){
            try{
                String string = dIn.readUTF();
                Object obj = ObjectUtil.convertFromString(string);
                if (obj instanceof String){
                    System.out.println("From server" + obj);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
