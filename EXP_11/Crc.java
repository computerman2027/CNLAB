import java.util.Scanner;

public class Crc {
    static char[] subtract(char[] a, char[] b)
    {
        char[] ans = new char[a.length];
        for(int i = a.length-1; i>=0;i--)
            ans[i] = (a[i] == b[i]) ? '0' : '1';
        return ans;
    }

    static String divide(String data, String divisor)
    {
        int divisorLength = divisor.length();
        int dataLength = data.length();
        char[] temp = data.substring(0, divisorLength).toCharArray();
        char[] divisorArray = divisor.toCharArray();
        int i,j;
        for(i=divisorLength-1;i<dataLength;i++)
        {
            if(temp[0]=='1')
            {
                temp = subtract(temp, divisorArray);
            }
            if(i<dataLength-1)
            {
                for(j=1;j<divisorLength;j++)
                {
                    temp[j-1]=temp[j];
                }
                temp[j-1]= data.charAt(i+1);
            }
        }
        return new String(temp,1,divisorLength-1);
    }

    static String generateCrc(String data, String divisor)
    {
        StringBuilder adjustedData = new StringBuilder(data);
        adjustedData.append("0".repeat(divisor.length()-1));
        String remainder = divide(adjustedData.toString(), divisor);
        return data+remainder;
    }

    static boolean checkCrc(String data, String divisor)
    {
        String remainder = divide(data, divisor);
        return !remainder.contains("1");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Data : ");
        String data = sc.next()+sc.nextLine();
        System.out.print("Enter Divisor : ");
        String divisor = sc.next()+sc.nextLine();

        String codeword = generateCrc(data, divisor);

        System.out.println("Code word = "+ codeword);

        System.out.print("Enter Received Codeword : ");
        String receivedCodeWord = sc.next()+sc.nextLine();

        if(checkCrc(receivedCodeWord, divisor))
            System.out.println("Correct Data");
        else
            System.out.println("Invalid data");
    }
}