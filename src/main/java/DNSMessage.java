import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class DNSMessage {

    private DNSMessageHeader dnsMessageHeader;
    private DNSMessageQuestion dnsMessageQuestion;
    private DNSMessageAnswer dnsMessageAnswer;
    private DNSMessageAuthority dnsMessageAuthority;

    public DNSMessage(
            DNSMessageHeader dnsMessageHeader,
            DNSMessageQuestion dnsMessageQuestion,
            DNSMessageAnswer dnsMessageAnswer,
            DNSMessageAuthority dnsMessageAuthority
    ) {
        this.dnsMessageHeader = dnsMessageHeader;
        this.dnsMessageQuestion = dnsMessageQuestion;
        this.dnsMessageAnswer = dnsMessageAnswer;
        this.dnsMessageAuthority = dnsMessageAuthority;
    }

    public byte[] getDNSMessageInBytes() throws Exception {
        byte[] message=new byte[512];

        byte[] dnsMessageHeader=getDnsMessageHeader().getDNSMessageHeaderInBytes();
        byte[] dnsMessageQuestion=getDnsMessageQuestion().getDNSMessageQuestionInBytes();

        ArrayList<byte[]> dnsMessageItemsList=new ArrayList<>();
        dnsMessageItemsList.add(dnsMessageHeader);
        dnsMessageItemsList.add(dnsMessageQuestion);

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

    public DNSMessageQuestion getDnsMessageQuestion() {
        return dnsMessageQuestion;
    }

    public void setDnsMessageQuestion(DNSMessageQuestion dnsMessageQuestion) {
        this.dnsMessageQuestion = dnsMessageQuestion;
    }

    public DNSMessageAnswer getDnsMessageAnswer() {
        return dnsMessageAnswer;
    }

    public void setDnsMessageAnswer(DNSMessageAnswer dnsMessageAnswer) {
        this.dnsMessageAnswer = dnsMessageAnswer;
    }

    public DNSMessageAuthority getDnsMessageAuthority() {
        return dnsMessageAuthority;
    }

    public void setDnsMessageAuthority(DNSMessageAuthority dnsMessageAuthority) {
        this.dnsMessageAuthority = dnsMessageAuthority;
    }

    // toString()
    @Override
    public String toString() {
        return "DNSMessage{" +
                "dnsMessageHeader=" + dnsMessageHeader +
                ", dnsMessageQuestion=" + dnsMessageQuestion +
                ", dnsMessageAnswer=" + dnsMessageAnswer +
                ", dnsMessageAuthority=" + dnsMessageAuthority +
                '}';
    }

}
