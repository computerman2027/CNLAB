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
    public IPAddress(String ip) {
        this.ip = new int[4];
        this.subnet = new int[4];
        wildcardMask = new int[4];

        String[] iparr = ip.split("\\.");
    

        for (int i = 0; i < 4; i++) {
            this.ip[i] = Integer.parseInt(iparr[i]);
        }
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

    void calculateWildCardMask()
    {
        for (int i = 0; i < 4; i++) {
            this.wildcardMask[i] = 255 - this.subnet[i];
        }
    }

    public IPAddress(IPAddress other) {
        this.ip = Arrays.copyOf(other.ip, 4);
        this.subnet = Arrays.copyOf(other.subnet, 4);
        this.wildcardMask = Arrays.copyOf(other.wildcardMask, 4);
    }

    String toBinaryIP()
    {
        String binary="";

        for(int i =0;i<4;i++)
        {
            String temp = String.format("%8s", Integer.toBinaryString(ip[i])).replace(' ', '0');
            binary=binary+temp;
            if(i<3)
            binary=binary+".";
        }
        return binary;
    }

    String toBinarySubnet()
    {
        String binary="";

        for(int i =0;i<4;i++)
        {
            String temp = String.format("%8s", Integer.toBinaryString(subnet[i])).replace(' ', '0');
            binary=binary+temp;
            if(i<3)
            binary=binary+".";
        }
        return binary;
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

    char getIPClass() {
        if (ip[0] >= 1 && ip[0] <= 126)
            return 'A';
        else if (ip[0] >= 128 && ip[0] <= 191)
            return 'B';
        else if (ip[0] >= 192 && ip[0] <= 223)
            return 'C';
        else if (ip[0] >= 224 && ip[0] <= 239)
            return 'D';
        else if (ip[0] >= 240 && ip[0] <= 255)
            return 'E';
        else
            return 'X';   // Invalid for this assignment
    }

    int defaultPrefix() {
        switch (getIPClass()) {
            case 'A':
                return 8;
            case 'B':
                return 16;
            case 'C':
                return 24;
            default:
                return -1;
        }
    }

    int getPrefixLength() {
        int prefix = 0;
    
        for (int i = 0; i < 4; i++) {
            prefix += Integer.bitCount(subnet[i]);
        }
    
        return prefix;
    }

    int numberOfBlocks() {

        int prefix = getPrefixLength();
        int defaultPrefix = defaultPrefix();
    
        if (prefix > defaultPrefix) {
            System.out.println("Not a supernet.");
            return 1;
        }
    
        return 1 << (defaultPrefix - prefix);
    }

    void displayBlocks() {

        char ipClass = getIPClass();
        int blocks = numberOfBlocks();
    
        if (blocks == 1) {
            System.out.println("\nNot a Supernet.");
            return;
        }
    
        IPAddress network = calculateNWAddress();
    
        System.out.println("\nNumber of Blocks : " + blocks);
        System.out.println("\nBlock Ranges:");
    
        for (int i = 0; i < blocks; i++) {
    
            int[] start = Arrays.copyOf(network.ip, 4);
            int[] end = Arrays.copyOf(network.ip, 4);
    
            switch (ipClass) {
    
                case 'A':
                    start[0] = network.ip[0] + i;
                    start[1] = 0;
                    start[2] = 0;
                    start[3] = 0;
    
                    end[0] = network.ip[0] + i;
                    end[1] = 255;
                    end[2] = 255;
                    end[3] = 255;
                    break;
    
                case 'B':
                    start[1] = network.ip[1] + i;
                    start[2] = 0;
                    start[3] = 0;
    
                    end[1] = network.ip[1] + i;
                    end[2] = 255;
                    end[3] = 255;
                    break;
    
                case 'C':
                    start[2] = network.ip[2] + i;
                    start[3] = 0;
    
                    end[2] = network.ip[2] + i;
                    end[3] = 255;
                    break;
            }
    
            System.out.println(
                    "Block " + (i + 1) + " : "
                            + start[0] + "." + start[1] + "." + start[2] + "." + start[3]
                            + "  -  "
                            + end[0] + "." + end[1] + "." + end[2] + "." + end[3]);
        }
    }
    
    static int nextPowerOf2(int x) {
        int ans = 1;
        while (ans < x)
            ans <<= 1;
        return ans;
    }

    static int log2(int n) {
        int ans = 0;
        while (n > 1) {
            n /= 2;
            ans++;
        }
        return ans;
    }
    
    static String prefixToMask(int prefix) {
        int[] mask = new int[4];
    
        for (int i = 0; i < 4; i++) {
            int bits = Math.min(prefix, 8);
    
            if (bits == 0)
                mask[i] = 0;
            else
                mask[i] = 256 - (1 << (8 - bits));
    
            prefix -= bits;
        }
    
        return mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3];
    }
    
    static IPAddress[] createSubnetworks(IPAddress original, int subnetCount) {

        subnetCount = nextPowerOf2(subnetCount);
    
        int defaultPrefix = original.defaultPrefix();
    
        int borrowedBits = log2(subnetCount);
    
        int newPrefix = defaultPrefix + borrowedBits;
    
        String newMask = prefixToMask(newPrefix);
    
        // Create first subnet
        IPAddress first = new IPAddress(
                original.ip[0] + "." + original.ip[1] + "." +
                original.ip[2] + "." + original.ip[3],
                newMask);
    
        first = first.calculateNWAddress();
    
        IPAddress[] subnets = new IPAddress[subnetCount];
    
        subnets[0] = first;
        subnets[0].calculateWildCardMask();
    
        int hostsPerSubnet = first.totalAddresses();
    
        for (int i = 1; i < subnetCount; i++) {
    
            IPAddress next = new IPAddress(subnets[i - 1]);
    
            int increment = hostsPerSubnet;
    
            while (increment > 0) {
    
                next.ip[3]++;
    
                for (int j = 3; j > 0; j--) {
                    if (next.ip[j] > 255) {
                        next.ip[j] = 0;
                        next.ip[j - 1]++;
                    }
                }
    
                increment--;
            }
    
            subnets[i] = next;
            // subnets[i].calculateWildCardMask();
        }
    
        return subnets;
    }
    
    @Override
    public String toString() {
        return "IP: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
                " Subnet: " + subnet[0] + "." + subnet[1] + "." + subnet[2] + "." + subnet[3];
    }
}
