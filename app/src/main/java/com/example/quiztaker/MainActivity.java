package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
ASSIGNMENT: FINAL PROJECT
TEAM MEMBERS: ERIC LEE & BRYAN ORTT
DATE: 7/3/2020
CLASS: CECS 453
 */

//This activity is shown when the student first logs into his or her account
//The options given are "Take a Quiz", "View Final Grade Report", or "Log out"
public class MainActivity extends AppCompatActivity {

    private String theUsername;
    private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = this.getIntent().getExtras();
        theUsername = b.getString("info");
        logOutBtn = findViewById(R.id.logOutBtn);

        Button takeQuizButton = findViewById(R.id.takeQuizBtn);
        Button viewReportButton = findViewById(R.id.viewReportBtn);
        TextView greetingsMessage = findViewById(R.id.greetingsTextView);

        greetingsMessage.setText("Greetings, " + theUsername);

        //Student elects to take a quiz
        takeQuizButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TakeQuizDisplay.class);
                intent.putExtra("username", theUsername);
                MainActivity.this.startActivity(intent);
            }
        });

        //Student elects to his or her quiz grades report
        viewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GradeReportOption.class);
                intent.putExtra("username", theUsername);
                intent.putExtra("actionToTake", "Display All Quizzes");
                MainActivity.this.startActivity(intent);
            }
        });

        //Student elects to log out of his or her account
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}