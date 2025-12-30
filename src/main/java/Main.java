import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
                DNSMessageQuestion[] dnsMessageQuestionList=dnsRequestMessage.getDnsMessageQuestionList();

                int numberOfQuestions=dnsMessageQuestionList.length;

                DNSMessageAnswer[] dnsMessageAnswerList=new DNSMessageAnswer[dnsMessageQuestionList.length];

                for(int i=0;i<numberOfQuestions;i++){

                    DNSMessageQuestion dnsMessageQuestion=dnsMessageQuestionList[i];

                    //Flags:  CODE BITS -> QR 1 | OPCODE 4 | AA 1 | TC 1 | RD 1 | RA 1 | Z 3 | RCODE 4
                    byte valueToSetMSB=-128;
                    byte opCode= (byte) (dnsMessageHeader.getFlags()[0] & Byte.parseByte("01111000",2));
                    byte rd= (byte) (dnsMessageHeader.getFlags()[0] & 1);

                    byte[] flags=new byte[]{(byte)(valueToSetMSB | opCode | rd),(byte)(opCode==0?0:4)};

                    dnsMessageHeader.setFlags(flags);
                    dnsMessageHeader.setQuestionCount(new byte[]{0,1});
                    dnsMessageHeader.setAnswerCount(new byte[]{0,1});

                    DNSMessageAnswer dnsMessageAnswer= DNSMessageParser.getDNSMessageAnswer(dnsMessageQuestion);

                    dnsMessageAnswerList[i]=dnsMessageAnswer;
                }


                DNSMessage dnsResponseMessage=new DNSMessage(dnsMessageHeader,dnsMessageQuestionList,dnsMessageAnswerList,null);

                final DatagramPacket packetResponse = new DatagramPacket(dnsResponseMessage.getDNSMessageInBytes(),512, packet.getSocketAddress());
                serverSocket.send(packetResponse);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
