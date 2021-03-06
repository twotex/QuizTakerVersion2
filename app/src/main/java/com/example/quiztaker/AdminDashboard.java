package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity {

    private Button categoryButton;
    private Button createQuizButton;
    private Button changeTimeOfQuizButton;
    private Button registerStudentsQuizButton;
    private Button buttonResults;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        categoryButton = findViewById(R.id.categoryButton);
        createQuizButton = findViewById(R.id.createQuizButton);
        changeTimeOfQuizButton = findViewById(R.id.changeTimeOfQuizButton);
        registerStudentsQuizButton = findViewById(R.id.registerStudentsQuizButton);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonResults = findViewById(R.id.buttonResults);


        // event listener for create a quiz button
        categoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizCategory.class);
                startActivity(intent);
            }

        });

        // event listener for create a quiz button
        createQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateQuiz.class);
                startActivity(intent);
            }

        });

        // event listener for create change quiz time
        changeTimeOfQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeLimit.class);
                startActivity(intent);
            }

        });

        // event listener to add student to quiz
        registerStudentsQuizButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterStudents.class);
                startActivity(intent);
            }

        });

        // event listener to add student to quiz
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }

        });

        // event listener to add student to quiz
        buttonResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminResults.class);
                startActivity(intent);
            }

        });

    }
}
