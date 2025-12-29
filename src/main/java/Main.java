import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Main {
  public static void main(String[] args){

     try(DatagramSocket serverSocket = new DatagramSocket(2053)) {
       while(true) {
         final byte[] buf = new byte[512];
         final DatagramPacket packet = new DatagramPacket(buf, buf.length);
         serverSocket.receive(packet);
         System.out.println("Received data");

         DNSMessage dnsMessage =DNSMessageParser.getDNSMessage(buf);

         final byte[] bufResponse = new byte[512];

         System.arraycopy(dnsMessage.getDnsMessageHeader().getDnsOriginalMessageHeaderInBytes(), 0, bufResponse, 0, 2);
           /**
            * Setting the MSB of the flags array
            */
         bufResponse[2]= -128;

         System.arraycopy(dnsMessage.getDnsMessageQuestion().getDnsOriginalMessageQuestionInBytes(), 0, bufResponse, 12, dnsMessage.getDnsMessageQuestion().getDnsOriginalMessageQuestionInBytes().length);
           /**
            * Set QD Count to 1
            */
         bufResponse[5]=1;

         final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
         serverSocket.send(packetResponse);
       }
     } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
     }
  }
}
