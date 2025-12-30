import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean forwardingDNSServerMode=false;
        String forwardingServerAddress=null;
        String ipAddress=null;
        Integer port=null;

        if(args[0].equals("--resolver")){
            forwardingServerAddress=args[1];
            String[] addressSplitList=forwardingServerAddress.split(":");
            ipAddress=addressSplitList[0];
            port=Integer.parseInt(addressSplitList[1]);
            forwardingDNSServerMode=true;
        }

        try (DatagramSocket serverSocket = new DatagramSocket(2053)) {
            while (true) {
                final byte[] buf = new byte[512];
                final DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                System.out.println("Received data");

                DNSMessage dnsRequestMessage = DNSMessageParser.getDNSMessage(buf);

                DNSMessageHeader dnsMessageHeader=dnsRequestMessage.getDnsMessageHeader();

                /**
                 * Set flags to tune it for response
                 */

                //Flags:  CODE BITS -> QR 1 | OPCODE 4 | AA 1 | TC 1 | RD 1 | RA 1 | Z 3 | RCODE 4
                byte valueToSetMSB=(byte)(forwardingDNSServerMode?0:-128);
                byte opCode= (byte) (dnsMessageHeader.getFlags()[0] & 0b01111000);
                byte rd= (byte) (dnsMessageHeader.getFlags()[0] & 1);

                byte[] flags=new byte[]{(byte)(valueToSetMSB | opCode | rd),(byte)(opCode==0?0:4)};

                byte[] originalQuestionCount=dnsMessageHeader.getQuestionCount();
                byte[] originalPacketId=dnsMessageHeader.getPacketId();

                dnsMessageHeader.setFlags(flags);
                dnsMessageHeader.setAnswerCount(dnsMessageHeader.getQuestionCount());

                DNSMessageQuestion[] dnsMessageQuestionList=dnsRequestMessage.getDnsMessageQuestionList();

                int numberOfQuestions=dnsMessageQuestionList.length;

                DNSMessageAnswer[] dnsMessageAnswerList=new DNSMessageAnswer[dnsMessageQuestionList.length];


                for(int i=0;i<numberOfQuestions;i++){
                    DNSMessageQuestion dnsMessageQuestion=dnsMessageQuestionList[i];
                    DNSMessageAnswer dnsMessageAnswer= DNSMessageParser.getDNSMessageAnswerLocally(dnsMessageQuestion);

                    dnsMessageAnswerList[i]=dnsMessageAnswer;
                }


                DNSMessage dnsResponseMessage=new DNSMessage(dnsMessageHeader,dnsMessageQuestionList,forwardingDNSServerMode?null:dnsMessageAnswerList,null);

                if(forwardingDNSServerMode){
                    /**
                     * Send individual questions to the resolving server
                     */

                    for(int i=0;i<numberOfQuestions;i++){
                        /**
                         * Send new packetID for each of the individual requests
                         */
                        dnsMessageHeader.setPacketId(new byte[]{(byte)(i+1),0});

                        final DatagramPacket packetResponse = new DatagramPacket(dnsResponseMessage.getDNSMessageInBytes(true),512, new InetSocketAddress(ipAddress,port));
                        serverSocket.send(packetResponse);

                        byte[] forwardedServerResponsePacketData=new byte[512];

                        DatagramPacket responsePacket = new DatagramPacket(forwardedServerResponsePacketData, forwardedServerResponsePacketData.length);
                        serverSocket.receive(responsePacket);

                        int messageAnswerOffset=12+dnsResponseMessage.getDnsMessageQuestionList()[0].getDNSMessageQuestionInBytes().length;

                        DNSMessageAnswer dnsMessageAnswer= DNSMessageParser.parseDNSMessageAnswer(forwardedServerResponsePacketData,messageAnswerOffset);

                        dnsMessageAnswerList[i]=dnsMessageAnswer;
                    }

                    dnsResponseMessage.setDnsMessageAnswerList(dnsMessageAnswerList);
                }

                /**
                 * Reset header to match first request
                 */
                dnsMessageHeader.setPacketId(originalPacketId);
                valueToSetMSB=-128;
                flags=new byte[]{(byte)(valueToSetMSB | opCode | rd),(byte)(opCode==0?0:4)};;
                dnsMessageHeader.setFlags(flags);
                dnsMessageHeader.setQuestionCount(originalQuestionCount);
                dnsMessageHeader.setAnswerCount(originalQuestionCount);

                final DatagramPacket packetResponse = new DatagramPacket(dnsResponseMessage.getDNSMessageInBytes(false),512, packet.getSocketAddress());
                serverSocket.send(packetResponse);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
