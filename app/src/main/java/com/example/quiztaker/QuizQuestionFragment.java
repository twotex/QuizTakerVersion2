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

    private String currentAnswer;
    private ArrayList<Question> allQuestions;
    private String currentQuestion;
    private ArrayList<String> solutions;
    private ArrayList<String> questionsText;
    private int questionsAnsweredCorrect;


    public QuizQuestionFragment(String quizCategory, String quizName, int quizId, String theUsername) {
        username = theUsername;
        theCategory = quizCategory;
        theName = quizName;
        id = quizId;
        currentQuestionIndex = 0;
        allQuestions = new ArrayList<>();
    }

    public static QuizQuestionFragment newInstance(String quizCategory, String quizName, int id, String theUsername) {
        QuizQuestionFragment fragment = new QuizQuestionFragment(quizCategory, quizName, id, theUsername);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_question, container, false);

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
        allQuestions = dataBaseHelper.getQuestions(id);
        numberOfQuestions = allQuestions.size();

        if (numberOfQuestions > 0) {
            loadQuestion(currentQuestionIndex);
        }

        return rootView;
    }

    public void loadQuestion(int questionToLoad) {

        currentQuestionOptionsAvailable = 4; //Update this number with database query results
        setAvailableRadioButtons(currentQuestionOptionsAvailable);

        radioButtonContainer.clearCheck();
        questionNumber.setText("Question# " + (questionToLoad + 1));
        questionDescription.setText(allQuestions.get(questionToLoad).getQuestion());

        //Random number generator needed to put answer in a random location
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(4);

        if (randomInt == 0) {
            optionOne.setText(allQuestions.get(questionToLoad).getAnswer());
            optionTwo.setText(allQuestions.get(questionToLoad).getOption2());
            optionThree.setText(allQuestions.get(questionToLoad).getOption1());
            optionFour.setText(allQuestions.get(questionToLoad).getOption3());
        }

        else if (randomInt == 1) {
            optionOne.setText(allQuestions.get(questionToLoad).getOption1());
            optionTwo.setText(allQuestions.get(questionToLoad).getAnswer());
            optionThree.setText(allQuestions.get(questionToLoad).getOption3());
            optionFour.setText(allQuestions.get(questionToLoad).getOption2());
        }

        else if (randomInt == 2) {
            optionOne.setText(allQuestions.get(questionToLoad).getOption3());
            optionTwo.setText(allQuestions.get(questionToLoad).getOption2());
            optionThree.setText(allQuestions.get(questionToLoad).getAnswer());
            optionFour.setText(allQuestions.get(questionToLoad).getOption1());
        }

        else {
            optionOne.setText(allQuestions.get(questionToLoad).getOption2());
            optionTwo.setText(allQuestions.get(questionToLoad).getOption3());
            optionThree.setText(allQuestions.get(questionToLoad).getOption1());
            optionFour.setText(allQuestions.get(questionToLoad).getAnswer());
        }

        currentAnswer = allQuestions.get(questionToLoad).getAnswer();
        currentQuestion = allQuestions.get(questionToLoad).getQuestion();
        questionsText.add(currentQuestion);
        solutions.add(currentAnswer);
        currentQuestionIndex++;

        //Hides the next question button and replaced with submit button.
        //This executes on the very last question
        if (questionToLoad == numberOfQuestions - 1) {
            QuizInProgress parentActivity = (QuizInProgress) getActivity();
            parentActivity.showSubmitButton();
        }
    }

    //Loads the next questions when the user hits the "next question" button
    public void nextQuestion() {
        int idOfCheckedRadioButton = radioButtonContainer.getCheckedRadioButtonId();
        if (idOfCheckedRadioButton != -1) {
            RadioButton selectedRadioButton = (RadioButton) getView().findViewById(idOfCheckedRadioButton);
            String userResponse = (String) selectedRadioButton.getText();
            if (userResponse.equals(currentAnswer)) {
                questionsAnsweredCorrect++;
            }
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

                if (userResponse.equals(currentAnswer)) {
                    questionsAnsweredCorrect++;
                }

                Intent intent = new Intent(getActivity(), QuizResults.class);
                intent.putExtra("quizId", String.valueOf(id));
                intent.putExtra("username", username);
                intent.putExtra("correct", String.valueOf(questionsAnsweredCorrect));
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

        else { //USER RAN OUT OF TIME AND WAS NOT ABLE TO FINISH THE EXAM.
            Intent intent = new Intent(getActivity(), QuizResults.class);
            intent.putExtra("quizId", String.valueOf(id));
            intent.putExtra("username", username);
            intent.putExtra("correct", String.valueOf(questionsAnsweredCorrect));
            intent.putExtra("numberOfQuestions", String.valueOf(numberOfQuestions));
            intent.putExtra("solutions", solutions);
            intent.putExtra("questions", questionsText);
            intent.putExtra("quizName", theName);
            intent.putExtra("category", theCategory);
            startActivity(intent);
        }
    }

    //Hides specific radio buttons depending on the number of possible answers
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
