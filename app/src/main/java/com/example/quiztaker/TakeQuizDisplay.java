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
    private int quizID;
    public String theUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_quiz_menu);



        chosenCategory = "Category name placeholder";
        chosenQuiz = "Quiz name placeholder";

        Bundle b = this.getIntent().getExtras();
        final String theUsername = b.getString("username"); // username

        Toast.makeText(getApplicationContext(), theUsername,
                Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString("txt", theUsername);

        // set Fragmentclass Arguments
        //QuizMenuFragment fragobj = new QuizMenuFragment(theUsername);
        //fragobj.setArguments(bundle);

        QuizMenuFragment fragment =
                QuizMenuFragment.newInstance(theUsername);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.quizAreaListViews, fragment)
                .commit();
        final Button submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
                Quiz q = dataBaseHelper.findQuizIdandTime(chosenCategory, chosenQuiz);

                //UserDetails userInQuestion = new UserDetails(theUsername, 45); //Pass this info in
                Intent intent = new Intent(TakeQuizDisplay.this, QuizInProgress.class);
                intent.putExtra("theCategory", chosenCategory);
                intent.putExtra("theQuiz", chosenQuiz);
                intent.putExtra("quizId",q.getId());
                intent.putExtra("minutes", q.getMinutes());
                intent.putExtra("userName", theUsername);

                       Toast toast = Toast.makeText(getApplicationContext(),
                "!!! Category name: " +chosenCategory + "\nQuiz name: " + chosenQuiz + "\nQuizID: " + q.getId() + "\nMinutes: " + q.getMinutes() + "\n username: " + theUsername,
                Toast.LENGTH_SHORT);
        toast.show();

                TakeQuizDisplay.this.startActivity(intent);
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

    public void updateChosenOptions(String category, String quiz, int quizId) {
        chosenCategory = category;
        chosenQuiz = quiz;
    }


}
