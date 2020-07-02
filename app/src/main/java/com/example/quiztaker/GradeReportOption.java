package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GradeReportOption extends AppCompatActivity {

    private UserDetails theUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_report_style);
        theUserDetails = getIntent().getExtras().getParcelable("theUserObject");


        Button allQuizzesButton = findViewById(R.id.reportAllQuizzesBtn);
        Button categoryQuizzesButton = findViewById(R.id.reportCategoryQuizzesBtn);


        allQuizzesButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(GradeReportOption.this, VariableGradeReport.class);
                intent.putExtra("theUserObject", theUserDetails);
                intent.putExtra("actionToTake", "Display All Quizzes");

                GradeReportOption.this.startActivity(intent);
            }
        });

        categoryQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GradeReportOption.this, VariableGradeReport.class);
                intent.putExtra("theUserObject", theUserDetails);
                intent.putExtra("actionToTake", "Display Category Quizzes");
                GradeReportOption.this.startActivity(intent);
            }
        });

    }
}
