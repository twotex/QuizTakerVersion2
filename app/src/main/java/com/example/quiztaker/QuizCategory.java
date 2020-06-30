package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizCategory extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonSubmit;
    private EditText editTextTextCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_category);

        buttonBack = findViewById(R.id.buttonBack);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextTextCategory = findViewById(R.id.editTextQuizName);


        // event listener for submit button that creates a quiz category
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
                long res = dataBaseHelper.insertCategory(editTextTextCategory.getText().toString());
                if (res < 0) {
                    Toast.makeText(getApplicationContext(),"Error: Category is already in system",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Successfully added " + editTextTextCategory.getText().toString() +
                            " to the system",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });


        // event listener for back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }

        });

    }
}