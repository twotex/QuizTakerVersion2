package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TimeLimit extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonSubmit;
    private Spinner spinnerCategory;
    private Spinner spinnerQuizName;
    private EditText editTextMinutes;
    private EditText editTextHours;
    private CheckBox checkboxUnlimited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_time);
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));

        buttonBack = findViewById(R.id.buttonBack);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerQuizName = findViewById(R.id.spinnerQuizName);
        editTextHours = findViewById(R.id.editTextHours);
        editTextMinutes = findViewById(R.id.editTextMinutes);
        checkboxUnlimited = findViewById(R.id.checkBoxUnlimited);

        final ArrayList<String> nameList = new ArrayList<>();


        // Query database for categories and put them in an array list then fill the spinner
        final ArrayList<String> categoryList = dataBaseHelper.selectCategory();
        categoryList.add(0, "(SELECT)");
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategory.setAdapter(spinnerArrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                nameList.clear();
                ArrayList<Quiz> list = dataBaseHelper.selectQuizNames(spinnerCategory.getSelectedItem().toString());
                for (int i = 0; i < list.size(); i++) {
                    nameList.add(list.get(i).getQuizName());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nameList);
                spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinnerQuizName.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        spinnerQuizName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    ArrayList<Quiz> list = dataBaseHelper.selectQuizNames(spinnerCategory.getSelectedItem().toString());
                    int minutes = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getQuizName().equals(spinnerQuizName.getSelectedItem().toString())) {
                            minutes = Integer.parseInt(list.get(i).getTimeLimit());
                        }
                    }

                    int hrs = minutes / 60; //since both are ints, you get an int
                    int mins = minutes % 60;

                    editTextHours.setText(String.valueOf(hrs));
                    editTextMinutes.setText(String.valueOf(mins));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        // event listener for back button
        checkboxUnlimited.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextHours.setText("");
                editTextMinutes.setText("");
            }

        });






        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!checkboxUnlimited.isActivated()) {
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

                    if (minValidation == true && hoursValidation == true) {
                        String quizName = spinnerQuizName.getSelectedItem().toString();
                        String quizCategory = spinnerCategory.getSelectedItem().toString();

                        long res = dataBaseHelper.updateQuizTime(quizName,quizCategory, String.valueOf(totalMinutes));
                        if (res < 0) {
                            // clean up later
                        } else {
                            Toast.makeText(getApplicationContext(),"Successfully updated time!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });



    }
}
