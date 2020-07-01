package com.example.quiztaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class QuizQuestion extends AppCompatActivity {

    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private EditText editTextOption1;
    private EditText editTextOption2;
    private EditText editTextOption3;
    private Button buttonBack;
    private Button buttonAdd;
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        editTextOption1 = findViewById(R.id.editTextOption1);
        editTextOption2 = findViewById(R.id.editTextOption2);
        editTextOption3 = findViewById(R.id.editTextOption3);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonBack = findViewById(R.id.buttonBack);


        Bundle b = this.getIntent().getExtras();
        String[] array =b.getStringArray("arr");
        String quizName = array[0];
        String quizCategory = array[1];

        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));

        quiz = dataBaseHelper.selectQuiz(quizName, quizCategory);
        //Toast.makeText(getApplicationContext(), String.valueOf(quiz.getId()),
          //      Toast.LENGTH_LONG).show();


        // event listener to add student to quiz
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long res = dataBaseHelper.insertQuestion(quiz, editTextQuestion.getText().toString());
                Toast.makeText(getApplicationContext(), String.valueOf(res),
                      Toast.LENGTH_LONG).show();
            }

        });






    }

}
