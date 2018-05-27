import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class MyClient {
        public static void main(String[] args) throws Exception
	    {
	        Socket s  =  new Socket("223.3.206.238",6000);   //please modify the IP and port according to your settings
	        new Thread(new ClientThread(s)).start();
        }
}
