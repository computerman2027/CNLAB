import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class Server1 {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);

            Socket s = ss.accept();

            DataInputStream dis = new DataInputStream(s.getInputStream());

            String str = (String)dis.readUTF();

            if(str.equalsIgnoreCase("time"))
            {
                System.out.println(LocalDateTime.now());
            }

            s.close();
            ss.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
