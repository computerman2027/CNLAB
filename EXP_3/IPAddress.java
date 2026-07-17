import java.util.*;

public class IPAddress {
    int[] ip;
    int[] subnet;
    int[] wildcardMask;

    public IPAddress() {
        ip = new int[4];
        subnet = new int[4];
        wildcardMask = new int[4];
    }

    public IPAddress(String ip, String subnet) {
        this.ip = new int[4];
        this.subnet = new int[4];
        wildcardMask = new int[4];

        String[] iparr = ip.split("\\.");
        String[] subnetarr = subnet.split("\\.");

        for (int i = 0; i < 4; i++) {
            this.ip[i] = Integer.parseInt(iparr[i]);
            this.subnet[i] = Integer.parseInt(subnetarr[i]);
            this.wildcardMask[i] = 255 - this.subnet[i];
        }
    }

    public IPAddress(IPAddress other) {
        this.ip = Arrays.copyOf(other.ip, 4);
        this.subnet = Arrays.copyOf(other.subnet, 4);
        this.wildcardMask = Arrays.copyOf(other.wildcardMask, 4);
    }

    IPAddress calculateNWAddress() {
        IPAddress ans = new IPAddress();

        for (int i = 0; i < 4; i++) {
            ans.ip[i] = this.ip[i] & this.subnet[i];
            ans.subnet[i] = this.subnet[i];
        }

        return ans;
    }

    IPAddress[] calculateRange() {
        IPAddress startAddress = calculateNWAddress();
        IPAddress endAddress = new IPAddress();

        for(int i =0;i<4;i++)
        {
            endAddress.ip[i]= startAddress.ip[i]+this.wildcardMask[i];
            endAddress.subnet[i]=startAddress.subnet[i];
        }

        return new IPAddress[]{startAddress,endAddress};
    }

    int totalAddresses() {
        int hostBits = 0;
    
        for (int i = 0; i < 4; i++) {
            hostBits += Integer.bitCount(wildcardMask[i]);
        }
    
        return 1 << hostBits;
    }

    int usableAddresses() {
        int total = totalAddresses();
    
        if (total <= 2)
            return total;
    
        return total - 2;
    }
    void incrementIP() {
        for (int i = 3; i >= 0; i--) {
            if (ip[i] < 255) {
                ip[i]++;
                break;
            }
            ip[i] = 0;
        }
    }
    void decrementIP() {
        for (int i = 3; i >= 0; i--) {
            if (ip[i] > 0) {
                ip[i]--;
                break;
            }
            ip[i] = 255;
        }
    }
    IPAddress[] usableRange() {
        IPAddress[] range = calculateRange();
    
        IPAddress first = new IPAddress(range[0]);
        IPAddress last = new IPAddress(range[1]);
    
        first.incrementIP();
        last.decrementIP();
    
        return new IPAddress[]{first, last};
    }
    void displayDetails() {

        System.out.println("IP Address       : " + this);
    
        IPAddress network = calculateNWAddress();
    
        System.out.println("\nNetwork Address");
        System.out.println(network);
    
        IPAddress[] range = calculateRange();
    
        System.out.println("\nAddress Range");
        System.out.println("Start : " + range[0]);
        System.out.println("End   : " + range[1]);
    
        System.out.println("\nTotal Addresses : " + totalAddresses());
        System.out.println("Usable Addresses: " + usableAddresses());
    
        IPAddress[] usable = usableRange();
    
        System.out.println("\nUsable Range");
        System.out.println("First : " + usable[0]);
        System.out.println("Last  : " + usable[1]);
    }

    int getPrefixLength()
    {
        int prefix = 0;

        for(int i =0;i<4;i++)
        {
            prefix+= Integer.bitCount(subnet[i]);
        }
        return prefix;
    }

    int numberOfBlocks()
    {
        int prefix = getPrefixLength();

        if(prefix>24)
        {
            return 1;
        }

        return 1<< (24-prefix);
    }

    void displayBlocks() {

        int prefix = getPrefixLength();
    
        if (prefix > 24) {
            System.out.println("\nNot a supernet.");
            return;
        }
    
        int blocks = numberOfBlocks();
    
        IPAddress network = calculateNWAddress();
    
        System.out.println("\nNumber of Blocks : " + blocks);
        System.out.println("\nBlock Ranges:");
    
        for (int i = 0; i < blocks; i++) {
    
            int thirdOctet = network.ip[2] + i;
    
            System.out.println(
                "Block " + (i + 1) + " : " +
                network.ip[0] + "." +
                network.ip[1] + "." +
                thirdOctet + ".0  -  " +
                network.ip[0] + "." +
                network.ip[1] + "." +
                thirdOctet + ".255"
            );
        }
    }

    @Override
    public String toString() {
        return "IP: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
                " Subnet: " + subnet[0] + "." + subnet[1] + "." + subnet[2] + "." + subnet[3];
    }
}
