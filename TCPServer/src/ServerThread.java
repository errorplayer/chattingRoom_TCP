import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class ServerThread implements Runnable {
	    Socket s = null;
	    BufferedReader br = null;
        
	    public ServerThread(Socket s) throws IOException
	    {
	        this.s = s;
	        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    }

	    public void run()
	    {
	        try
	        {
	            String content = null;
	           
	            while ((content = readFromClient()) != null)
	            {
	            	if (content.contains("我发送了"))
	            	{
	            		String[] ss = new String[20];
						 ss = content.split(",");
	            		deleteFile("F://receivedFile."+ss[1]);
	            		InputStream in = s.getInputStream();
	            		File fff = new File("F://receivedFile."+ss[1]);
	                    FileOutputStream fos = new FileOutputStream(fff);
	                    System.out.println("开始接数据...");
	                    byte[] buffer  = new byte[1024];
	                    int len = 0;
	                    int dataCounter = 0;
	                    //buffer = content.getBytes();
	                    //len = buffer.length;
	                    while ((len = in.read(buffer)) != -1) {
	                        fos.write(buffer, 0, len);
	                        dataCounter += len;
	                        System.out.println(dataCounter);
	                    }
	                    fos.flush();
	                    fos.close();
	                    //in.close();
	            	}else
	                for (Object s : MyServer.socketList)
	                {
	                	Socket sub_s = (Socket) s;
	                	
	                    PrintStream ps  = new PrintStream(sub_s.getOutputStream());
	                    ps.println(content);
	                }
	            }
	        }
	        catch (IOException e )
	        {
	            e.printStackTrace();
	        }
	    }

	    private String readFromClient() {

	        try
	        {
	            return br.readLine();
	        }
	        catch (IOException e)
	        {
	            MyServer.socketList.remove(s);
	        }
	        return null;
	    }
	    
	    public  File createFile(String pathName) {
	        File file = new File(pathName);
	        try{
	             boolean bFile = false;
	        
	        	bFile = file.createNewFile();
	        	System.out.println(bFile);
	        	}
	        	catch(IOException ioe)
	        	{
	        		ioe.printStackTrace();
	        	}
	        
	        return file;
	    }

	    public  boolean deleteFile(String fileName) {
	        File file = new File(fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("删除单个文件" + fileName + "成功！");
	                return true;
	            } else {
	                System.out.println("删除单个文件" + fileName + "失败！");
	                return false;
	            }
	        } else {
	            System.out.println("删除单个文件失败：" + fileName + "不存在！");
	            return false;
	        }
	    }

}
