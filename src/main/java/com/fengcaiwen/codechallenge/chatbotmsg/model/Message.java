package com.fengcaiwen.codechallenge.chatbotmsg.model;

public class Message {

    private Long messageID;
    private Long customerID;
    private Long dialogID;
    private String text;
    private String language;
    private Boolean consent;

    public Message(Long messageID, Long customerID, Long dialogID, String text, String language, Boolean consent) {
        this.messageID = messageID;
        this.customerID = customerID;
        this.dialogID = dialogID;
        this.text = text;
        this.language = language;
        this.consent = consent;
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getDialogID() {
        return dialogID;
    }

    public void setDialogID(Long dialogID) {
        this.dialogID = dialogID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }
}
