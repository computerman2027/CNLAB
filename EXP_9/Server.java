import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {

    public static void main(String[] args) {
        try {

            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept();

            DataInputStream din = new DataInputStream(s.getInputStream());

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            String str = "";

            str = (String) din.readUTF();
            System.out.println(("Client says : " + str));

            dout.writeUTF(str.toUpperCase());

            s.close();
            ss.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}