package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;

import java.util.List;

public interface MessageService {

    String handleConsent(Boolean granted, Long dialogID);

    List<Message> getMessages(Long customerId, String language);

    Long pushMessage(Long customerID, Long dialogID, MessageVO messageVO);
}
