import java.net.*;
import java.util.*;


public class AddressFinding {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try{
            InetAddress localIp = InetAddress.getLocalHost();
            System.out.println("local host address = "+ localIp.getHostAddress());
            System.out.println("local host name = "+ localIp.getHostAddress());
        }
        catch (UnknownHostException e)
        {
            System.out.println(e.getMessage());
        }


        try{

            System.out.println("Enter server name : ");
            String serverName = sc.next();
            InetAddress localIp = InetAddress.getByName(serverName);
            System.out.println("local host address = "+ localIp.getHostAddress());
            System.out.println("local host name = "+ localIp.getHostAddress());
        }
        catch (UnknownHostException e)
        {
            System.out.println(e.getMessage());
        }
    }
}