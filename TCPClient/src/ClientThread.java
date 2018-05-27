import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.String.*;

import javax.swing.*;



public class ClientThread implements Runnable {

	 //static final String user_id = "22015314  quzhaohui ��";
	static  String user_id = "Ĭ���û���";
	static  boolean Fileflag = false;
	static  String  FileName = "null";
    static Frame f = new Frame("Chatting Room");
    static Box whole_layout = Box.createVerticalBox();
    static Box horizon_box_down = Box.createHorizontalBox();
    static Box horizon_box_up = Box.createHorizontalBox();
    static TextField self_info_display = new TextField(8);
    static ScrollPane  chatting_content_display = new ScrollPane();
    static TextArea text_content = new TextArea();
    static Button b_send = new Button("Send");
    static Button b_chooseFile  = new Button("chooseFile");
    static TextField  enter_text_field = new TextField(40);
    static String all_text_content = new String();

    private Socket s;
    private Socket s2;


    BufferedReader br = null;
    public ClientThread(Socket s) throws IOException
    {
        this.s = s;
        this.s2 = s;
        br = new BufferedReader( new InputStreamReader(s.getInputStream()));
        GUI_init();
       

    }

    public void run()
    {
        try
        {
            String content = null;
            while((content = br.readLine()) != null)
            {
                
            	System.out.println(content);
                all_text_content = all_text_content + "\n"+content;
                text_content.setText(all_text_content);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void GUI_init() throws IOException 
    {
    	 
    	b_send.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
    		   if(!enter_text_field.getText().isEmpty())
    			{
    				    
					PrintStream ps;
					try {
						ps = new PrintStream(s.getOutputStream());
						String  line = enter_text_field.getText();
				           user_id = self_info_display.getText();
				           ps.println(user_id+": "+line);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
    		}
          });
        
    	b_chooseFile.addActionListener(new ActionListener() {

    	public void actionPerformed(ActionEvent e) {  
        // TODO Auto-generated method stub  
    		//deleteFile("F://121.txt");
           JFileChooser jfc=new JFileChooser();  
           jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
           jfc.showDialog(new JLabel(), "ѡ��");  
           File file=jfc.getSelectedFile();  
           if(file.isDirectory()){  
                System.out.println("�ļ���:"+file.getAbsolutePath());  
            } 
           else if(file.isFile()){  
                System.out.println("�ļ�:"+file.getAbsolutePath());  
                Fileflag = true;
                FileName = file.getAbsolutePath();
                
                File file_transfered = new File(FileName);
                try {
                	PrintStream ps;
                	final OutputStream output = s.getOutputStream();
					ps = new PrintStream(output);
					String back_form = jfc.getSelectedFile().getName();
				
					System.out.println(back_form);
					String[] ss = new String[20];
					 ss = back_form.split("\\.");
					int count = ss.length;
					System.out.println(count);
					String  line = "�ҷ�����,"+ss[1];//jfc.getSelectedFile().getName();
			        user_id = self_info_display.getText();
			        ps.println(user_id+":"+line);
                	
                    FileInputStream fis = new FileInputStream(file);
                    System.out.println("���ӳɹ������ڴ�������...");
                    byte[] buffer = new byte[1024000];
                    int len = 0;
                    int dataCounter = 0;
                    while ((len = fis.read(buffer)) != -1) {
                  
                        output.write(buffer, 0, len);
                        dataCounter += len;
                    }
                    s.shutdownOutput();
                    output.flush();
                    fis.close();
                    
                    //output.close();
		            
		            System.out.println("���ݴ�����ϣ�������" + dataCounter + "�ֽڡ�");
		            
				} catch (UnknownHostException e1) {
		            e1.printStackTrace();
		        } catch (IOException e2) {
		            e2.printStackTrace();
		        }
            }  
           System.out.println(jfc.getSelectedFile().getName());  
           
         }  
    	});
        
    	
    	chatting_content_display.setBounds(10,10,220,130);
        chatting_content_display.add(text_content);
        horizon_box_up.add(chatting_content_display);
        
        horizon_box_up.add(self_info_display);
        whole_layout.add(horizon_box_up);
        horizon_box_down.add(enter_text_field);
        horizon_box_down.add(b_send);
        horizon_box_down.add(b_chooseFile);
        whole_layout.add(horizon_box_down);

        f.add(whole_layout);
        f.addWindowListener(new MyListener());
        f.pack();
        f.setVisible(true);


    }
    class MyListener extends WindowAdapter {
        public void windowClosing(WindowEvent e)
        {
            System.out.println("�û��ر��˴���");
            System.exit(0);
        }
    }
    public  boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
                return true;
            } else {
                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
                return false;
            }
        } else {
            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
            return false;
        }
    }


}
