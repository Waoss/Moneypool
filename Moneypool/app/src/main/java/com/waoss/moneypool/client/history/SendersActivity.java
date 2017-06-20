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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.login.LoginActivity;

public class SendersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senders);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, parseStringArray(getIntent().getStringArrayExtra("senders")));
        ListView listView = (ListView) findViewById(R.id.list_view_senders);
        listView.setAdapter(arrayAdapter);
    }

    protected String[] parseStringArray(String[] receivers) {
        if (receivers[0].equalsIgnoreCase("")) {
            return new String[]{"You haven't borrowed from anyone.Keep it up!"};
        }
        String[] result = new String[receivers.length / 2];
        int index = 0;
        for (int i = 0; i < receivers.length; i++) {
            result[index++] = receivers[i] + "\n" + receivers[++i];
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.logout_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.logout);
        SpannableString spannableString = new SpannableString("Logout");
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
