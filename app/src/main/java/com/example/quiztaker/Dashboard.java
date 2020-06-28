package com.example.quiztaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textViewMessage = findViewById(R.id.textViewMessage);

        Intent intent = getIntent();
        String message = intent.getStringExtra("username");

        textViewMessage.setText("Welcome, " + message);

    }
}
