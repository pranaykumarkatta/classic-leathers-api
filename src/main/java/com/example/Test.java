package com.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate template = new RestTemplate();
//        String res= template.getForObject(
//                "http://sms.autobysms.com/app/smsapi/index.php?key=4649EC3A869075&campaign=0&routeid=9&type=text&contacts=9505424696&%20senderid=SPTSMS&msg=Welcome!!&template_id=1707166619134631839",String.class);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println(cal.get(Calendar.MONTH));
    }
}
