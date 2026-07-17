import java.util.*;
import java.io.IOException;
import java.net.*;

public class MulticastServer {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Group Multicast IP: ");
        String group = sc.next() + sc.nextLine();
        System.out.println("Enter port no : ");
        int port = sc.nextInt();

        InetAddress address = InetAddress.getByName(group);

        DatagramSocket socket = new DatagramSocket();

        System.out.println("Multicast server started");

        System.out.print("Enter message: ");
        String message = sc.next()+sc.nextLine();

        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address, port);

        socket.send(packet);

        System.out.println("Message sent");

        socket.close();
        sc.close();
    }
}