package com.example.whatsapp;

public class ModelMessage {
    private String senderId;
    private String message;
    private String messageId;

    public ModelMessage(){}
    public ModelMessage(String senderId, String message, String messageId) {
        this.senderId = senderId;
        this.message = message;
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }
}
