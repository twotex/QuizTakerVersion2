package com.example.quiztaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TakeQuizDisplay extends AppCompatActivity {
    private String chosenCategory;
    private String chosenQuiz;
    public String theUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_quiz_menu);

        theUsername = this.getIntent().getExtras().getString("username");

        QuizMenuFragment fragment =
                QuizMenuFragment.newInstance("Take a Quiz", theUsername);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.quizAreaListViews, fragment)
                .commit();

        final Button submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
                Quiz q = dataBaseHelper.findQuizIdandTime(chosenCategory, chosenQuiz);

                Intent intent = new Intent(TakeQuizDisplay.this, QuizInProgress.class);
                intent.putExtra("theCategory", chosenCategory);
                intent.putExtra("theQuiz", chosenQuiz);
                intent.putExtra("quizId",q.getId());
                intent.putExtra("minutes", q.getMinutes());
                intent.putExtra("userName", theUsername);

                TakeQuizDisplay.this.startActivity(intent);
                finish();
            }
        });
    }

    public void setVisibleSubmitButton() {
        final Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setVisibility(View.VISIBLE);
    }

    public void setInvisibleSubmitButton() {
        final Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setVisibility(View.INVISIBLE);
    }

    public void updateChosenOptions(String category, String quiz) {
        chosenCategory = category;
        chosenQuiz = quiz;
    }
}
