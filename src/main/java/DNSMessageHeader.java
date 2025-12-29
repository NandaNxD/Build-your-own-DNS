import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
@AllArgsConstructor
public class DNSMessageHeader {
    /**
     * DNS Message header is always 12 bytes long
     */

    /**
     * Its format **
     *
     * 16 bits: Packet Identifier ID
     * 
     * 1 bit : Query Response QR
     * 4 bits: Operation Code OPCODE
     * 1 bit : Authoritative Answer AA
     * 1 bit : Truncated Message TC
     * 1 bit : Recursion Desired RD
     * 1 bit : Recursion Available RA
     * 3 bits: Reserved Z
     * 4 bits: Response Code RCODE
     *
     * 16 bits: Question Count QDCOUNT
     * 16 bits: Answer Count ANCOUNT
     * 16 bits: Authority Count NSCOUNT
     * 16 bits: Additional Count ARCOUNT
     *
     */

    private byte[] dnsOriginalMessageHeaderInBytes;

     // To get packet ID
     private byte[] packetId;
     private byte[] flags;
     private byte[] questionCount;
     private byte[] answerCount;
     private byte[] authorityCount;
     private byte[] additionalCount;

     public int getPacketIdInInt(){
         return Short.toUnsignedInt(ByteBuffer.wrap(getPacketId()).getShort());
     }

    
}
