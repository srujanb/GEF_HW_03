package ServerSpecific;

import ServerSpecific.Models.Client;
import Utils.UniversalConstants;
import processing.core.PApplet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain extends PApplet{

    private static final int PORT = 9754;
    private static ServerSocket serverSocket;
    private static GameInstance gameInstance;
    private static PApplet currentPappletInstance;
    private static Boolean serverStillOn = true;

    public static void main(String args[]){
        PApplet.main("ServerSpecific.ServerMain");
        //initialize server.
        try {
            initServer();
        } catch (IOException e) {
            System.out.println("Cannot initialize game. Quiting");
            e.printStackTrace();
            return;
        }

        //look for new clients.
        while (serverStillOn){
            try {
                System.out.println("Accepting clients");
                Socket socket = serverSocket.accept();
                Client client = new Client();
                client.setSocket(socket);
                try {
                    //This gives system enough time to initiate stuffs
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameInstance.addClient(client);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
        gameInstance = new GameInstance(currentPappletInstance);
        (new Thread(gameInstance)).start();
    }

    @Override
    public void settings() {
        size(UniversalConstants.PAPPLET_WIDTH,UniversalConstants.PAPPLET_HEIGHT);
        currentPappletInstance = this;
    }

    @Override
    public void setup() {
        frameRate(UniversalConstants.DEFAULT_FRAME_RATE);
    }

    @Override
    public void draw() {
        try {
            gameInstance.clearBackground();
            gameInstance.drawCurrentState();
//            Thread.sleep(30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
