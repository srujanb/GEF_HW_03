package ServerSpecific;

import ServerSpecific.Models.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private static final int PORT = 9754;
    private static ServerSocket serverSocket;
    private static GameInstance gameInstance;

    private static Boolean serverStillOn = true;

    public static void main(String args[]){
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
        gameInstance = new GameInstance();
        (new Thread(gameInstance)).start();
    }
}
