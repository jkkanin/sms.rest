package com.objectfrontier.sms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.objectfrontier.sms.config.MessageDSTxManager;
import com.objectfrontier.sms.dao.MessageDao;
import com.objectfrontier.sms.model.Message;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    @MessageDSTxManager
    public void sendMessage(List<Message> messages) {

    	// Replace with your username
    	String user = "jkkanin";

    	// Replace with your API KEY (We have sent API KEY on activation email, also available on panel)
    	String apikey = "l2IphKVMchdCgXRKfUwf";

    	// Replace if you have your own Sender ID, else donot change
    	String senderid = "WEBSMS";

    	// For Plain Text, use "txt" ; for Unicode symbols or regional Languages like hindi/tamil/kannada use "uni"
    	String type="txt";

    	//Prepare Url
    	URLConnection myURLConnection=null;
    	URL myURL=null;
    	BufferedReader reader=null;

    	//Send SMS API
    	String mainUrl="http://smshorizon.co.in/api/sendsms.php?";

    	for (Message message : messages) {
			
    		// Replace with the destination mobile Number to which you want to send sms
    		String mobile = message.getNumber();

    		// Replace with your Message content
    		String content = message.getMessage();

//        	//encoding message 
//        	String encoded_message=URLEncoder.encode(message);

    		StringBuilder sbPostData= new StringBuilder(mainUrl);
    		sbPostData.append("user="+user); 
    		sbPostData.append("&apikey="+apikey);
    		try {
				sbPostData.append("&message="+URLEncoder.encode(content, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
    		sbPostData.append("&mobile="+mobile);
    		sbPostData.append("&senderid="+senderid);
    		sbPostData.append("&type="+type);
    		
    		//final string
    		mainUrl = sbPostData.toString();
    		try {
    			
    			//prepare connection
    			myURL = new URL(mainUrl);
    			myURLConnection = myURL.openConnection();
    			myURLConnection.connect();
    			reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
    			
    			//reading response 
    			String response;
    			while ((response = reader.readLine()) != null) {
    				//print response 
    				System.out.println(response);
    			}
    			
    			//finally close connection
    			reader.close();
    			
    		} catch (IOException e) { 
    			e.printStackTrace();
    		}
		}

    	//Prepare parameter string 
    	/*String createdExcel = ExcelGen.createExcel(messages);

    	if (createdExcel == null) {
    		return;
    	}
        
    	try {

    		HttpClient client = new HttpClient();
    		PostMethod method = new PostMethod("http://enterprise.smsgupshup.com/GatewayAPI/rest");
    		File f = new File(createdExcel);
    		Part[] parts ={
    				new StringPart("method", "xlsUpload"),
    				new StringPart("userid", "2000143893"),
    				new StringPart("password", "Ticket4u"),
    				new StringPart("filetype", "xls"),
    				new StringPart("v", "1.1"),
    				new StringPart("auth_scheme", "PLAIN"),
    				new FilePart(f.getName(), f)
    		};
    		method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
    		int statusCode = client.executeMethod(method);
    		System.out.println(method.getResponseBodyAsString());
    		System.out.println(statusCode);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

        messageDao.persistMessage(messages);*/
    }

	/*@Override
	@MessageDSTxManager
    public void processGupShupMessageStatus(String externalId, String phoneNo, String status, String deliveredTS, String cause) {
		messageDao.persistMessageStatus(externalId, phoneNo, status, deliveredTS, cause);
    }*/
}
