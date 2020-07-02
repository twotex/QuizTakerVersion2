package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        theScore = findViewById(R.id.quizResultsTextView);
        theQuestion = findViewById(R.id.theQuestionTextView);
        theAnswer = findViewById(R.id.theAnswerTextView);
        questionsListView = findViewById(R.id.questionNumberListView);
        mainMenu = findViewById(R.id.main_menu_button);

        DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));

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

        String theScoreString = correct + "/" + totalQuestions;
        System.out.println("Username: " + username);
        System.out.println("Quiz id: " + quizId);
        System.out.println("Correct: " + correct);
        System.out.println("Total questions: " + totalQuestions);
        System.out.println("Quiz name: " + quizName);
        System.out.println("Category: " + category);
        System.out.println("Questions: " + questions.toString());
        System.out.print("\n\n\n\n");
        System.out.println("Answers: " + solutions.toString());

        findPercentage(theScoreString);
        findGrade(theScoreString);

        theScore.setText("Grade: " + findGrade(theScoreString) + " - Percentage: " + findPercentage(theScoreString) + " - Score: " + theScoreString);
        quizNumOfQuestions = Integer.parseInt(totalQuestions);
        dataBaseHelper.insertQuiz(username, category, quizId, theScoreString, quizName);

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
            }
        });
    }

    protected String findPercentage(String score) {
        int numerator;
        int denominator;

        List<String> items = Arrays.asList(score.split("/"));
        System.out.println("List Contents: " + items.toString());
        numerator = Integer.parseInt(items.get(0));
        denominator = Integer.parseInt(items.get(1));

        double ratio = (double)numerator / (double)denominator;
        ratio = ratio * 100;
        String finalPercentage = String.format("%.2f%%", ratio);
        return finalPercentage;
    }

    protected String findGrade(String score) {
        int numerator;
        int denominator;

        List<String> items = Arrays.asList(score.split("/"));
        System.out.println("List Contents: " + items.toString());
        numerator = Integer.parseInt(items.get(0));
        denominator = Integer.parseInt(items.get(1));

        double ratio = (double)numerator / (double)denominator;
        ratio = ratio * 100;
        String finalGrade;

        if (ratio >= 90.0) {
            finalGrade = "A";
        }

        else if (ratio >= 80.0) {
            finalGrade = "B";
        }

        else if (ratio >= 70.0) {
            finalGrade = "C";
        }

        else if (ratio >= 60.0) {
            finalGrade = "D";
        }

        else {
            finalGrade = "F";
        }

        return finalGrade;
    }
}



