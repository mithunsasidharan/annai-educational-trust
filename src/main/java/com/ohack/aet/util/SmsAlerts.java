package com.ohack.aet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.ohack.aet.model.TrainingEvent;
import com.ohack.aet.model.User;

public class SmsAlerts {

	public static final String HTTP_API = "http://cloud.fowiz.com/api/message_http_api.php";
	public static final String PASSCODE = "123456789";
	public static final String USERNAME = "uchittibabu";  

	public void sendSmsAlerts(TrainingEvent evnt, List<User> userList){
		  
		long toPhoneNumber = 0L;
		String myMessage = "";
		
		if(null != userList && userList.size() > 0){
			for(User user : userList){
				toPhoneNumber = user.getPhoneNo();
				String message = "Hi. You are eligible for the event "+ evnt.getEventName() +". Kindly contact the Annai Institutional Education Trust.";
				smsClient(toPhoneNumber, message);
			}
		}

		
	}
	
	public String smsClient(long phNo, String msg){
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(HTTP_API + "?username="+ USERNAME + "&phonenumber=" + phNo +"&message="+msg+"&passcode="+PASSCODE);
		HttpResponse response;
		StringBuffer responseStr = new StringBuffer();
		try {
			response = client.execute(request);
			BufferedReader rd;
			rd = new BufferedReader
					(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseStr.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (UnsupportedOperationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return responseStr.toString();
	}
}
