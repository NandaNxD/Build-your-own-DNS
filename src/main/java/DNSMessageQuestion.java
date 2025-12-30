import java.util.ArrayList;

public class DNSMessageQuestion {
    private byte[] dnsOriginalMessageQuestionInBytes;

    private byte[] name;
    private byte[] qType;
    private byte[] qClass;

    private String domainName;

    public DNSMessageQuestion(byte[] dnsOriginalMessageQuestionInBytes, byte[] name, byte[] qType, byte[] qClass,String domainName) {
        this.dnsOriginalMessageQuestionInBytes = dnsOriginalMessageQuestionInBytes;

        this.name = name;
        this.qType = qType;
        this.qClass = qClass;
        this.domainName=domainName;
    }

    public byte[] getDNSMessageQuestionInBytes() throws Exception{
        ArrayList<byte[]> dnsMessageQuestionItemList=new ArrayList<>();
        dnsMessageQuestionItemList.add(name);
        dnsMessageQuestionItemList.add(qType);
        dnsMessageQuestionItemList.add(qClass);

        return Util.mergeByteArrayListToSingleByteArray(dnsMessageQuestionItemList);
    }


    /**
     * Getters and setters
     */

    public byte[] getDnsOriginalMessageQuestionInBytes() {
        return dnsOriginalMessageQuestionInBytes;
    }

    public void setDnsOriginalMessageQuestionInBytes(byte[] dnsOriginalMessageQuestionInBytes) {
        this.dnsOriginalMessageQuestionInBytes = dnsOriginalMessageQuestionInBytes;
    }

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

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

}
