[![progress-banner](https://backend.codecrafters.io/progress/dns-server/e90b737c-6dc2-451e-a755-3988ab9378c8)](https://app.codecrafters.io/users/codecrafters-bot?r=2qF)

# DNS Server - Java Implementation

## Project Overview

This is a **DNS (Domain Name System) server** implementation written in Java. The project is part of the [CodeCrafters DNS Server Challenge](https://app.codecrafters.io/courses/dns-server/overview).

A DNS server is responsible for:
- **Parsing DNS queries**: Receiving DNS packets from clients and extracting domain name requests
- **Processing DNS records**: Handling various DNS record types (A, AAAA, CNAME, etc.)
- **Responding to queries**: Sending back DNS responses with the requested domain information
- **Forwarding requests**: Optionally forwarding queries to upstream DNS resolvers
- **Implementing DNS protocol**: Following the DNS protocol specifications for packet format and communication

### Key Features
- Listens on port 2053 for incoming DNS queries
- Parses and constructs DNS messages according to RFC standards
- Supports DNS query forwarding to upstream resolvers via `--resolver` mode
- Handles DNS message headers, questions, answers, and authority sections

## Architecture

The project is structured with clean separation of concerns:

### Core Components

- **Main.java** - Entry point and DNS server implementation
  - Listens for incoming UDP packets on port 2053
  - Handles both direct response mode and forwarding mode
  - Routes DNS queries through the message pipeline

- **DNSMessage.java** - DNS packet structure and serialization
  - Represents a complete DNS message with header, questions, answers, and authority records
  - Handles conversion to/from byte arrays for network transmission

- **DNSMessageParser.java** - DNS packet parsing
  - Parses incoming byte arrays into structured DNS message objects
  - Decodes DNS packet format from wire format

- **DNSMessageHeader.java** - DNS message header handling
  - Manages DNS header fields: ID, flags (QR, OPCODE, AA, TC, RD, RA, RCODE), question/answer/authority/additional record counts

- **DNSMessageQuestion.java** - DNS query question section
  - Represents individual DNS questions with domain name and query type

- **DNSMessageAnswer.java** - DNS answer records
  - Stores DNS answer records with domain, type, class, TTL, and resource data

- **DNSMessageAuthority.java** - DNS authority records
  - Handles authority/nameserver records in DNS responses

- **Util.java** - Helper utilities
  - Common utility functions for byte array manipulation and data conversion

### Communication Flow

```
Client DNS Query
       ↓
   [Main.java - Listen on port 2053]
       ↓
   [DNSMessageParser - Parse incoming bytes]
       ↓
   [DNSMessage - Structured representation]
       ↓
   [Process Query / Forward if --resolver mode]
       ↓
   [DNSMessage - Build response]
       ↓
   [Serialize to bytes]
       ↓
   Send Response to Client
```

## Reference

- [DNS Protocol Guide](https://github.com/EmilHernvall/dnsguide/blob/b52da3b32b27c81e5c6729ac14fe01fef8b1b593/chapter1.md)
- [CodeCrafters DNS Challenge](https://app.codecrafters.io/courses/dns-server/overview)

## Testing

Test the DNS server with:
```sh
dig @127.0.0.1 -p 2053 +noedns google.com
```

## Getting Started

**Note**: If you're viewing this repo on GitHub, head over to
[codecrafters.io](https://codecrafters.io) to try the challenge.

### Stage 1

The entry point for your `your_program.sh` implementation is in
`src/main/java/Main.java`. Study and uncomment the relevant code, and push your
changes to pass the first stage:

```sh
git commit -am "pass 1st stage" # any msg
git push origin master
```

### Stage 2 & Beyond

1. Ensure you have `mvn` installed locally
2. Run `./your_program.sh` to run your program, which is implemented in
   `src/main/java/Main.java`.
3. Commit your changes and run `git push origin master` to submit your solution
   to CodeCrafters. Test output will be streamed to your terminal.
