import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class StopWaitSender {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getByName("localhost");

        System.out.println("Enter no of frames : ");
        int noOfFrames = Integer.parseInt(br.readLine());

        ArrayList<String> frames = new ArrayList<>();

        for(int i =0;i<noOfFrames;i++)
        {
            frames.add("Frame-"+i);
        }
        // String[] frames = {"Frame-0", "Frame-1", "Frame-2", "Frame-3"};

        int seq = 0;

        for(String frame : frames)
        {
            int txCount = 1;
            while(true)
            {

                String msg = seq + ":" + frame + ":TX" + txCount;
                // String msg = seq + ":" + frame;

                byte[] sendData = msg.getBytes();

                DatagramPacket dp =
                        new DatagramPacket(sendData, sendData.length, ip, 5000);

                ds.send(dp);

                System.out.println("Sent -> " + msg);

                ds.setSoTimeout(2000);

                try {

                    byte[] ackData = new byte[100];

                    DatagramPacket ackPacket =
                            new DatagramPacket(ackData, ackData.length);

                    ds.receive(ackPacket);

                    String ack =
                            new String(ackPacket.getData(),0,ackPacket.getLength());

                    System.out.println("Received ACK -> " + ack);

                    ack = ack.substring(0,4);
                    if(ack.equals("ACK" + (1-seq)))
                    {
                        seq = 1 - seq;
                        break;
                    }

                }
                catch(SocketTimeoutException e)
                {
                    System.out.println("Timeout... Retransmitting");
                    
                }
                txCount++;
            }
        }

        ds.close();
    }
}