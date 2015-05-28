package com.objectfrontier.sms.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.objectfrontier.sms.model.Messages;
import com.objectfrontier.sms.service.MessageService;

@Component
@Path("message")
public class MessageRestService {

    @Autowired
    private MessageService messageService;

    @POST
    @Path("/send-message")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public void sendMessage(@RequestBody Messages messages) {
        messageService.sendMessage(messages.getMessages());
    }

    /**
     * Real Time Delivery Report, understand the response param from GupShup
     * 
     * @param externalId 
     * 		- Unique ID for each message.
     * 
     * @param phoneNo
     * 		-Phone number of the receiver.
     * 
     * @param status
     * 		- Final status of the message, possible values are SUCCESS, FAILURE or UNKNOWN.
     * 			1. SUCCESS - SUCCESS
     * 			2. FAIL 
     * 				2.1 ABSENT_SUBSCRIBER
     * 				2.2 UNKNOWN_SUBSCRIBER
     * 				2.3 BLOCKED_MASK
     * 				2.4 SYSTEM_FAILURE
     * 				2.5 CALL_BARRED
     * 				2.6 SERVICE_DOWN
     * 				2.7 OTHER
     * 				2.8 DND_FAIL
     * 				2.9 DND_TIMEOUT
     * 				2.10 OUTSIDE_WORKING_HOUR
     * 			3. SUBMITTED - SMSCTIMEOUT
     * 
     * @param deliveredTS 
     * 		- Time of delivery of message as LONG number.
     * 
     * @param cause
     * 		- This is the response you will get depending on the status. Various statuses and their explanation are below.
     * 			1. ABSENT_SUBSCRIBER - When the service provider fails to reach the member/subscriber, this value is passed.
     *  		2. UNKNOWN_SUBSCRIBER - Unknown/invalid number.
     * 			3. BLOCKED_MASK - Mask is blocked by SMS GupShup.
     * 			4. SYSTEM_FAILURE - Failure due to a problem in the Operator’s systems (Originating or Destination Operator)
     * 			5. CALL_BARRED - Subscriber has some kind of call barring active on the number due to which messages from unknown sources are blocked.
     * 			6. SERVICE_DOWN - Operator service is temporarily is down.
     * 			7. OTHER - Message that are sent but could not be delivered for reasons that don’t fall under any mentioned category
     * 			8. DND_FAIL - Number is either in DND or Blocked due to being in DND or a complaint.
     * 			9. DND_TIMEOUT - Latest DND status is not available in time for the message to be sent. (Max 1 day)
     * 			10. OUTSIDE_WORKING_HOUR - Message sending is outside mentioned working hours.
     */
    @GET
    @Path("/get-feedback")
    @Produces({ MediaType.APPLICATION_JSON })
    public void getGupShupMessageStatus(
    		@QueryParam("externalId") String externalId,
    		@QueryParam("phoneNo") String phoneNo,
    		@QueryParam("status") String status,
    		@QueryParam("deliveredTS") String deliveredTS,
    		@QueryParam("cause") String cause) {

    	System.out.println("externalId : " + externalId);
    	System.out.println("phoneNo : " + phoneNo);
    	System.out.println("status : " + status);
    	System.out.println("deliveredTS : " + deliveredTS);
    	System.out.println("cause : " + cause);
    	
    	//messageService.processGupShupMessageStatus(externalId, phoneNo, status, deliveredTS, cause);
    }

    @GET
    @Path("/receive-message")
    @Produces({ MediaType.APPLICATION_JSON })
    public void receiveMessage(
    		@QueryParam("phonecode") String phonecode,
    		@QueryParam("keyword") String keyword,
    		@QueryParam("location") String location,
    		@QueryParam("carrier") String carrier,
    		@QueryParam("content") String content,
    		@QueryParam("msisdn") String msisdn,
    		@QueryParam("timestamp") String timestamp) {

    	System.out.println("phonecode : " + phonecode);
    	System.out.println("keyword : " + keyword);
    	System.out.println("location : " + location);
    	System.out.println("carrier : " + carrier);
    	System.out.println("content : " + content);
    	System.out.println("msisdn : " + msisdn);
    	System.out.println("timestamp : " + timestamp);
    }
    
    @GET
    @Path("/receive-horizon-message")
    @Produces({ MediaType.APPLICATION_JSON })
    public void receiveHorizonMessage(
    		@QueryParam("from") String from,
    		@QueryParam("message") String message) {

    	System.out.println("from : " + from);
    	System.out.println("message : " + message);
    }
}
