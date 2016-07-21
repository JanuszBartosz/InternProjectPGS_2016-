package com.pgs.soft.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
	private String userName = "bjanusz@pgs-soft.com";
	private String apiKey = "90a70d48-6289-44d2-a849-e323dcefc30b";
	
	public  String sendConfirmationEmail(String template, String to, String email, String password, String registrationToken)  {
        
		String data = "";
		
	    try{
	        data = "userName=" + URLEncoder.encode(userName, "UTF-8");
	        data += "&api_key=" + URLEncoder.encode(apiKey, "UTF-8");
	        data += "&template=" + URLEncoder.encode(template, "UTF-8");
	        data += "&to=" + URLEncoder.encode(to, "UTF-8");
	        data += "&merge_email=" + URLEncoder.encode(email, "UTF-8");
	        data += "&merge_password=" + URLEncoder.encode(password, "UTF-8");
	        data += "&merge_token=" + URLEncoder.encode(registrationToken, "UTF-8");
	    }
	    catch(Exception e){
            e.printStackTrace();
	    }
        return sendData(data);
    }

	private String sendData(String data)
	{
		String result = "";
		try {
	        URL url = new URL("https://api.elasticemail.com/mailer/send");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data);
	        wr.flush();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
	        result = rd.readLine();
	        wr.close();
	        rd.close();
		}
        catch(Exception e) {
            
            e.printStackTrace();
        }
		
        return result;
	}
	
}
