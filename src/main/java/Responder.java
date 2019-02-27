import java.io.*;
import java.net.Socket;

public class Responder
{
    String status = null;
    String contentType=null;
    String fileName = null;
    String httpMethod = "";
    String END = "\r\n";
    String contentLength = null;
    DataOutputStream out = null;
    File file = null;
    FileInputStream fis = null;
    boolean badRequest = false;

    public Responder(String httpMethod, String fileName, Socket socket, boolean badRequest)
    {
        this.httpMethod = httpMethod;
        this.fileName = fileName;
        this.badRequest = badRequest;

        try
        {
            this.out = new DataOutputStream(socket.getOutputStream());
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendResponse() throws Exception
    {
        if(badRequest)
        {
            System.out.println("\n404 Bad Request\n");
            status = "HTTP/1.1 404 Not Found" + END;
            fileName = "ERROR404.HTML";
        }
    }
}
