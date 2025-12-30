import java.util.ArrayList;
import java.util.Arrays;

public class DNSMessageAnswer {

    /**
     * Its format
     *
     *   +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *     |                                               |
     *     /                                               /
     *     /                      NAME   Label sequence                  /
     *     |                                               |
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *     |                      TYPE   2 byte                  |
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *     |                     CLASS   2 byte                 |
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *     |                      TTL    4 byte                  |
     *     |                                               |
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     *     |                   RDLENGTH  2 byte                  |
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--|
     *     /                     RDATA   Variable based on type (4 bytes for  A type record)
     *     /
     *     /                                               /
     *     +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
     */

    private byte[] name;
    private byte[] qType;
    private byte[] qClass;
    private byte[] ttl;
    private byte[] rDLength;
    private byte[] rData;

    public DNSMessageAnswer(byte[] name, byte[] qType, byte[] qClass, byte[] ttl, byte[] rDLength, byte[] rData) {
        this.name = name;
        this.qType = qType;
        this.qClass = qClass;
        this.ttl = ttl;
        this.rDLength = rDLength;
        this.rData = rData;
    }

    public byte[] getDNSMessageAnswerInBytes(){
        ArrayList<byte[]> dnsMessageAnswerItemList=new ArrayList<>(Arrays.asList(name,qType,qClass,ttl,rDLength,rData));

        return Util.mergeByteArrayListToSingleByteArray(dnsMessageAnswerItemList);
    }

    /**
     * Getters and setters
     */

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getqType() {
        return qType;
    }

    public void setqType(byte[] qType) {
        this.qType = qType;
    }

    public byte[] getqClass() {
        return qClass;
    }

    public void setqClass(byte[] qClass) {
        this.qClass = qClass;
    }

    public byte[] getTtl() {
        return ttl;
    }

    public void setTtl(byte[] ttl) {
        this.ttl = ttl;
    }

    public byte[] getrDLength() {
        return rDLength;
    }

    public void setrDLength(byte[] rDLength) {
        this.rDLength = rDLength;
    }

    public byte[] getrData() {
        return rData;
    }

    public void setrData(byte[] rData) {
        this.rData = rData;
    }

}
