package com.fengcaiwen.codechallenge.chatbotmsg.services;

import com.fengcaiwen.codechallenge.chatbotmsg.entity.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.repositories.MessageRepository;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RedisTemplate redisTemplate;

    public static final String KEY = "chat:";


    @Override
    public Response pushMessage(Long customerId, Long dialogId, MessageVO messageVO){
        if (ObjectUtils.isEmpty(messageVO.getLanguage())) {
            return Response.fail("Language not available");
        }
        if (ObjectUtils.isEmpty(messageVO.getText())) {
            return Response.fail("Text not available");
        }
        Message message = new Message();
        BeanUtils.copyProperties(messageVO, message);
        message.setCreateTime(LocalDateTime.now());
        message.setCustomerId(customerId);
        message.setDialogId(dialogId);

        //save data to redis
        HashOperations hash = redisTemplate.opsForHash();
        Object o = hash.get(KEY, dialogId);
        if (o == null) {
            o = new ArrayList<Message>();
        }
        List list = (List) o;
        list.add(message);
        hash.put(KEY, dialogId, list);
        return Response.success();
    }

    @Override
    public Response handleConsent(Boolean granted, Long dialogId) {

        //if consent is granted, read data from redis and save it to external db
        if (granted) {
            HashOperations hash = redisTemplate.opsForHash();
            Object o = hash.get(KEY, dialogId);
            List<Message> list = (List) o;
            if (!ObjectUtils.isEmpty(list)) {
                for (Message message : list) {
                    messageRepository.save(message);
                }
            }
        }
        //delete the redis cache
        HashOperations hash = redisTemplate.opsForHash();
        hash.delete(KEY, dialogId);
        return Response.success();
    }

    @Override
    public Response getMessages(Long customerId, String language, Integer page, Integer size) {

        //pagination and sorting configuration
        Integer pageSize = 100;
        Integer pageNo = 0;
        if (page != null && size != null){
            pageSize = size;
            pageNo = page;
        }
        else if (page != null){
            pageNo = page;
        }
        else if (size != null){
            pageSize = size;
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createTime").descending());

        //find by different parameters
        List<Message> list = Collections.emptyList();
        Object o = new Object();
        if (customerId != null && !ObjectUtils.isEmpty(language)){
            o = messageRepository.findByCustomerIdAndLanguage(customerId, language, paging);
        }
        else if (customerId != null){
            o = messageRepository.findByCustomerId(customerId, paging);
        }
        else if (!ObjectUtils.isEmpty(language)){
            o = messageRepository.findByLanguage(language, paging);
        }
        else {
            o = messageRepository.findAll(paging).get();
        }
        return Response.success(o);
    }

}
