package com.waoss.moneypool.client.net;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.waoss.moneypool.client.notification.NotificationTestActivity;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class URLReaderTask extends AsyncTask<String, Void, String> {
    boolean running = true;
    String result;

    public boolean isRunning() {
        return running;
    }

    public String getResult() {
        return result;
    }

    @Override
    protected String doInBackground(String... strings) {
        running = true;
        result = "initial_useless_string";
        try {
            Log.i("qwe", strings[0]);
            HttpURLConnection httpURLConnection = getConnection(strings[0]);
            while (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i("url", httpURLConnection.getResponseMessage());
                httpURLConnection = getConnection(strings[0]);
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            result = IOUtils.toString(new InputStreamReader(inputStream));
            result = parseHttpString(result,Boolean.parseBoolean(strings[1]));
            Log.i("rea", result);
            //Toast.makeText(MainActivity.getInstance(),"Done",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.i("e", ExceptionUtils.getStackTrace(e));
        }
        running = false;
        return result;

    }

    public String parseHttpString(String s, boolean spacing) {
        String res = "";
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        while (stringTokenizer.hasMoreTokens()) {
            if (spacing) {
                res += stringTokenizer.nextToken() + " ";
            } else {
                res += stringTokenizer.nextToken();
            }
        }
        res = res.trim();
        return res;
    }

    protected HttpURLConnection getConnection(String url) throws IOException {
        Log.i("service", "In getConnection");
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        return urlConnection;
    }
}
