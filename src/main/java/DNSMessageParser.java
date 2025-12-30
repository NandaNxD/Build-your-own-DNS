import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.out;

public class DNSMessageParser {
    public DNSMessageParser(){

    }

    static DNSMessage getDNSMessage(byte[] packetData){

        /**
         * Read DNS Message section
         */
        
        DNSMessageHeader dnsMessageHeader =getDNSHeader(packetData);
        DNSMessageQuestion dnsMessageQuestion=getDNSMessageQuestion(packetData);


        return new DNSMessage(dnsMessageHeader,dnsMessageQuestion,null,null);
    }

    static private DNSMessageHeader getDNSHeader(byte[] packetData){
        /**
         * Header size is 12 bytes
         */

        int offset=0;

        byte[] dnsOriginalMessageHeader=new byte[2*6];

        byte[] packetid=new byte[2];
        byte[] flags=new byte[2];
        byte[] questionCount=new byte[2];
        byte[] answerCount=new byte[2];
        byte[] authorityCount=new byte[2];
        byte[] additionalCount=new byte[2];

        for(int i = 0,j=0; i<12; i++,j++){
            if(i%2==0){
                j=0;
            }

            if(i<2){
                packetid[j]=packetData[i];
            }
            else if(i<2*2){
                flags[j]=packetData[i];
            }
            else if(i<2*3){
                questionCount[j]=packetData[i];
            }
            else if(i<2*4){
                answerCount[j]=packetData[i];
            }
            else if(i<2*5){
                authorityCount[j]=packetData[i];
            }
            else{
                additionalCount[j]=packetData[i];
            }
            dnsOriginalMessageHeader[i]=packetData[i];
        }


        return new DNSMessageHeader(dnsOriginalMessageHeader,packetid,flags,questionCount,answerCount,authorityCount,additionalCount);
    }

    private static DNSMessageQuestion getDNSMessageQuestion(byte[] packet){
        int offset=12;

        boolean reachedEndOfName=false;

        StringBuilder domainName=new StringBuilder();

        ArrayList<Byte> name=new ArrayList<>();

        while(!reachedEndOfName){
            byte contentLength=packet[offset++];
            name.add(contentLength);

            if(contentLength==0){
                domainName.deleteCharAt(domainName.length()-1);
                reachedEndOfName=true;
                continue;
            }

            while(contentLength-->0){
                domainName.append((char)packet[offset]);
                name.add(packet[offset++]);
            }
            domainName.append('.');
        }

        byte[] qType=new byte[2];

        System.arraycopy(packet,offset,qType,0,2);
        offset+=2;

        byte[] qClass=new byte[2];
        System.arraycopy(packet,offset,qClass,0,2);

        offset+=2;


        return new DNSMessageQuestion(Arrays.copyOfRange(packet,12,offset), Util.convertByteArrayListToByteArray(name),qType,qClass,domainName.toString());
    }

}
