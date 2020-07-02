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


        theScore.setText(correct + "/" + totalQuestions);
        quizNumOfQuestions = Integer.parseInt(totalQuestions); //UPDATE THIS WITH QUERY
        dataBaseHelper.insertQuiz(username,category, quizId, theScore.getText().toString(), quizName);

        Toast.makeText(getApplicationContext(), "username: "+ username + "" +
                "\nQuizID" + quizId +
                "\ncorrect: " + correct +
                "\nNumber of Questions"+ totalQuestions +
                "\nsolutions: " + solutions.toString() , Toast.LENGTH_SHORT).show();

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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        questionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    theQuestion.setText(questions.get(position));
                    theAnswer.setText(solutions.get(position));


//                else if (position == 1) { //SWAP STRING WITH QUERY RESULTS
//                    theQuestion.setText("It takes 4 nickels to make $0.25");
//                    theAnswer.setText("False");
//                }
//
//                else { //SWAP STRING WITH QUERY RESULTS
//                    theQuestion.setText("How many inches are there in 2 feet?");
//                    theAnswer.setText("24");
//                }
            }
    });

}
}

