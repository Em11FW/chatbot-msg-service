package com.fengcaiwen.codechallenge.chatbotmsg.controller;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.services.MessageService;
import com.fengcaiwen.codechallenge.chatbotmsg.vo.MessageVO;
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
    public ResponseEntity<Map<String, String>> pushMessage (@RequestBody MessageVO messageVO,
                                                            @PathVariable("customerID") final Long customerID,
                                                            @PathVariable("dialogID") final Long dialogID){
        Long messageID = 0L;
        try {
            messageID = messageService.pushMessage(customerID, dialogID, messageVO);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        map.put("message", "pushed message " + messageID);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/consent/{dialogID}")
    public ResponseEntity<Map<String, String>> grantConsent(@RequestBody Boolean granted, @PathVariable("dialogID") final Long dialogID) {

        Map<String, String> map = new HashMap<>();
        map.put("message", messageService.handleConsent(granted, dialogID));
        return new ResponseEntity<>(map, HttpStatus.OK);


    }

    @GetMapping("/data")
    public List<Message> getMessages (@RequestParam(value = "customerId", required = false) final Long customerId,
                                      @RequestParam(value = "language", required = false) final String language){
        return messageService.getMessages(customerId, language);
    }


}
