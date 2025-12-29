import java.util.Objects;

public class DNSMessage {

    private DNSMessageHeader dnsMessageHeader;
    private DNSMessageQuestion dnsMessageQuestion;
    private DNSMessageAnswer dnsMessageAnswer;
    private DNSMessageAuthority dnsMessageAuthority;

    // All-args constructor
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

    // Getters & Setters
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
