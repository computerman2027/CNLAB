package EXP_7;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class EchoServer {

    public static void main(String[] args) {
        try{

            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept();

            DataInputStream din = new DataInputStream(s.getInputStream());

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String str = "",str2="";

            while (true) 
            {
                str=(String)din.readUTF();
                System.out.println(("Client says : "+str));
                if(str.equalsIgnoreCase("stop"))
                {
                    dout.writeUTF("SERVER STOPPING");
                    break;
                }    
                
                str2=br.readLine();
                dout.writeUTF(str2);
                dout.flush();

            }

            din.close();
            dout.close();
            s.close();
            ss.close();

        }   
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }    
    }
}