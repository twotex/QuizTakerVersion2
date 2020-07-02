package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterStudents extends AppCompatActivity {

    private Spinner spinnerCategory;
    private Spinner spinnerQuizName;
    private Spinner spinnerStudents;
    private Button buttonUpdate;
    private Button buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_students);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerQuizName = findViewById(R.id.spinnerQuizName);
        spinnerStudents = findViewById(R.id.spinnerStudents);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonBack = findViewById(R.id.buttonBack);

        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));



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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // event listener to add student to quiz
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }

        });


        ArrayList<User> userResults = dataBaseHelper.selectStudentInfo();

        Toast.makeText(getApplicationContext(), userResults.toString(),
                Toast.LENGTH_LONG).show();

        ArrayList<String> userList = new ArrayList<>();
        for (int i = 0; i < userResults.size(); i++) {
            userList.add(userResults.get(i).getUsername());
        }

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userList);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStudents.setAdapter(spinnerArrayAdapter2);

        // event listener for back button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = spinnerStudents.getSelectedItem().toString();
                String quiz = spinnerQuizName.getSelectedItem().toString();
                String category = spinnerCategory.getSelectedItem().toString();
                long res = dataBaseHelper.insetStudentQuiz(username, quiz, category);

                String message;
                if (res < 0) {
                    message = "Already added to quiz";
                } else {
                    message = "Student added to quiz";
                }
                Toast.makeText(getApplicationContext(), message,
                        Toast.LENGTH_LONG).show();
            }

        });
    }

}