import java.util.*;

public class ClassIdentifier {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ENTER IP ADDRESS : ");
        String inputIp = sc.next();

        IPAddress ip = new IPAddress(inputIp);

        char ipclass= ip.getIPClass();

        System.out.println(ipclass=='X'?"Invalid": "class " + ipclass);
    }
}