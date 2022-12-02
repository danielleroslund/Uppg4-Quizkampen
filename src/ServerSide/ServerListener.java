package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {

    private int port = 55555;
    private Socket connection;
    private ServerSocket serverSocket; // lyssnar efter connection

    private ServerListener(){
        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e){
            e.printStackTrace();
        }

        while(true){
            try{
                System.out.println("Waiting for clients");
                connection = serverSocket.accept(); // accepterar första anslutningen, skapar en player "server" åt client 1

                Player p1 = new Player(connection);
                System.out.println("Spelare 1 ansluten " + connection.getInetAddress().getHostAddress());

                Player p2 = new Player(serverSocket.accept()); // accepterar andra anslutningen, skapar player2 åt client
                System.out.println("Spelare 2 ansluten " + connection.getInetAddress().getHostName());

                System.out.println("Starting");

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        new PlayerHandler(p1,p2);
                    }
                }).start();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new ServerListener();
    }
}
