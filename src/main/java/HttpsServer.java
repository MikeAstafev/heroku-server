import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


public class HttpsServer
{
    static int port = 1337;
    public static void main(String[] args) throws Throwable {
        //try to create server with port '8080'
        Properties prop = new Properties();
        String str = prop.getProperty("$PORT");
        System.out.println(str);

        ServerSocket server = null;
        System.out.println(System.getenv("$PORT"));
        System.out.println(port);
        //creating server socket on port 8080
        try {
            server = new ServerSocket(8080, 0);
            System.out.println("Server started on port: " + server.getLocalPort() + "\n");
        } catch (IOException ex) {
            System.out.println("Port " + port + " is blocked.");
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
