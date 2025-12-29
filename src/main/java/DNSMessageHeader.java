import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

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

    // Header fields (each 16 bits / 2 bytes)
    private byte[] packetId;
    private byte[] flags;
    private byte[] questionCount;
    private byte[] answerCount;
    private byte[] authorityCount;
    private byte[] additionalCount;

    // All-args constructor
    public DNSMessageHeader(
            byte[] dnsOriginalMessageHeaderInBytes,
            byte[] packetId,
            byte[] flags,
            byte[] questionCount,
            byte[] answerCount,
            byte[] authorityCount,
            byte[] additionalCount
    ) {
        this.dnsOriginalMessageHeaderInBytes = dnsOriginalMessageHeaderInBytes;
        this.packetId = packetId;
        this.flags = flags;
        this.questionCount = questionCount;
        this.answerCount = answerCount;
        this.authorityCount = authorityCount;
        this.additionalCount = additionalCount;
    }

    public int getPacketIdInInt() {
        return Short.toUnsignedInt(
                ByteBuffer.wrap(getPacketId()).getShort()
        );
    }

    // Getters & Setters
    public byte[] getDnsOriginalMessageHeaderInBytes() {
        return dnsOriginalMessageHeaderInBytes;
    }

    public void setDnsOriginalMessageHeaderInBytes(byte[] dnsOriginalMessageHeaderInBytes) {
        this.dnsOriginalMessageHeaderInBytes = dnsOriginalMessageHeaderInBytes;
    }

    public byte[] getPacketId() {
        return packetId;
    }

    public void setPacketId(byte[] packetId) {
        this.packetId = packetId;
    }

    public byte[] getFlags() {
        return flags;
    }

    public void setFlags(byte[] flags) {
        this.flags = flags;
    }

    public byte[] getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(byte[] questionCount) {
        this.questionCount = questionCount;
    }

    public byte[] getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(byte[] answerCount) {
        this.answerCount = answerCount;
    }

    public byte[] getAuthorityCount() {
        return authorityCount;
    }

    public void setAuthorityCount(byte[] authorityCount) {
        this.authorityCount = authorityCount;
    }

    public byte[] getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(byte[] additionalCount) {
        this.additionalCount = additionalCount;
    }


    // toString()
    @Override
    public String toString() {
        return "DNSMessageHeader{" +
                "dnsOriginalMessageHeaderInBytes=" + Arrays.toString(dnsOriginalMessageHeaderInBytes) +
                ", packetId=" + Arrays.toString(packetId) +
                ", flags=" + Arrays.toString(flags) +
                ", questionCount=" + Arrays.toString(questionCount) +
                ", answerCount=" + Arrays.toString(answerCount) +
                ", authorityCount=" + Arrays.toString(authorityCount) +
                ", additionalCount=" + Arrays.toString(additionalCount) +
                '}';
    }

}
