package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GradeReportOption extends AppCompatActivity {

    private String userName;

    //This activity is shown when the student decides to view his or her grades
    //The student is given the option to view all grades or view grades by category
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_report_style);
        userName = this.getIntent().getExtras().getString("username");

        Button allQuizzesButton = findViewById(R.id.reportAllQuizzesBtn);
        Button categoryQuizzesButton = findViewById(R.id.reportCategoryQuizzesBtn);

        //If all quizzes button is pressed
        allQuizzesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(GradeReportOption.this, VariableGradeReport.class);
                intent.putExtra("theUserName", userName);
                intent.putExtra("actionToTake", "Display All Quizzes");

                GradeReportOption.this.startActivity(intent);
            }
        });

        //If quizzes by category button is pressed
        categoryQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GradeReportOption.this, VariableGradeReport.class);
                intent.putExtra("theUserName", userName);
                intent.putExtra("actionToTake", "Display Category Quizzes");
                GradeReportOption.this.startActivity(intent);
            }
        });
    }
}
