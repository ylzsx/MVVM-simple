package com.example.login;

import android.os.Bundle;

import com.example.fw_annotations.BindPath;

import androidx.appcompat.app.AppCompatActivity;

@BindPath("login/LoginActivity")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
