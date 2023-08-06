package com.classicLeathers.classicLeathersTool;

import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import okhttp3.*;

import java.io.IOException;

public class MessagingUtils {
    private final OkHttpClient httpClient = new OkHttpClient();

    public void sendWhatsAppMessage(RetailSalesEntryDto retailSalesEntryDto) throws Exception {
        String msgBody = "Dear "+retailSalesEntryDto.getCustomerName()+", Thank's for your purchase @CLASSIC LEATHERS. \\n" +
                "Product Details:\\n  \\n Keep shopping with us!! Have a great day";

        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url("http://pvwhatsapp.autobysms.com/wapp/api/send?apikey=a16243811b814c01b426febcdd5a69e7&mobile=8190839579&msg="+msgBody)
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String s = response.body().string();
            if (s.contains("\"statuscode\":200")) {
                System.out.println("Message sent......!");
                System.out.println(s);
            } else {
                System.out.println(s);
            }
        }

    }
}
