package com.waffle.infra.notification.models;

public class Message {
    private static final int PREVIEW_LENGTH = 100;

    String subject;
    String body;

    private Message() {}

    public static Message with(String subject, String body) {
        Message m = new Message();
        m.subject = subject;
        m.body = body;
        return m;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getPreview() {
        String preview = body.substring(0, Math.min(body.length(), PREVIEW_LENGTH));
        return body.length() <= PREVIEW_LENGTH ? preview : (preview + "...");
    }
}
