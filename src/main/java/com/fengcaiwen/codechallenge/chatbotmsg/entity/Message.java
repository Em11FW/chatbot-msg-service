package com.fengcaiwen.codechallenge.chatbotmsg.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "message_info")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "dialog_id")
    private Long dialogId;
    @Column(name = "text")
    private String text;
    @Column(name = "language")
    private String language;
    @Column(name = "created_at")
    private LocalDateTime createTime;

    public Message() {
    }

    public Message(Long messageId, Long customerId, Long dialogId, String text, String language, LocalDateTime createTime) {
        this.messageId = messageId;
        this.customerId = customerId;
        this.dialogId = dialogId;
        this.text = text;
        this.language = language;
        this.createTime = createTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
