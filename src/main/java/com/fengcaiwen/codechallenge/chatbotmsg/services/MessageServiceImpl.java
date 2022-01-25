package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.repositories.MessageRepository;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;


    @Override
    public Response pushMessage(Long customerID, Long dialogID, MessageVO messageVO){
        if (ObjectUtils.isEmpty(messageVO.getLanguage())) {
            return Response.fail("Language not available");
        }
        if (ObjectUtils.isEmpty(messageVO.getText())) {
            return Response.fail("Text not available");
        }
        messageRepository.create(customerID, dialogID, messageVO.getText(), messageVO.getLanguage(), LocalDateTime.now());
        return Response.success();
    }

    @Override
    public Response handleConsent(Boolean granted, Long dialogID) {
        if (granted){
            messageRepository.createConsent(dialogID);
        }
        else {
            messageRepository.deleteByConsent(dialogID);
        }
        return Response.success();
    }

    @Override
    public Response getMessages(Long customerId, String language) {
        List<Message> list = Collections.emptyList();
        if (customerId != null && !ObjectUtils.isEmpty(language)){
            list = messageRepository.findByCustomerIdAndLanguage(customerId, language);
        }
        else if (customerId != null && ObjectUtils.isEmpty(language)){
            list = messageRepository.findByCustomerId(customerId);
        }
        else if (customerId == null && !ObjectUtils.isEmpty(language)){
            list = messageRepository.findByLanguage(language);
        }
        else if (customerId == null && ObjectUtils.isEmpty(language)) {
            list = messageRepository.findAll();
        }
        return Response.success(list);
    }

}
