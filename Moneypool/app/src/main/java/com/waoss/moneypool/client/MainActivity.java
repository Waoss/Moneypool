package com.waoss.moneypool.client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.waoss.moneypool.client.net.URLReaderTask;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    Button b;
    String result;

    public void setResult(String result) {
        this.result = result;
    }

    static MainActivity instance;

    public static MainActivity getInstance() {

        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        b = (Button) findViewById(R.id.lend);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("abcde", "OnClickMethod");
                URLReaderTask urlReaderTask = new URLReaderTask();
                urlReaderTask.execute("https://4097d1c4.ngrok.io/updateuser.jsp?sendername=roit&senderpassword=shorma&receivername=veroit&receiverpassword=coalhi&balance=100");
                while (urlReaderTask.isRunning() == true) ;
                String exitCode = urlReaderTask.getResult();
                Log.i("res", exitCode);
                if (exitCode.equals("0")) {
                    Toast.makeText(MainActivity.this, "Transaction successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
