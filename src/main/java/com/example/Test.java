package com.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.itextpdf.html2pdf.HtmlConverter;

public class Test {
    public static void main(String[] args) throws Exception {
        String s = "A01|BL\n" +
                "A01|BR\n" +
                "A01|TN\n" +
                "A02|BL\n" +
                "A02|BR\n" +
                "A02|TN\n" +
                "A03|BL\n" +
                "A03|BR\n" +
                "A03|TN\n" +
                "A04|BL\n" +
                "A04|BR\n" +
                "A04|TN";

        List l = new ArrayList();
        l.addAll(Arrays.asList(s.split("\n")));
        l.forEach(o -> {
            System.out.println(o+"39");
            System.out.println(o+"40");
            System.out.println(o+"41");
            System.out.println(o+"42");
            System.out.println(o+"43");
            System.out.println(o+"44");
            System.out.println(o+"45");
            System.out.println(o+"46");
            System.out.println(o+"47");
        });

    }
}
