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

//This class is instantiated when the user has selected the exact quiz that he or she would like to take
public class QuizInProgress extends AppCompatActivity {

    private String quizCategory;
    private String quizName;
    private String userName;
    private int quizId;
    private int minutesTotal;

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

        //Obtain user and quiz information from intent
        quizCategory = this.getIntent().getExtras().getString("theCategory");
        quizName = this.getIntent().getExtras().getString("theQuiz");
        userName = this.getIntent().getExtras().getString("userName");
        quizId = this.getIntent().getExtras().getInt("quizId");
        minutesTotal = this.getIntent().getExtras().getInt("minutes"); //total time limit for the quiz converted to minutes

        //Extract the hours & minutes available for the quiz from the total minutes allowed for the quiz
        int hoursRemaining =  minutesTotal / 60;
        int minutesRemaining = minutesTotal % 60;
        int secondsRemaining = 0; //Seconds is always 0 since the admin cannot adjusts the seconds time limit

        String theTimeFormatted = String.format("Time Remaining: %02d:%02d:%02d", hoursRemaining, minutesRemaining, secondsRemaining);
        timeRemaining.setText(theTimeFormatted);

        fragment = QuizQuestionFragment.newInstance(quizCategory, quizName, quizId, userName);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.quizAreaSection, fragment).commit();

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fragment.nextQuestion();
            }
        });

        //Student has decided to submit his quiz for grading
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //If the student still had time remaining when he or she submitted the quiz
                if(totalSecondsLeft >= 1) {
                    finish();
                    timer.cancel();
                    fragment.submitQuiz(true);
                }

                //If the student did not have any time remaining when he or she submitted the quiz
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

        int delay = 1000; //Delay in milliseconds until timer starts functioning
        int period = 1000; //Delay in milliseconds between periodic update intervals

        //Timer object that is used to periodically update the time remaining for the quiz
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





