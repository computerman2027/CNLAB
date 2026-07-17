import java.util.*;

public class IPtoBinary {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ENTER IP ADDRESS : ");
        String inputIp = sc.next();

        IPAddress ip = new IPAddress(inputIp);

        System.out.println(ip.toBinaryIP());

    }
}