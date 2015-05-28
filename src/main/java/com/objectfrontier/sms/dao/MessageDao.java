package com.objectfrontier.sms.dao;

import java.util.List;

import com.objectfrontier.sms.model.Message;


public interface MessageDao {

    void persistMessage(List<Message> messages);

    //void persistMessageStatus(String externalId, String phoneNo, String status, String deliveredTS, String cause);
}
