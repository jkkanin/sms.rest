package com.objectfrontier.sms.service;

import java.util.List;

import com.objectfrontier.sms.model.Message;


public interface MessageService {

    void sendMessage(List<Message> messages);

    //void processGupShupMessageStatus(String externalId, String phoneNo, String status, String deliveredTS, String cause);
}
