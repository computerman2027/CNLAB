import java.util.Scanner;

public class Subnetwork {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
    
        System.out.print("ENTER IP ADDRESS : ");
        String inputIp = sc.next();
    
        IPAddress ip = new IPAddress(inputIp);
    
        System.out.print("Enter number of subnetworks : ");
        int n = sc.nextInt();
    
        IPAddress[] subnets = IPAddress.createSubnetworks(ip, n);
    
        for (int i = 0; i < subnets.length; i++) {
    
            System.out.println("\n==============================");
            System.out.println("Subnet " + (i + 1));
            System.out.println("==============================");
    
            subnets[i].displayDetails();
        }
    }
}
