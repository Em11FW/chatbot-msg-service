package com.fengcaiwen.codechallenge.chatbotmsg.repositories;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository {

    Long create(Long customerID, Long dialogID, String text, String language, LocalDateTime now);

    Integer deleteByConsent(Long dialogID);

    List<Message> findAll();

    List<Message> findByCustomerIdAndLanguage(Long customerId, String language);

    List<Message> findByCustomerId(Long customerId);

    List<Message> findByLanguage(String language);

    Integer createConsent(Long dialogID);
}