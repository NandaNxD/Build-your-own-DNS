public class DNSMessageQuestion {
    private byte[] dnsOriginalMessageQuestionInBytes;

    private byte[] name;
    private byte[] qType;
    private byte[] qClass;

    private String domainName;

    public DNSMessageQuestion(byte[] dnsOriginalMessageQuestionInBytes, byte[] name, byte[] qType, byte[] qClass,String domainName) {
        this.dnsOriginalMessageQuestionInBytes = dnsOriginalMessageQuestionInBytes;
        this.qType = qType;
        this.name = name;
        this.qClass = qClass;
        this.domainName=domainName;
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
