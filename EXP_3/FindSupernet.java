import java.util.Scanner;

public class FindSupernet {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("ENTER IP ADDRESS : ");
        String inputIp = sc.next();

        System.out.print("Enter subnet Mask : ");
        String subnetMask = sc.next();

        IPAddress ip1 = new IPAddress(inputIp,subnetMask);

        ip1.displayDetails();

        ip1.displayBlocks();
    }
}
