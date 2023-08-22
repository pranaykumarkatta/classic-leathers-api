package com.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.html2pdf.HtmlConverter;

public class Test {
    public static void main(String[] args) throws Exception {
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate template = new RestTemplate();
//        String res= template.getForObject(
//                "http://sms.autobysms.com/app/smsapi/index.php?key=4649EC3A869075&campaign=0&routeid=9&type=text&contacts=9505424696&%20senderid=SPTSMS&msg=Welcome!!&template_id=1707166619134631839",String.class);
        HtmlConverter.convertToPdf("<h1>Hello</h1>\"\n" +
                "\t\t\t+ \"<p>This was created using iText</p>\"\n" +
                "\t\t\t+ \"<a href='hmkcode.com'>hmkcode.com</a>", new FileOutputStream("D:\\WORKSPACE\\classic-leathers-ui\\Index.pdf"));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println(cal.get(Calendar.MONTH));
    }
}
