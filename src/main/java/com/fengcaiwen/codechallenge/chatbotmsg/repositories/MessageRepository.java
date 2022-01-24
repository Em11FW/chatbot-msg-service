package com.fengcaiwen.codechallenge.chatbotmsg.repositories;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;

import java.util.List;

public interface MessageRepository {

    Integer create(String customerID, String dialogID, String text, String language);

    Integer updateConsent(String dialogID);

    Integer deleteByConsent(String dialogID);
}