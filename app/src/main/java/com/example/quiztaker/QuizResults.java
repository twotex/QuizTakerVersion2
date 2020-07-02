package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizResults extends AppCompatActivity {
    private TextView theScore;
    private TextView theQuestion;
    private TextView theAnswer;
    private ListView questionsListView;
    private int quizNumOfQuestions;
    private Button mainMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results);
        theScore = findViewById(R.id.detailedScoreTextView);
        theQuestion = findViewById(R.id.theQuestionTextView);
        theAnswer = findViewById(R.id.theAnswerTextView);
        questionsListView = findViewById(R.id.questionNumberListView);
        mainMenu = findViewById(R.id.main_menu_button);

        DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));


        //theScore.setText("Final Score: 7/10 - 70% - Grade: C"); //UPDATE THIS WITH QUERY

        // MAKE THIS DATA SHOW ON SCREEN
        Intent incomingIntent = getIntent();
        String username = incomingIntent.getStringExtra("username");
        String quizId = incomingIntent.getStringExtra("quizId");
        String correct = incomingIntent.getStringExtra("correct");
        String totalQuestions = incomingIntent.getStringExtra("numberOfQuestions");
        String quizName = incomingIntent.getStringExtra("quizName");
        String category = incomingIntent.getStringExtra("category");
        final ArrayList<String> solutions = incomingIntent.getStringArrayListExtra("solutions");    /// GET INFO HERE
        final ArrayList<String> questions = incomingIntent.getStringArrayListExtra("questions");
        // ALL THE DATA ABOVE IS COMING IN CORRECTLY

        System.out.println("Username: " + username);
        System.out.println("Quiz id: " + quizId);
        System.out.println("Correct: " + correct);
        System.out.println("Total questions: " + totalQuestions);
        System.out.println("Quiz name: " + quizName);
        System.out.println("Category: " + category);
        System.out.println("Questions: " + questions.toString());
        System.out.print("\n\n\n\n");
        System.out.println("Answers: " + solutions.toString());


        theScore.setText("Score for " + quizName + ": " + correct + "/" + totalQuestions);
        quizNumOfQuestions = Integer.parseInt(totalQuestions);
        dataBaseHelper.insertQuiz(username,category, quizId, theScore.getText().toString(), quizName);

        // going into Database (username, category, quiz_id)

        ArrayList <Integer> theQuestionNumbers = new ArrayList<>();

        for (int count = 0; count < quizNumOfQuestions; count++) {
            theQuestionNumbers.add(count + 1);
        }

        final ArrayAdapter<Integer> customAdapter;

        customAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, theQuestionNumbers);
        questionsListView.setAdapter(customAdapter);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    theQuestion.setText(questions.get(position));
                    theAnswer.setText(solutions.get(position));
//                }
            }
    });
}
}

