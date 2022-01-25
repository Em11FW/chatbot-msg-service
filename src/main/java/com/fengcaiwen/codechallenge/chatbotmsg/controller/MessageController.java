package com.fengcaiwen.codechallenge.chatbotmsg.controller;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.services.MessageService;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/data/{customerID}/{dialogID}")
    public Response pushMessage (@RequestBody MessageVO messageVO,
                                 @PathVariable("customerID") final Long customerID,
                                 @PathVariable("dialogID") final Long dialogID){
        return messageService.pushMessage(customerID, dialogID, messageVO);
    }

    @PostMapping("/consent/{dialogID}")
    public Response grantConsent(@RequestBody Boolean granted, @PathVariable("dialogID") final Long dialogID) {
        return messageService.handleConsent(granted, dialogID);
    }

    @GetMapping("/data")
    public Response getMessages (@RequestParam(value = "customerId", required = false) final Long customerId,
                                 @RequestParam(value = "language", required = false) final String language,
                                 @RequestParam(value = "page", required = false) final Integer page,
                                 @RequestParam(value = "size", required = false) final Integer size){
        return messageService.getMessages(customerId, language, page, size);
    }


}
