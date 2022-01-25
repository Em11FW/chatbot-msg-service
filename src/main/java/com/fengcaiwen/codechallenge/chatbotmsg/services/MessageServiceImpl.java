package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.repositories.MessageRepository;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
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
    public Long pushMessage(Long customerID, Long dialogID, MessageVO messageVO){
        return messageRepository.create(customerID, dialogID, messageVO.getText(), messageVO.getLanguage(), LocalDateTime.now());
    }

    @Override
    public String handleConsent(Boolean granted, Long dialogID) {
        return granted? String.format("consent granted for dialog %s : %s", dialogID, messageRepository.createConsent(dialogID)):
                String.format("consent denied, deleted %d messages in dialog %s", messageRepository.deleteByConsent(dialogID), dialogID);
    }

    @Override
    public List<Message> getMessages(Long customerId, String language) {
        if (customerId != null && !ObjectUtils.isEmpty(language)){
            return messageRepository.findByCustomerIdAndLanguage(customerId, language);
        }
        if (customerId != null && ObjectUtils.isEmpty(language)){
            return messageRepository.findByCustomerId(customerId);
        }
        if (customerId == null && !ObjectUtils.isEmpty(language)){
            return messageRepository.findByLanguage(language);
        }
        if (customerId == null && ObjectUtils.isEmpty(language)) {
            return messageRepository.findAll();
        }
        return Collections.emptyList();
    }

}
