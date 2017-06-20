package com.waoss.moneypool.client.login;

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
import android.widget.EditText;
import android.widget.Toast;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.net.URLReaderTask;

public class LendActivity extends AppCompatActivity {

    EditText receiverUsername;
    EditText lendAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        receiverUsername = (EditText) findViewById(R.id.receiverUsername);
        lendAmount = (EditText) findViewById(R.id.lendAmount);
    }

    public void confirmLendOnAction(View view) {
        String user1=LoginActivity.getInstance().getLoginSession().getUsername();
        String user2=receiverUsername.getText().toString();
        if(user1.equals(user2))
        {
            Toast.makeText(this, "Enter a valid user!", Toast.LENGTH_SHORT).show();
            return;
        }
        URLReaderTask urlReaderTask = new URLReaderTask();
        urlReaderTask.execute(String.format("http://moneypool.localtunnel.me/lend.jsp?sendername=%s&senderpassword=%s&receivername=%s&receiverpassword=lulz&balance=%s",
                LoginActivity.getInstance().getLoginSession().getUsername(), LoginActivity.getInstance().getLoginSession().getPassword(),
                receiverUsername.getText().toString(), lendAmount.getText().toString()), "false");
        while (urlReaderTask.isRunning()) ;
        if (urlReaderTask.getResult().equalsIgnoreCase("0")) {
            Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong! :(", Toast.LENGTH_SHORT).show();
        }
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
