import java.util.*;

public class FindNetworkAddress {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ENTER IP ADDRESS : ");
        String inputIp = sc.next();

        System.out.print("Enter subnet Mask : ");
        String subnetMask = sc.next();

        IPAddress ip1 = new IPAddress(inputIp,subnetMask);

        IPAddress nwaddress = ip1.calculateNWAddress();

        System.out.print("newtork address = ");
        System.out.println(nwaddress);

        ip1.displayDetails();
    }
}