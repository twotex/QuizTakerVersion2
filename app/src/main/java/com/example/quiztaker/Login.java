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
import java.util.HashMap;

public class Login extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonSignup;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private HashMap<String, User> listOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        listOfUsers = new HashMap<>();


        Intent intent = getIntent();
        HashMap<String,User> userCreated = (HashMap<String, User>) intent.getSerializableExtra("map");
        if (userCreated != null) {
            listOfUsers.putAll(userCreated);
        }


        // event listener for signup
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                intent.putExtra("map", listOfUsers);
                startActivity(intent);
            }

        });



        // event listener for login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();


                DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
                // checks credentials for valid account in database
                boolean validLogin = dataBaseHelper.checkStudentLogin(username, password);

                // CODE TO GO TO STUDENT MAIN ACTIVITY DASHBOARD!!! -- Testing admin section hard coded right now
                if (username.equals("test")) {
                    Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                    startActivity(intent);
                }

                if (validLogin) {
                    Toast.makeText(getApplicationContext(), "SUCCESSFUL!!!",
                            Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(), "INVALID USERNAME OR PASSWORD",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // Set all inputs to empty upon resume
    protected void onResume() {
        super.onResume();
        editTextUsername.setText("");
        editTextPassword.setText("");

    }
}
