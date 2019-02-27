import com.sun.net.httpserver.HttpServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.StringTokenizer;
import java.net.Socket;
import java.util.Date;
//import org.json.simple.JSONObject;

//checking client response

//КОД СЕССИИ КЛИЕНТА ВЗЯЛ СО СТОРОННЕГО САЙТА:
//Он читает http запрос, адрес, и возвращает юзеру то, что лежит по адресу
//парсинг json-post-get запросов реализую после твоего фидбека, Гошан

public class ClientSession implements Runnable
{
    private Socket socket = null;
    String[] request = null;
    String requestLine = "";
    String httpMethod = "";
    String httpVersion = "";
    String fileName = "";
    String status = "";
    String END = "\r\n";
    boolean badRequest = true;
    private InputStream in = null;
    private OutputStream out = null;

    private static final String DEF_FILES = "/www";
    @Override
    public void run()
    {
        try
        {
            processRequest();
        }catch(IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try
            {
                socket.close();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processRequest() throws Exception
    {
        System.out.println("\nReading request...\n");
        request = readRequest(socket).split(System.getProperty("line.separator"));
        requestLine = request[0].toUpperCase();
        //ответ клиенту
        String response = "";

        for(String str : request)
            System.out.println(str);

        switch (requestLine)
        {
            case "GET":
                responseGET(response);
                send(response);
                break;
            case "POST":
                responsePOST(response);
                send(response);
                break;
            default:
                throw new NotImplementedException();
        }

//        Responder respond = new Responder(httpMethod,fileName, socket, badRequest);
//        respond.sendResponse();
        socket.close();
    }

    private void responseGET(String response)
    {
        response = "{ ";
        response += "name: S7, id: 1, logoURL: https://upload.wikimedia.org/wikipedia/ru/a/a1/Aeroflot_logo.svg";

//        JSONObject getresp = new JSONObject();
//        getresp.put("name", "Аэрофлот");
//        getresp.put("id", "1");
//        getresp.put("logoURL","https://upload.wikimedia.org/wikipedia/ru/a/a1/Aeroflot_logo.svg");
        //response = getresp.toJSONString();
    }

    private void responsePOST(String response)
    {

    }

    private void send(String response) throws IOException
    {
        InputStream strm = new ByteArrayInputStream(response.getBytes());
        PrintStream answer = new PrintStream(out, true, "UTF-8");
        byte[] buffer = new byte[1024];
        int count = 0;
        while((count = strm.read(buffer)) != -1)
            out.write(buffer, 0, count);
        strm.close();

    }

    private String readRequest(Socket sock) throws IOException
    {
        InputStream input = socket.getInputStream();

        int avaliable = 0;
        String received = "";
        while (!received.endsWith(END + END))
        {
            avaliable = input.available();
            byte[] bytes = new byte[avaliable];
            input.read(bytes);

            received += new String(bytes);
        }
        return received.trim();
    }

    public ClientSession(Socket socket) throws IOException
    {
        this.socket = socket;
        initialize();
    }

    private void initialize() throws IOException
    {
        //Get input - there will be messages from client
        in = socket.getInputStream();
        //output - answer to client
        out = socket.getOutputStream();
    }
//
//    //get header from client file
//    private String readHeader() throws IOException
//    {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        StringBuilder builder = new StringBuilder();
//        String ln = null;
//        while(true)
//        {
//            ln = reader.readLine();
//            if(ln == null || ln.isEmpty())
//                break;
//            builder.append(ln + System.getProperty("line.separator"));
//        }
//        return builder.toString();
//    }
//
//    //get identifier of client resource
//    private String getURIFromHeader(String header)
//    {
//        int from = header.indexOf(" ") + 1;
//        int to = header.indexOf(" ",from);
//        String uri = header.substring(from,to);
//        int paramIndex = uri.indexOf("?");
//        if(paramIndex != -1)
//            uri = uri.substring(0, paramIndex);
//        return DEF_FILES + uri;
//    }
//    //send answer to the client - http of chosen resource
//    //if nothing is chosen - send list of free resources
//    private int send (String url) throws IOException
//    {
//        InputStream strm = HttpServer.class.getResourceAsStream(url);
//        int code = (strm != null) ? 200 : 404;
//        String header = getHeader(code);
//        PrintStream answer = new PrintStream(out, true, "UTF-8");
//        answer.print(header);
//        if(code == 200)
//        {
//            int count = 0;
//            byte[] buffer = new byte[1024];
//            while((count = strm.read(buffer)) != -1)
//                out.write(buffer, 0, count);
//            strm.close();
//        }
//        return code;
//    }
//    //return http-header of answer
//    private String getHeader(int code)
//    {
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
//        buffer.append("Date: " + new Date().toString() + "\n");
//        buffer.append("Accept-Ranges: none\n");
//        //buffer.append("Content-Type: " + contentType + "\n");
//        buffer.append("\n");
//        return buffer.toString();
//    }
//    //return comments to the code of result
//    private String getAnswer(int code)
//    {
//        switch (code)
//        {
//            case 200:
//                return "OK";
//            case 404:
//                return "Not Found";
//            default:
//                return "Internal Server Error";
//        }
//    }
}
