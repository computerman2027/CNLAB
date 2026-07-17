import java.net.*;
import java.io.*;

public class StopWaitReceiver {

    public static void main(String[] args) throws Exception {

        DatagramSocket ds = new DatagramSocket(5000);

        int expectedSeq = 0;

        while(true)
        {
            byte[] receiveData = new byte[1024];

            DatagramPacket dp =
                    new DatagramPacket(receiveData, receiveData.length);

            ds.receive(dp);

            String msg =
                    new String(dp.getData(),0,dp.getLength());

            int randomNum = (int)(Math.random() * 101);

            if(randomNum>80)
            {
                continue;
            }

            System.out.println("Received -> " + msg);

            int seq = Character.getNumericValue(msg.charAt(0));

            if(seq == expectedSeq)
            {
                System.out.println("Accepted Frame");

                expectedSeq = 1 - expectedSeq;
            }
            else
            {
                System.out.println("Duplicate Frame");
            }

            randomNum = (int)(Math.random() * 101);

            if(randomNum>80)
            {
                continue;
            }

            randomNum = (int)(Math.random() * 101);

            if(randomNum>70)
            {
                Thread.sleep(3000);
                System.out.println("LATE");
            }

            // String ack = "ACK" + expectedSeq;
            String ack = "ACK" + expectedSeq + "-T" + System.currentTimeMillis();

            byte[] ackData = ack.getBytes();

            DatagramPacket ackPacket =
                    new DatagramPacket(
                            ackData,
                            ackData.length,
                            dp.getAddress(),
                            dp.getPort()
                    );

            ds.send(ackPacket);

            System.out.println("Sent -> " + ack);
        }
    }
}