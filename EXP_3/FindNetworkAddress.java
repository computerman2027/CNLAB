import java.util.*;

// class IPAddress {
//     int[] ip;
//     int[] subnet;

//     public IPAddress() {
//         ip = new int[4];
//         subnet = new int[4];
//     }

//     public IPAddress(String ip, String subnet) {
//         this.ip = new int[4];
//         this.subnet = new int[4];

//         String[] iparr = ip.split("\\.");
//         String[] subnetarr = subnet.split("\\.");

//         for (int i = 0; i < 4; i++) {
//             this.ip[i] = Integer.parseInt(iparr[i]);
//             this.subnet[i] = Integer.parseInt(subnetarr[i]);
//         }
//     }

//     IPAddress calculateNWAddress() {
//         IPAddress ans = new IPAddress();

//         for (int i = 0; i < 4; i++) {
//             ans.ip[i] = this.ip[i] & this.subnet[i];
//             ans.subnet[i] = this.subnet[i];
//         }

//         return ans;
//     }

//     @Override
//     public String toString() {
//         return "IP: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
//                 "\nSubnet: " + subnet[0] + "." + subnet[1] + "." + subnet[2] + "." + subnet[3];
//     }

// }

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
    }
}