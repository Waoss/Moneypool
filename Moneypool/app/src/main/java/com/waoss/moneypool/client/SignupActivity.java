package com.waoss.moneypool.client;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.waoss.moneypool.client.login.LoginActivity;
import com.waoss.moneypool.client.net.URLReaderTask;

public class SignupActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText fullName, mobileNo, location, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        fullName = (EditText) findViewById(R.id.fullName);
        mobileNo = (EditText) findViewById(R.id.mobileNumber);
        location = (EditText) findViewById(R.id.location);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
    }

    public void alreadyUserOnAction(View view) {
        gotoLoginActivity();
    }

    public void signupOnClick(View view) {
        if (fullName.getText().toString().equals("") || mobileNo.getText().toString().equals("") ||
                location.getText().toString().equals("") || password.getText().toString().equals("") ||
                username.getText().toString().equals("") || confirmPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Fill all details.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
            Toast.makeText(this, "Password different in required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        URLReaderTask readerTask = new URLReaderTask();
        readerTask.execute(String.format("http://moneypool.localtunnel.me/adduser.jsp?username=%s&password=%s", username.getText().toString(), password.getText().toString()));
        while (readerTask.isRunning()) ;
        if (readerTask.getResult().equalsIgnoreCase("0")) {
            Toast.makeText(this, "User succesfully made", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        this.gotoLoginActivity();
    }

    protected void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
