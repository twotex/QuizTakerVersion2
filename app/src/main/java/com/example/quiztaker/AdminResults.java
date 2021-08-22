package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminResults extends AppCompatActivity {

    private Spinner spinnerStudents;
    private String theStudentsUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results_admin);

        final ListView theListView = findViewById(R.id.ListViewQuizResults);
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
        final TextView gradeTextBox = findViewById(R.id.letterGradeTextView);;
        final TextView scoreTextBox = findViewById(R.id.scoreTextView);;
        final TextView percentageTextBox = findViewById(R.id.percentageTextView);;
        final Button buttonBack = findViewById(R.id.backBtn);


        //ArrayList<Result> queryRes = dataBaseHelper.selectQuizResults();    // your going to call this query
        //ArrayList<String> lvString = new ArrayList<>();

        spinnerStudents = findViewById(R.id.spinnerStudents);

        ArrayList<User> userResults = dataBaseHelper.selectStudentInfo();

        ArrayList<String> userList = new ArrayList<>();
        userList.add("SELECT");
        for (int i = 0; i < userResults.size(); i++)
        {
                userList.add(userResults.get(i).getUsername());
        }

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, userList);
        spinnerArrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerStudents.setAdapter(spinnerArrayAdapter2);

        /////////////////////////////////////////////////////////////////////


        spinnerStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //ListView theListView = findViewById(R.id.ListViewQuizResults);
                theStudentsUsername = spinnerStudents.getSelectedItem().toString();
                final ArrayList<Quiz> listOfStudentQuizzes = dataBaseHelper.selectStudentQuizes(theStudentsUsername);
                final ArrayList<String> quizAndCategoryCombined = new ArrayList<String>();

                for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                    quizAndCategoryCombined.add(listOfStudentQuizzes.get(i).getQuizCategory()
                            + " - " + listOfStudentQuizzes.get(i).getQuizName());
                }
                final ArrayAdapter<String> adapter;

        /*
        adapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1,
                    quizAndCategoryCombined);
        */
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        quizAndCategoryCombined);
                theListView.setAdapter(adapter);

                gradeTextBox.setText("");
                scoreTextBox.setText("");
                percentageTextBox.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        /////////////////////////////////////////////////////////////////////





        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                theStudentsUsername = spinnerStudents.getSelectedItem().toString();
                final ArrayList<Quiz> listOfStudentQuizzes = dataBaseHelper.selectStudentQuizes(theStudentsUsername);


                String categoryClicked = listOfStudentQuizzes.get(position).getQuizCategory();
                String quizClicked = listOfStudentQuizzes.get(position).getQuizName();

                try {
                    ArrayList<Result> listOfQuizzes = dataBaseHelper.selectQuizResults();
                    ArrayList<Result> userQuizResults = new ArrayList<>();
                    for (int i = 0; i < listOfQuizzes.size(); i++) {
                        if (listOfQuizzes.get(i).getUsername().equals(theStudentsUsername)) {
                            userQuizResults.add(listOfQuizzes.get(i));
                        }
                    }

                    String quizScore = "";
                    for (int i = 0; i < userQuizResults.size(); i++) {
                        if (userQuizResults.get(i).getCategory().equals(categoryClicked) && userQuizResults.get(i).getQuizName().equals(quizClicked)) {
                            quizScore = (userQuizResults.get(i).getScore());
                            break;
                        }
                    }

                    String usersScore = quizScore;

                    QuizResults temp = new QuizResults();
                    gradeTextBox.setText(temp.findGrade(usersScore));
                    scoreTextBox.setText(usersScore);
                    percentageTextBox.setText(temp.findPercentage(usersScore));
                }

                catch(Exception e)
                {
                    gradeTextBox.setText("N/A");
                    scoreTextBox.setText("N/A");
                    percentageTextBox.setText("N/A");
                }

            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }
        });



        //listView = findViewById(R.id.listView);

        /*
        Result test = new Result("Bryan","Math","10","9/10","Q1");  // THIS IS DUMMY DATA TEST
        for (int i = 0; i < queryRes.size(); i++) {
            lvString.add(queryRes.get(i).getUsername() + " - " + queryRes.get(i).getQuizName() + " - " + queryRes.get(i).getCategory() + " - " +
                    queryRes.get(i).getScore());
        }
         */

        /*
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(

                this,
                android.R.layout.simple_list_item_1,
                lvString );

        listView.setAdapter(arrayAdapter);
        */
    }
}
