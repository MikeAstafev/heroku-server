import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpsServer
{
    static int PORT = 8080;
    public static void main(String[] args) throws Throwable {
        //try to create server with port '8080'
        ServerSocket server = null;
        //creating server socket on port 8080
        try {
            server = new ServerSocket(PORT, 0);
            System.out.println("Server started on port: " + server.getLocalPort() + "\n");
        } catch (IOException ex) {
            System.out.println("Port " + PORT + " is blocked.");
            System.exit(-1);
        }
        //new step - waiting for clients
        while (true) {
            try {
                System.out.println("Waiting for connection...");
                Socket client = server.accept();
                //print if connected
                System.out.println("Connection accepted.");
                ClientSession session = new ClientSession(client);
                new Thread(session).start();
            } catch (IOException ex) {
                System.out.println("Failed to establish connection.");
                System.out.println(ex.getMessage());
                System.exit(-1);
            }
        }
    }
}
