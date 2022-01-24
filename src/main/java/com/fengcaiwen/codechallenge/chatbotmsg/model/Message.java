package com.fengcaiwen.codechallenge.chatbotmsg.model;

public class Message {

    private Integer messageID;
    private String customerID;
    private String dialogID;
    private String text;
    private String language;
    private Boolean consent;

    public Message(Integer messageID, String customerID, String dialogID, String text, String language, Boolean consent) {
        this.messageID = messageID;
        this.customerID = customerID;
        this.dialogID = dialogID;
        this.text = text;
        this.language = language;
        this.consent = consent;
    }

    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDialogID() {
        return dialogID;
    }

    public void setDialogID(String dialogID) {
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
