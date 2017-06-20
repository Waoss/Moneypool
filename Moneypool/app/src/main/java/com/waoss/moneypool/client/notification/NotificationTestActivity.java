package com.waoss.moneypool.client.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.login.LoginActivity;
import com.waoss.moneypool.client.service.NotificationService;

public class NotificationTestActivity extends AppCompatActivity {
    Button notify;
    static Context context;
    Button signIn;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        context = this;
        Intent intent = new Intent(NotificationTestActivity.this, NotificationService.class);
        Log.i("service", "Started service");
        startService(intent);
        Log.i("service", "Service running!");
        notify = (Button) findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationHandler.showNotification(NotificationTestActivity.this, R.drawable.ic_stat_name, "Hum Chotyas!", "Tumau Chotya");
            }
        });
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivityIntent = new Intent(NotificationTestActivity.this, LoginActivity.class);
                startActivity(loginActivityIntent);
            }
        });
    }
}
