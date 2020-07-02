package com.example.quiztaker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class QuizInProgress extends AppCompatActivity {

    private String quizCategory;
    private String quizName;

    static int totalSecondsLeft;
    static Timer timer;

    TextView timeRemaining;
    private Button nextQuestionButton;
    private Button submitButton;
    private QuizQuestionFragment fragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_in_progress);
        timeRemaining = findViewById(R.id.timeRemainingTextView);
        nextQuestionButton = findViewById(R.id.nextQuestionBtn);
        submitButton = findViewById(R.id.submitQuizBtn);

        Bundle b = this.getIntent().getExtras();
        final String category = b.getString("theCategory");
        final String quizname = b.getString("theQuiz");
        int quizId = b.getInt("quizId");
        int minutesTotal = b.getInt("minutes");

//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Category name: " +category + "\nQuiz name: " + quizname + "\nQuizID: " + quizId + "\nMinutes: " + minutesTotal,
//                Toast.LENGTH_SHORT);
//        toast.show();

        // converts minutes to hour and minutes
        int hoursRemaining =  minutesTotal / 60;
        int minutesRemaining = minutesTotal % 60;
        int secondsRemaining = 0;


        String theTimeFormatted = String.format("Time Remaining: %02d:%02d:%02d", hoursRemaining, minutesRemaining, secondsRemaining);
        timeRemaining.setText(theTimeFormatted);
        String quizCategory = getIntent().getExtras().getString("theCategory");
        String quizName = getIntent().getExtras().getString("theQuiz");
        fragment = QuizQuestionFragment.newInstance(quizCategory, quizName, quizId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.quizAreaSection, fragment).commit();

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragment.nextQuestion();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(totalSecondsLeft >= 1) {
                    finish();
                    timer.cancel();
                    fragment.submitQuiz(true);
                }

                else {
                    finish();
                    fragment.submitQuiz(false);
                }
            }
        });

        int allowedTimeConvertedToSeconds = 0;
        allowedTimeConvertedToSeconds += secondsRemaining;
        allowedTimeConvertedToSeconds += 60 * minutesRemaining;
        allowedTimeConvertedToSeconds += 60 * 60 * hoursRemaining;

        timer = new Timer();
        totalSecondsLeft = allowedTimeConvertedToSeconds;

        int delay = 1000;
        int period = 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (totalSecondsLeft > 1 ) {
                    totalSecondsLeft--;
                    int hoursLeft = totalSecondsLeft / (60 * 60);
                    int minutesLeft = (totalSecondsLeft - (hoursLeft * 60 * 60)) / 60;
                    int secondsLeft = totalSecondsLeft - (hoursLeft * 60 * 60) - (minutesLeft * 60);

                    timeRemaining.setText(String.format("Time Remaining: %02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft));
                }

                else {
                    totalSecondsLeft--;
                    timer.cancel();
                    timeRemaining.setText("Time Remaining: 00:00:00");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showSubmitButton();
                            outOfTimeToast();
                            hideRadioButtons();
                        }
                    });
                }
            }
        }, delay, period);
    }

    public void showNextQuestionButton() {
        nextQuestionButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.INVISIBLE);
    }

    public void showSubmitButton() {
        nextQuestionButton.setVisibility(View.INVISIBLE);
        submitButton.setVisibility(View.VISIBLE);
    }

    public void outOfTimeToast() {
        Toast.makeText(this, "You are out of time. Please submit your quiz.", Toast.LENGTH_SHORT).show();
    }

    public void hideRadioButtons() {
        fragment.setAvailableRadioButtons(0);
    }

}


