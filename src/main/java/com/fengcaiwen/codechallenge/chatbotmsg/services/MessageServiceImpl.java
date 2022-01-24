package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;


    @Override
    public Integer pushMessage(String customerID, String dialogID, String text, String language) {
        return messageRepository.create(customerID, dialogID, text, language);
    }

}
