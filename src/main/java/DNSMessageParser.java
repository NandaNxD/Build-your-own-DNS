import java.nio.ByteBuffer;

public class DNSMessageParser {
    public DNSMessageParser(){

    }

    static DNSMessage getDNSMessage(byte[] packetData){
        DNSMessage dnsMessage = null;

        /**
         * Read DNS Message section
         */
        
        DNSMessageHeader dnsMessageHeader =getDNSHeader(packetData);


        return new DNSMessage(dnsMessageHeader,null,null,null);
    }

    static private DNSMessageHeader getDNSHeader(byte[] packetData){
        /**
         * Header size is 12 bytes
         */

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
}
