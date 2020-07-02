package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class QuizQuestionFragment extends Fragment {

    private String theCategory;
    private String theName;
    private int numberOfQuestions;
    private int currentQuestionIndex;
    private int currentQuestionOptionsAvailable;
    private int id;
    private String username;
    private TextView questionNumber;
    private TextView questionDescription;
    private RadioGroup radioButtonContainer;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private ArrayList<Question> questions;
    private String currentAnswer;
    private String currentQuestion;
    private ArrayList<String> solutions;
    private ArrayList<String> questionsText;
    private int correct;

    public QuizQuestionFragment(String quizCategory, String quizName, int quizId) {
        theCategory = quizCategory;
        theName = quizName;
        numberOfQuestions = 3; //Update this
        currentQuestionIndex = 0;
        id = quizId;
        questions = new ArrayList<>();

    }

    public static QuizQuestionFragment newInstance(String quizCategory, String quizName, int id) {
        QuizQuestionFragment fragment = new QuizQuestionFragment(quizCategory, quizName, id);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_question, container, false);

        username = getArguments().getString("username");

        questionNumber = rootView.findViewById(R.id.questionNumberTextView);
        questionDescription = rootView.findViewById(R.id.questionDescriptionTextView);
        radioButtonContainer = rootView.findViewById(R.id.radioGroupContainer);
        optionOne = rootView.findViewById(R.id.optionOneRadioButton);
        optionTwo = rootView.findViewById(R.id.optionTwoRadioButton);
        optionThree = rootView.findViewById(R.id.optionThreeRadioButton);
        optionFour = rootView.findViewById(R.id.optionFourRadioButton);
        solutions = new ArrayList<>();
        questionsText = new ArrayList<>();


        DataBaseHelper dataBaseHelper = new DataBaseHelper((getContext()));
        questions = dataBaseHelper.getQuestions(id);
        numberOfQuestions = questions.size();



               Toast toast = Toast.makeText(getContext(), questions.toString(),
               Toast.LENGTH_SHORT);
        toast.show();

        if (numberOfQuestions > 0) {
            loadQuestion(currentQuestionIndex);
        }




        return rootView;
    }

    public void loadQuestion(int questionToLoad) {

        if (questionToLoad == 0) { //Keep this if statement. Use it to load every question besides the last one
            currentQuestionOptionsAvailable = 4; //Update this number with database query results
            setAvailableRadioButtons(currentQuestionOptionsAvailable);
            questionNumber.setText("Question# 1");
            questionDescription.setText(questions.get(questionToLoad).getQuestion());
            optionOne.setText(questions.get(questionToLoad).getOption1());
            optionTwo.setText(questions.get(questionToLoad).getAnswer());
            optionThree.setText(questions.get(questionToLoad).getOption3());
            optionFour.setText(questions.get(questionToLoad).getOption2());

            currentAnswer = questions.get(questionToLoad).getAnswer();

            currentQuestion = questions.get(questionToLoad).getQuestion();
            questionsText.add(currentQuestion);
            solutions.add(currentAnswer);


//            questionNumber.setText("Question# 1");
//            questionDescription.setText("Who was the first president of the United States of America?");
//            optionOne.setText("George Washington");
//            optionTwo.setText("Barack Obama");
//            optionThree.setText("Thomas Jefferson");
        }


        else if (questionToLoad == 1) { //This is just for testing purposes. It can be deleted later
            radioButtonContainer.clearCheck();
            currentQuestionOptionsAvailable = 4; //Update this number with database query results
            setAvailableRadioButtons(currentQuestionOptionsAvailable);
            questionNumber.setText("Question# 2");
            questionDescription.setText(questions.get(questionToLoad).getQuestion());
            optionOne.setText(questions.get(questionToLoad).getOption1());
            optionTwo.setText(questions.get(questionToLoad).getOption3());
            optionThree.setText(questions.get(questionToLoad).getAnswer());
            optionFour.setText(questions.get(questionToLoad).getOption2());

            currentAnswer = questions.get(questionToLoad).getAnswer();
            currentQuestion = questions.get(questionToLoad).getQuestion();
            questionsText.add(currentQuestion);
            solutions.add(currentAnswer);

        }

        else { //Keep this else statement. It is to load the final question on the exam and hide the
               // next question button and replace it with the submit quiz button
            QuizInProgress parentActivity = (QuizInProgress) getActivity();
            parentActivity.showSubmitButton();
            radioButtonContainer.clearCheck();
            currentQuestionOptionsAvailable = 4; //Update this number with database query results
            setAvailableRadioButtons(currentQuestionOptionsAvailable);
            questionNumber.setText("Question# " + (questionToLoad+1));
            questionDescription.setText(questions.get(questionToLoad).getQuestion());
            optionOne.setText(questions.get(questionToLoad).getAnswer());
            optionTwo.setText(questions.get(questionToLoad).getOption1());
            optionThree.setText(questions.get(questionToLoad).getOption3());
            optionFour.setText(questions.get(questionToLoad).getOption2());

            currentAnswer = questions.get(questionToLoad).getAnswer();

            currentQuestion = questions.get(questionToLoad).getQuestion();
            questionsText.add(currentQuestion);
            solutions.add(currentAnswer);
        }

        currentQuestionIndex++;

        //
    }

    public void nextQuestion() {
        int idOfCheckedRadioButton = radioButtonContainer.getCheckedRadioButtonId();
        if (idOfCheckedRadioButton != -1) {
            RadioButton selectedRadioButton = (RadioButton) getView().findViewById(idOfCheckedRadioButton);
            String userResponse = (String) selectedRadioButton.getText();
            if (userResponse.equals(currentAnswer)) {
                correct++;
            }
            Toast.makeText(getContext(), userResponse, Toast.LENGTH_SHORT).show();
            loadQuestion(currentQuestionIndex);
        }

        else {
            Toast.makeText(getContext(), "Please select an option.", Toast.LENGTH_SHORT).show();
        }

    }

    public void submitQuiz(boolean timeRemaining) {
        if (timeRemaining == true) {
            int idOfCheckedRadioButton = radioButtonContainer.getCheckedRadioButtonId();
            if (idOfCheckedRadioButton != -1) {
                RadioButton selectedRadioButton = (RadioButton) getView().findViewById(idOfCheckedRadioButton);
                String userResponse = (String) selectedRadioButton.getText();
                Toast.makeText(getContext(), userResponse, Toast.LENGTH_SHORT).show();

                //UserDetails userInQuestion = new UserDetails(theUsername, 45); //Pass this info in
                Intent intent = new Intent(getActivity(), QuizResults.class);
                intent.putExtra("actionToTake", "WhatUp");
                intent.putExtra("quizId", String.valueOf(id));
                intent.putExtra("username", username);
                intent.putExtra("correct", String.valueOf(correct));
                intent.putExtra("numberOfQuestions", String.valueOf(numberOfQuestions));
                intent.putExtra("solutions", solutions);
                intent.putExtra("questions", questionsText);
                intent.putExtra("quizName", theName);
                intent.putExtra("category", theCategory);
                startActivity(intent);
            }

            else {
                Toast.makeText(getContext(), "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            boolean markQuestionWrong = true;
            Intent intent = new Intent(getActivity(), QuizResults.class);
            intent.putExtra("actionToTake", "WhatUp");
            startActivity(intent);
        }


    }

    public void setAvailableRadioButtons(int number) {
        switch (number) {
            case 0:
                optionOne.setVisibility(View.INVISIBLE);
                optionTwo.setVisibility(View.INVISIBLE);
                optionThree.setVisibility(View.INVISIBLE);
                optionFour.setVisibility(View.INVISIBLE);
                break;
            case 2:
                optionOne.setVisibility(View.VISIBLE);
                optionTwo.setVisibility(View.VISIBLE);
                optionThree.setVisibility(View.INVISIBLE);
                optionFour.setVisibility(View.INVISIBLE);
                break;
            case 3:
                optionOne.setVisibility(View.VISIBLE);
                optionTwo.setVisibility(View.VISIBLE);
                optionThree.setVisibility(View.VISIBLE);
                optionFour.setVisibility(View.INVISIBLE);
                break;
            default:
                optionOne.setVisibility(View.VISIBLE);
                optionTwo.setVisibility(View.VISIBLE);
                optionThree.setVisibility(View.VISIBLE);
                optionFour.setVisibility(View.VISIBLE);
        }
    }
}
