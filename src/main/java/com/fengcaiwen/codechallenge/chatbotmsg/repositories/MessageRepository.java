package com.fengcaiwen.codechallenge.chatbotmsg.repositories;

import com.fengcaiwen.codechallenge.chatbotmsg.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByCustomerIdAndLanguage(Long customerId, String language, Pageable paging);

    List<Message> findByCustomerId(Long customerId, Pageable paging);

    List<Message> findByLanguage(String language, Pageable paging);

    List<Message> findAll(Pageable paging);
}