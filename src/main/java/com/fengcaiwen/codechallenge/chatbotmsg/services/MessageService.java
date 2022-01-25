package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;

import java.util.List;

public interface MessageService {

    Long pushMessage(Long customerID, Long dialogID, String text, String language);

    String handleConsent(Boolean granted, Long dialogID);

    List<Message> getMessages(Long customerId, String language);
}
