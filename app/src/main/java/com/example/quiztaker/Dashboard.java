package com.example.quiztaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView textViewMessage;

    //This activity is shown to the user when he logs in and is not registered for any quizzes
    //A single "Hi" message is shown and no buttons are available
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
