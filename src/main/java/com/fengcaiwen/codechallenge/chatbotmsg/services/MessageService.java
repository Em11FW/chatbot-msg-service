package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;

public interface MessageService {

    Response handleConsent(Boolean granted, Long dialogID);

    Response getMessages(Long customerId, String language);

    Response pushMessage(Long customerID, Long dialogID, MessageVO messageVO);
}
