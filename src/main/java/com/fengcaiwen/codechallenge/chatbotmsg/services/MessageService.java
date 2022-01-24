package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;

import java.util.List;

public interface MessageService {

    Integer pushMessage(String customerID, String dialogID, String text, String language);
}
