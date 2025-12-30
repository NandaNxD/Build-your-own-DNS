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

        int numberOfQuestions=Short.toUnsignedInt(ByteBuffer.wrap(dnsMessageHeader.getQuestionCount()).getShort());

        DNSMessageQuestion[] dnsMessageQuestionList=new DNSMessageQuestion[numberOfQuestions];

        int offset=12;

        /**
         * Read all the questions
         */
        for(int i=0;i<numberOfQuestions;i++){
            DNSMessageQuestion dnsMessageQuestion=getDNSMessageQuestion(packetData,offset);
            dnsMessageQuestionList[i]=dnsMessageQuestion;
            offset+=dnsMessageQuestion.getName().length+dnsMessageQuestion.getqType().length+dnsMessageQuestion.getqClass().length;
        }

        return new DNSMessage(dnsMessageHeader,dnsMessageQuestionList,null,null);
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


        return new DNSMessageHeader(packetid,flags,questionCount,answerCount,authorityCount,additionalCount);
    }

    private static DNSMessageQuestion getDNSMessageQuestion(byte[] packet,int offset){

        boolean reachedEndOfName=false;

        StringBuilder domainName=new StringBuilder();

        ArrayList<Byte> name=new ArrayList<>();

        boolean compressionFlag=false;
        byte[] uncompressedEncodedDomainName=null;

        while(!reachedEndOfName){

            byte contentLength=packet[offset++];

            if((contentLength & 0b11000000)>0){
                // TODO: Handle compression
                /**
                 * If first two bits of the contentLength is 11,
                 * This represents that domain name is compressed
                 */
                int pointerOffset=Short.toUnsignedInt(ByteBuffer.wrap(new byte[]{(byte)(contentLength ^ 0b11000000),packet[offset]}).getShort());
                uncompressedEncodedDomainName=getEncodedDomainNameFromPointer(packet,pointerOffset);
                compressionFlag=true;
                offset++;
                break;
            }
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

        return new DNSMessageQuestion(compressionFlag?uncompressedEncodedDomainName: Util.convertByteArrayListToByteArray(name),qType,qClass,domainName.toString());
    }

    public static DNSMessageAnswer parseDNSMessageAnswer(byte[] packet,int offset){

        boolean reachedEndOfName=false;

        StringBuilder domainName=new StringBuilder();

        ArrayList<Byte> name=new ArrayList<>();

        boolean compressionFlag=false;
        byte[] uncompressedEncodedDomainName=null;

        while(!reachedEndOfName){

            byte contentLength=packet[offset++];

            if((contentLength & 0b11000000)>0){
                // TODO: Handle compression
                /**
                 * If first two bits of the contentLength is 11,
                 * This represents that domain name is compressed
                 */
                int pointerOffset=Short.toUnsignedInt(ByteBuffer.wrap(new byte[]{(byte)(contentLength ^ 0b11000000),packet[offset]}).getShort());
                uncompressedEncodedDomainName=getEncodedDomainNameFromPointer(packet,pointerOffset);
                compressionFlag=true;
                offset++;
                break;
            }
            name.add(contentLength);

            if(contentLength==0){
                //domainName.deleteCharAt(domainName.length()-1);
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

        byte[] ttl=new byte[4];
        System.arraycopy(packet,offset,ttl,0,4);

        offset+=4;

        byte[] rDLength=new byte[2];
        System.arraycopy(packet,offset,rDLength,0,2);

        offset+=2;

        byte[] rData=new byte[4];
        System.arraycopy(packet,offset,rData,0,4);

        return new DNSMessageAnswer(compressionFlag?uncompressedEncodedDomainName: Util.convertByteArrayListToByteArray(name),qType,qClass,ttl,rDLength,rData);
    }

    public static DNSMessageAnswer getDNSMessageAnswerLocally(DNSMessageQuestion dnsMessageQuestion){

        DNSMessageAnswer dnsMessageAnswer=new
                DNSMessageAnswer(
                        dnsMessageQuestion.getName(),
                        dnsMessageQuestion.getqType(),
                        dnsMessageQuestion.getqClass(),
                        new byte[4],
                        new byte[]{0,4},
                        new byte[]{0,0,0,0});

        return dnsMessageAnswer;
    }

    private static byte[] getEncodedDomainNameFromPointer(byte[] packet, int offset){
        boolean reachedEndOfName=false;

        ArrayList<Byte> name=new ArrayList<>();

        while(!reachedEndOfName){

            byte contentLength=packet[offset++];

            name.add(contentLength);

            if(contentLength==0){
                reachedEndOfName=true;
                continue;
            }

            while(contentLength-->0){
                name.add(packet[offset++]);
            }
        }
        return Util.convertByteArrayListToByteArray(name);
    }

}
