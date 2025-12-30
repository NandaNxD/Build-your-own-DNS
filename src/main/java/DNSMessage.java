import java.util.ArrayList;
import java.util.Arrays;

public class DNSMessage {

    private DNSMessageHeader dnsMessageHeader;
    private DNSMessageQuestion[] dnsMessageQuestionList;
    private DNSMessageAnswer[] dnsMessageAnswerList;
    private DNSMessageAuthority[] dnsMessageAuthorityList;

    public DNSMessage(
            DNSMessageHeader dnsMessageHeader,
            DNSMessageQuestion[] dnsMessageQuestionList,
            DNSMessageAnswer[] dnsMessageAnswerList,
            DNSMessageAuthority[] dnsMessageAuthorityList
    ) {
        this.dnsMessageHeader = dnsMessageHeader;
        this.dnsMessageQuestionList = dnsMessageQuestionList;
        this.dnsMessageAnswerList = dnsMessageAnswerList;
        this.dnsMessageAuthorityList = dnsMessageAuthorityList;
    }

    public byte[] getDNSMessageInBytes() throws Exception {
        byte[] message=new byte[512];

        byte[] dnsMessageHeader=getDnsMessageHeader().getDNSMessageHeaderInBytes();

        byte[] dnsMessageQuestionList= Util.mergeByteArrayListToSingleByteArray(
                Arrays.stream(getDnsMessageQuestionList()).map(DNSMessageQuestion::getDNSMessageQuestionInBytes).toList());

        byte[] dnsMessageAnswerList=Util.mergeByteArrayListToSingleByteArray(
                Arrays.stream(getDnsMessageAnswerList()).map(DNSMessageAnswer::getDNSMessageAnswerInBytes).toList());


        ArrayList<byte[]> dnsMessageItemsList=new ArrayList<>(Arrays.asList(dnsMessageHeader,dnsMessageQuestionList,dnsMessageAnswerList));

        byte[] mergedDNSMessage=Util.mergeByteArrayListToSingleByteArray(dnsMessageItemsList);

        System.arraycopy(mergedDNSMessage,0,message,0,mergedDNSMessage.length);

        return message;
    }

    /**
     * Getters and setters
     */
    public DNSMessageHeader getDnsMessageHeader() {
        return dnsMessageHeader;
    }

    public void setDnsMessageHeader(DNSMessageHeader dnsMessageHeader) {
        this.dnsMessageHeader = dnsMessageHeader;
    }

    public DNSMessageQuestion[] getDnsMessageQuestionList() {
        return dnsMessageQuestionList;
    }

    public void setDnsMessageQuestionList(DNSMessageQuestion[] dnsMessageQuestionList) {
        this.dnsMessageQuestionList = dnsMessageQuestionList;
    }

    public DNSMessageAnswer[] getDnsMessageAnswerList() {
        return dnsMessageAnswerList;
    }

    public void setDnsMessageAnswerList(DNSMessageAnswer[] dnsMessageAnswerList) {
        this.dnsMessageAnswerList = dnsMessageAnswerList;
    }

    public DNSMessageAuthority[] getDnsMessageAuthorityList() {
        return dnsMessageAuthorityList;
    }

    public void setDnsMessageAuthorityList(DNSMessageAuthority[] dnsMessageAuthorityList) {
        this.dnsMessageAuthorityList = dnsMessageAuthorityList;
    }

    // toString()
    @Override
    public String toString() {
        return "DNSMessage{" +
                "dnsMessageHeader=" + dnsMessageHeader +
                ", dnsMessageQuestion=" + Arrays.toString(dnsMessageQuestionList) +
                ", dnsMessageAnswer=" + Arrays.toString(dnsMessageAnswerList) +
                ", dnsMessageAuthority=" + Arrays.toString(dnsMessageAuthorityList) +
                '}';
    }

}
