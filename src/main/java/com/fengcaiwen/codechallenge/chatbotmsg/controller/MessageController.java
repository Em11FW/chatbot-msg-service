package com.fengcaiwen.codechallenge.chatbotmsg.controller;

import com.fengcaiwen.codechallenge.chatbotmsg.services.MessageService;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/data/{customerId}/{dialogId}")
    public Response pushMessage (@RequestBody MessageVO messageVO,
                                 @PathVariable("customerId") final Long customerId,
                                 @PathVariable("dialogId") final Long dialogId){
        return messageService.pushMessage(customerId, dialogId, messageVO);
    }

    @PostMapping("/consents/{dialogId}")
    public Response grantConsent(@RequestBody Boolean granted, @PathVariable("dialogId") final Long dialogId) {
        return messageService.handleConsent(granted, dialogId);
    }

    @GetMapping("/data")
    public Response getMessages (@RequestParam(value = "customerId", required = false) final Long customerId,
                                 @RequestParam(value = "language", required = false) final String language,
                                 @RequestParam(value = "page", required = false) final Integer page,
                                 @RequestParam(value = "size", required = false) final Integer size){
        return messageService.getMessages(customerId, language, page, size);
    }


}
