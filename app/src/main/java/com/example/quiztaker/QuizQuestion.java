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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);
        Bundle b = this.getIntent().getExtras();
        String[] array =b.getStringArray("arr");
        String quizName = array[0];
        String quizCategory = array[1];

        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));

        Quiz quiz = dataBaseHelper.selectQuiz(quizName, quizCategory);
        Toast.makeText(getApplicationContext(), String.valueOf(quiz.getId()),
                Toast.LENGTH_LONG).show();




    }

}
