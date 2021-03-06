package com.example.quiztaker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VariableGradeReport extends AppCompatActivity {

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_report);

        Button closeWindowButton = findViewById(R.id.backBtn);
        closeWindowButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState == null) {
            userName = this.getIntent().getExtras().getString("theUserName");
            String action = getIntent().getExtras().getString("actionToTake");

            QuizMenuFragment fragment =
                    QuizMenuFragment.newInstance(action, userName);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameForQuizzes, fragment)
                    .commit();
        }
    }

    public void updateStats(String grade, String score, String percentage) {
        TextView gradeTextView = findViewById(R.id.letterGradeTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView percentageTextView = findViewById(R.id.percentageTextView);
        gradeTextView.setText(grade);
        scoreTextView.setText(score);
        percentageTextView.setText(percentage);
    }

}
