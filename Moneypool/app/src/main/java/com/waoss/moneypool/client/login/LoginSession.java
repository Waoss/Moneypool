package com.waoss.moneypool.client.login;

import android.content.SharedPreferences;
import android.util.Log;

public class LoginSession {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public void stopSession() {
        editor.clear();
    }

    public LoginSession(SharedPreferences preferences) {
        this.preferences = preferences;
        this.editor = this.preferences.edit();
    }

    public void setUsername(String username) {
        this.editor.putString("username", username);
        this.editor.apply();
    }

    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }

    public String getUsername() {
        return preferences.getString("username", "unsuccessful");
    }

    public String getPassword() {
        return this.preferences.getString("password", "chotya");
    }
}
