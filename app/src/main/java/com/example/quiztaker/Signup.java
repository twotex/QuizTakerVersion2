package com.example.quiztaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    private Button buttonSubmit;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordRetype;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private HashMap<String,User> listOfUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordRetype = findViewById(R.id.editTextPasswordRetype);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);


        Intent intent = getIntent();
        listOfUsers = (HashMap<String, User>) intent.getSerializableExtra("map");

        // event listener for submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get field values into strings
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String retypePassword = editTextPasswordRetype.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                // validation values, default is false
                boolean fieldsFilled = false;
                boolean passwordsMatch = false;
                boolean usernameUnique = false;
                boolean validEmail = false;
                boolean validPhone = false;

                DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
                String res = dataBaseHelper.selectUsernames(username);

                // makes sure all fields are not empty
                if (!username.isEmpty() && !password.isEmpty() && !retypePassword.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                    fieldsFilled = true;


                    // validates passwords match
                    if (password.equals(retypePassword)) {
                        passwordsMatch = true;
                    } else {
                        toast("Passwords must match");
                    }

                    // validates email is correct format
                    validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                    if (!validEmail) {
                        toast("Invalid email format");
                    }

                    // validates phone is correct format
                    validPhone = Patterns.PHONE.matcher(phone).matches();
                    if (!validPhone) {
                        toast("Invalid phone format");
                    }

                    // checks username with other accounts
                    if (res.equals("0")) {
                        usernameUnique = true;
                    } else {
                        toast("Username already exists");
                    }

                } else {
                    toast("All fields required");
                }

                // adds user to database as long as all conditions are true
                if (fieldsFilled && usernameUnique && passwordsMatch && validEmail && validPhone) {
                    toast("Account created");

                    dataBaseHelper.insertStudent(new User(username,password,email,phone));

                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
            }
        });

    }

    // Creates a toast message, it takes a string
    private void toast(String message) {
        Toast.makeText(Signup.this, message, Toast.LENGTH_LONG).show();
    }

    // Set all inputs to empty upon resume
    protected void onResume() {
        super.onResume();
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextPasswordRetype.setText("");
        editTextEmail.setText("");
        editTextPhone.setText("");
    }
}
