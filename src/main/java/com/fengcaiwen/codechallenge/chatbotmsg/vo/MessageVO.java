package com.fengcaiwen.codechallenge.chatbotmsg.vo;

public class MessageVO {
    private String text;
    private String Language;

    public MessageVO(String text, String language) {
        this.text = text;
        Language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}
