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
    public Response getMessages(Long customerId, String language, Integer page, Integer size) {

        //pagination configuration
        String limit = "ALL";
        String offset = "0";
        if (page != null && size != null){
            limit = String.valueOf(size);
            offset = String.valueOf((page - 1) * size);
        }
        else if (page != null){
            limit = "100";
            offset = String.valueOf((page - 1) * 100);
        }
        else if (size != null){
            limit = String.valueOf(size);
        }


        //find by different parameters
        List<Message> list = Collections.emptyList();
        if (customerId != null && !ObjectUtils.isEmpty(language)){
            list = messageRepository.findByCustomerIdAndLanguage(customerId, language, limit, offset);
        }
        else if (customerId != null && ObjectUtils.isEmpty(language)){
            list = messageRepository.findByCustomerId(customerId, limit, offset);
        }
        else if (customerId == null && !ObjectUtils.isEmpty(language)){
            list = messageRepository.findByLanguage(language, limit, offset);
        }
        else if (customerId == null && ObjectUtils.isEmpty(language)) {
            list = messageRepository.findAll(limit, offset);
        }
        return Response.success(list);
    }

}
