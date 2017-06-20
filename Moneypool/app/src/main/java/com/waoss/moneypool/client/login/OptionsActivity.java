

package com.waoss.moneypool.client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.history.HistoryActivity;
import com.waoss.moneypool.client.service.NotificationService;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        Toast.makeText(this,"Press Back button to logout",Toast.LENGTH_LONG).show();
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);
    }

    public void gotoBorrowOnAction(View view) {
        startActivity(new Intent(this, BorrowActivity.class));
    }

    public void gotoHistoryOnAction(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    public void gotoLendOnAction(View view) {
        startActivity(new Intent(this, LendActivity.class));
    }


    public void logoutOnAction(View view) {
        LoginActivity.getInstance().getLoginSession().stopSession();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
