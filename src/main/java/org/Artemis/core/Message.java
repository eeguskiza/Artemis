package org.Artemis.core;

public class Message {
    private MessageType type;
    private String content;
    private User sender;
    private User recipient;

    public Message(MessageType type, String content, User sender, User recipient) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }
}
