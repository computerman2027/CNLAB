import java.util.*;
import java.io.IOException;
import java.net.*;

public class MulticastClient {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Group Multicast IP: ");
        String group = sc.next()+sc.nextLine();
        System.out.println("Enter port no : ");
        int port = sc.nextInt();


        InetAddress address = InetAddress.getByName(group);
        MulticastSocket socket = new MulticastSocket(port);

        socket.joinGroup(address);

        System.out.println("Joint multicast group : ");

        byte[] buffer = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        String message = new String(packet.getData(),0,packet.getLength());

        System.out.println("Received : "+ message);

        socket.leaveGroup(address);

        socket.close();

    }
}