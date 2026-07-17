import java.io.*;
import java.net.*;

public class Client1 {

    public static void main(String[] args) {
        
        try{

            Socket s = new Socket("localhost",8080);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            dout.writeUTF("time");
            dout.flush();
            dout.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}