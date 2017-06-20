package com.waoss.moneypool.client.history;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.login.LoginActivity;
import com.waoss.moneypool.client.net.URLReaderTask;
import com.waoss.moneypool.client.service.NotificationService;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
    }

    public void viewSendersOnAction(View view) {
        URLReaderTask urlReaderTask = new URLReaderTask();
        urlReaderTask.execute(String.format("http://moneypool.localtunnel.me/viewreceivers.jsp?username=%s&password=%s",
                LoginActivity.getInstance().getLoginSession().getUsername(), LoginActivity.getInstance().getLoginSession().getPassword()), "true");
        while (urlReaderTask.isRunning()) ;
        String[] senders = urlReaderTask.getResult().split(" ");
        Intent intent = new Intent(this, SendersActivity.class);
        intent.putExtra("senders", senders);
        startActivity(intent);
    }

    public void viewReceiversOnAction(View view) {
        URLReaderTask urlReaderTask = new URLReaderTask();
        urlReaderTask.execute(String.format("http://moneypool.localtunnel.me/viewsenders.jsp?username=%s&password=%s",
                LoginActivity.getInstance().getLoginSession().getUsername(), LoginActivity.getInstance().getLoginSession().getPassword()), "true");
        while (urlReaderTask.isRunning()) ;
        String[] receivers = urlReaderTask.getResult().split(" ");
        Intent intent = new Intent(this, ReceiversActivity.class);
        intent.putExtra("receivers", receivers);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.logout);
        SpannableString spannableString=new SpannableString("Logout");
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), 0);
        menuItem.setTitle(spannableString);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            LoginActivity.getInstance().getLoginSession().stopSession();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
