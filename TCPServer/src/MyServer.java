import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyServer {
	public static List<Object> socketList = Collections.synchronizedList(new ArrayList<>());
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(6000);
        while(true)
        {
            Socket s = ss.accept();
            socketList.add(s);
            new Thread(new ServerThread(s)).start();
            
            //223.3.206.238
        }
    }
}
