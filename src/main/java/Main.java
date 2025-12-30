import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        try (DatagramSocket serverSocket = new DatagramSocket(2053)) {
            while (true) {
                final byte[] buf = new byte[512];
                final DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                System.out.println("Received data");

                DNSMessage dnsRequestMessage = DNSMessageParser.getDNSMessage(buf);

                DNSMessageHeader dnsMessageHeader=dnsRequestMessage.getDnsMessageHeader();
                DNSMessageQuestion dnsMessageQuestion=dnsRequestMessage.getDnsMessageQuestion();

                dnsMessageHeader.setFlags(new byte[]{-128,0});
                dnsMessageHeader.setQuestionCount(new byte[]{0,1});
                dnsMessageHeader.setAnswerCount(new byte[]{0,1});

                DNSMessageAnswer dnsMessageAnswer= DNSMessageParser.getDNSMessageAnswer(dnsMessageQuestion);

                DNSMessage dnsResponseMessage=new DNSMessage(dnsMessageHeader,dnsMessageQuestion,dnsMessageAnswer,null);

                final DatagramPacket packetResponse = new DatagramPacket(dnsResponseMessage.getDNSMessageInBytes(),512, packet.getSocketAddress());
                serverSocket.send(packetResponse);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
