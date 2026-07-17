import java.io.*;
import java.net.*;
import java.time.*;

public class Server2 {

    public static void main(String[] args) {
        try{

            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept();

            DataInputStream din = new DataInputStream(s.getInputStream());

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String str = "";

            while (true) 
            {
                str=(String)din.readUTF();
                System.out.println(("Client says : "+str));
                if(str.equalsIgnoreCase("time"))
                {
                    dout.writeUTF("time = "+LocalDateTime.now());
                    break;
                }    
                else
                {
                    dout.writeUTF("INVALID COMMAND");
                }
            }
            s.close();
            ss.close();

        }   
        catch(Exception e) 
        {
            System.out.println(e.getMessage());
        }    
    }
}