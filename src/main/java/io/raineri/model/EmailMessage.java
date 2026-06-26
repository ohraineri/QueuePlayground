package io.raineri.model;

public class EmailMessage {
    private String to;
    private String subject;
    private String body;

    public EmailMessage(String to,String subject,String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }
}
