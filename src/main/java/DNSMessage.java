import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DNSMessage {
    private DNSMessageHeader dnsMessageHeader;
    private DNSMessageQuestion dnsMessageQuestion;
    private DNSMessageAnswer dnsMessageAnswer;
    private DNSMessageAuthority dnsMessageAuthority;

}
