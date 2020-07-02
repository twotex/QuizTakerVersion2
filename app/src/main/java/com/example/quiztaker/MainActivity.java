package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String theUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = this.getIntent().getExtras();
        final String theUsername = b.getString("info");


        Button takeQuizButton = findViewById(R.id.takeQuizBtn);
        Button viewReportButton = findViewById(R.id.viewReportBtn);
        TextView greetingsMessage = findViewById(R.id.greetingsTextView);

        greetingsMessage.setText("Greetings, " + theUsername);

        takeQuizButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //UserDetails userInQuestion = new UserDetails(theUsername, 45); //Pass this info in
                Intent intent = new Intent(MainActivity.this, TakeQuizDisplay.class);
                //intent.putExtra(theUsername);
                intent.putExtra("username", theUsername);
                MainActivity.this.startActivity(intent);

            }
        });

        viewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails userInQuestion = new UserDetails(theUsername, 45); //Pass this info in
                Intent intent = new Intent(MainActivity.this, GradeReportOption.class);
                intent.putExtra("theUserObject", userInQuestion);
                intent.putExtra("actionToTake", "Display All Quizzes");
                MainActivity.this.startActivity(intent);
            }
        });
    }
}