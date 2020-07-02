package com.example.quiztaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class CreateQuiz extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonSubmit;
    private Spinner spinnerCategory;
    private EditText editTextHours;
    private EditText editTextMinutes;

    private EditText editTextQuizName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_quiz);
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));


        buttonBack = findViewById(R.id.buttonBack);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        editTextHours = findViewById(R.id.editTextHours);
        editTextMinutes = findViewById(R.id.editTextMinutes);

        editTextQuizName = findViewById(R.id.editTextQuizName);

        // Query database for categories and put them in an array list then fill the spinner
        ArrayList<String> categoryList = dataBaseHelper.selectCategory();
        categoryList.add(0, "(SELECT)");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategory.setAdapter(spinnerArrayAdapter);





        // event listener for submit button that creates a quiz category
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String quizName = editTextQuizName.getText().toString();
                String quizCategory = spinnerCategory.getSelectedItem().toString();



                    int min = Integer.parseInt(editTextMinutes.getText().toString());
                    int hours = Integer.parseInt(editTextHours.getText().toString());
                    boolean minValidation = true;
                    boolean hoursValidation = true;
                    if (min > 59) {
                        Toast.makeText(getApplicationContext(),"Error: Minutes must be less than 60",
                                Toast.LENGTH_SHORT).show();
                        minValidation = false;
                    }
                    if (hours > 24) {
                        Toast.makeText(getApplicationContext(),"Error: Hours must be less than 24 or select unlimited",
                                Toast.LENGTH_SHORT).show();
                        hoursValidation = false;
                    }
                    int totalMinutes = 0;
                    for (int i = hours; i > 0; i--) {
                        totalMinutes += 60;
                    }
                    totalMinutes += min;

                    if (minValidation == true && hoursValidation == true && quizCategory != null && quizName != null) {
                        long res = dataBaseHelper.insertQuiz(quizName,quizCategory, String.valueOf(totalMinutes));
                        if (res < 0) {
                            Toast.makeText(getApplicationContext(),"Error: Quiz with that name and category already exists",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Successfully created quiz!",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), QuizQuestion.class);
                            intent.putExtra("arr", new String[] {quizName, quizCategory});
                            startActivity(intent);
                        }
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
