package com.fengcaiwen.codechallenge.chatbotmsg.controller;

import com.fengcaiwen.codechallenge.chatbotmsg.model.Message;
import com.fengcaiwen.codechallenge.chatbotmsg.services.MessageService;
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
    public ResponseEntity<Map<String, String>> pushMessage (@RequestBody Map<String, Object> messageMap,
                                                            @PathVariable("customerID") final String customerID,
                                                            @PathVariable("dialogID") final String dialogID) throws Exception {
        String text = (String) messageMap.get("text");
        String language = (String) messageMap.get("language");
        Integer messageID = 0;
        try {
            messageID = messageService.pushMessage(customerID, dialogID, text, language);
        } catch (Exception e){
            throw new Exception("failed to push message to db");
        }
        Map<String, String> map = new HashMap<>();
        map.put("message", "pushed message " + messageID);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
