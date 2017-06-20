package com.waoss.moneypool.client.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.login.LoginActivity;
import com.waoss.moneypool.client.notification.NotificationHandler;
import com.waoss.moneypool.client.notification.NotificationTestActivity;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;

/**
 * Created by Rohan on 17-06-2017.
 */
public class NotificationService extends IntentService {
    String response;
    String currentResponse;
    protected static final String URL = "http://moneypool.localtunnel.me/response.jsp";

    public NotificationService() {
        super("Notification Service");
        Log.i("service", "Constructor called");
//        Toast.makeText(NotificationTestActivity.getContext(), "Constructor", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("service", "In onHandleIntent");
        String prev = "-1";
        while (true) {
            currentResponse = this.getResponse(URL);
            currentResponse = currentResponse.trim();
            if (!currentResponse.equalsIgnoreCase("null") && !prev.equals(currentResponse)) {
                NotificationHandler.showNotification(this, R.drawable.ic_stat_name, "Transaction occured", currentResponse);
            }
            prev = currentResponse;
        }

        //Toast.makeText(NotificationTestActivity.getContext(), "onHandleIntent", Toast.LENGTH_SHORT).show();
    }

    protected String getResponse(String string) {
        Log.i("service", "In getResponse");
        String result = "";
        ArrayList<String> arrayList;
        try {
            String url = string;
            HttpURLConnection urlConnection = this.getConnection(url);
            while (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                urlConnection = this.getConnection(string);
            }
            arrayList = this.parse(urlConnection.getInputStream());
            result = finalise(arrayList);
            NotificationService.this.response = result;
            //Toast.makeText(NotificationTestActivity.getContext(), "Result returned", Toast.LENGTH_SHORT).show();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }


    protected HttpURLConnection getConnection(String url) throws IOException {
        //Toast.makeText(NotificationTestActivity.getContext(), "In search of connection", Toast.LENGTH_SHORT).show();
        Log.i("service", "In getConnection");
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection;
    }

    protected ArrayList<String> parse(InputStream inputStream) throws IOException {
        //Toast.makeText(NotificationTestActivity.getContext(), "Parsing", Toast.LENGTH_SHORT).show();
        Log.i("service", "In parse");
        String string = IOUtils.toString(inputStream, "utf-8");
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        ArrayList<String> result = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            result.add(stringTokenizer.nextToken());
        }
        return result;
    }

    protected String finalise(ArrayList<String> list) {

        String res = "null";
        try {
            switch (list.get(list.size() - 1)) {
                case "B":
                    if (LoginActivity.getInstance().getLoginSession().getUsername().equals(list.get(0))) {
                        res = String.format("%s sent %s to %s", list.get(1), list.get(2), list.get(0));
                    } else if (LoginActivity.getInstance().getLoginSession().getUsername().equals(list.get(1))) {
                        res = String.format("%s sent %s to %s", list.get(1), list.get(2), list.get(0));
                    }
                    break;
                case "L":
                    if (LoginActivity.getInstance().getLoginSession().getUsername().equals(list.get(0))) {
                        res = String.format("%s sent %s to %s", list.get(0), list.get(2), list.get(1));
                    } else if (LoginActivity.getInstance().getLoginSession().getUsername().equals(list.get(1))) {
                        res = String.format("%s sent %s to %s", list.get(0), list.get(2), list.get(1));
                    }
                    break;
            }
        } catch (NullPointerException e) {
        }
        return res;
    }

}
