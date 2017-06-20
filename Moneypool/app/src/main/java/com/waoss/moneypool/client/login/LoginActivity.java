package com.waoss.moneypool.client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.waoss.moneypool.client.R;
import com.waoss.moneypool.client.SignupActivity;
import com.waoss.moneypool.client.net.URLReaderTask;

public class LoginActivity extends AppCompatActivity {

    LoginSession loginSession;
    EditText password;
    EditText username;

    public static LoginActivity getInstance() {
        return instance;
    }

    static LoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        instance = this;
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
    }

    public LoginSession getLoginSession() {
        return loginSession;
    }

    public void loginOnAction(View view) {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        URLReaderTask urlReaderTask = new URLReaderTask();
        urlReaderTask.execute(String.format("http://moneypool.localtunnel.me/check.jsp?username=%s&password=%s",
                username.getText().toString(), password.getText().toString()), "false");
        while (urlReaderTask.isRunning()) ;
        if (urlReaderTask.getResult().equals("0")) {
            loginSession = new LoginSession(this.getSharedPreferences("Users", MODE_MULTI_PROCESS));
            loginSession.setUsername(username.getText().toString());
            loginSession.setPassword(password.getText().toString());
            startActivity(new Intent(this, OptionsActivity.class));
        } else {
            Toast.makeText(this, "First sign up", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUpOnAction(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return true;
    }
}
