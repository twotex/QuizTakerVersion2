package com.example.quiztaker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VariableGradeReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_report);

        Button closeWindowButton = findViewById(R.id.closeWindowBtn);

        closeWindowButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState == null) {
            UserDetails userProfile = getIntent().getExtras().getParcelable("theUserObject");
            String action = getIntent().getExtras().getString("actionToTake");


            QuizMenuFragment fragment =
                    QuizMenuFragment.newInstance(action);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameForQuizzes, fragment) //adding the song detail fragment to the frame layout within activity_song_detail
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
