package com.example.quiztaker;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizMenuFragment extends Fragment {

    private String activityToDo;
    private int categoryPosition;
    private int specificQuizPosition;


    public QuizMenuFragment(String doThis) {
        activityToDo = doThis;
        categoryPosition = 0;
        specificQuizPosition = 0;
    }


    public static QuizMenuFragment newInstance(String userName) {
        QuizMenuFragment fragment = new QuizMenuFragment(userName);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DataBaseHelper db = new DataBaseHelper(getContext());


        if (activityToDo.equals("Display All Quizzes")) { //This code is executed when the user is looking up his grades by all quizzes
            View rootView = inflater.inflate(R.layout.quiz_list_view_combined, container, false);
            ListView theListView = rootView.findViewById(R.id.listViewAllQuizzes);

            ArrayList<String> quizNames = new ArrayList<String>();
            quizNames.add("Web Development Quiz");
            quizNames.add("Database Quiz");
            quizNames.add("Operating System Quiz");
            final ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1,
                    quizNames);
            theListView.setAdapter(adapter);

            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                    //adapter.add("Game Programming Quiz"); Works.
                    VariableGradeReport parentActivity = (VariableGradeReport) getActivity();
                    parentActivity.updateStats("A", "9/10", "90%");
                }
            });
            return rootView;
        }

        else if (activityToDo.equals("Display Category Quizzes")) { //This code is executed when the user is looking up his grades by category quizzes
            View rootView = inflater.inflate(R.layout.quiz_list_view_categorized, container, false);
            final ListView categoriesListView = rootView.findViewById(R.id.listViewCategories);
            final ListView specificQuizzesListView = rootView.findViewById(R.id.listViewSpecificQuizzes);

         //   ArrayList<Quiz> listOfStudentQuizzes = db.selectStudentQuizes(studentUsername);
//
            ArrayList<String> quizCategories = new ArrayList<>();                             // Gets all the quiz categories
            ArrayList<String> quizNames = new ArrayList<String>();
//
//
//            for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
//                quizCategories.add(listOfStudentQuizzes.get(i).getQuizCategory());
//            }

            quizCategories.add("Comp Sci");
            quizCategories.add("Math");
            quizCategories.add("Bio");
            quizCategories.add("Physics");

            final ArrayAdapter<String> categoryAdapter;
            final ArrayAdapter<String> specificQuizAdapter;

            categoryAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizCategories);
            categoriesListView.setAdapter(categoryAdapter);

            specificQuizAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizNames);
            specificQuizzesListView.setAdapter(specificQuizAdapter);

            categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    specificQuizAdapter.clear();

                    //Add quizzes here that are available for this category. Use the position variable to determine the category picked.
                    specificQuizAdapter.add("Web Development Quiz");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    specificQuizAdapter.add("Database Quiz");
                    specificQuizAdapter.add("Operating System Quiz");


                    specificQuizzesListView.setSelector(R.color.colorWhite);

                }
            });

            specificQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    VariableGradeReport parentActivity = (VariableGradeReport) getActivity();
                    parentActivity.updateStats("A", "9/10", "90%");

                    specificQuizzesListView.setSelector(R.color.colorDarkerGray);
                }
            });
            return rootView;
        }

        else { //This code is executed when we are about to take a quiz and we want to show the user the quizzes that are available to him
            final View rootView = inflater.inflate(R.layout.quiz_list_view_categorized, container, false);
            final ListView categoriesListView = rootView.findViewById(R.id.listViewCategories);
            final ListView specificQuizzesListView = rootView.findViewById(R.id.listViewSpecificQuizzes);

            // PLEASE SELECT A QUIZ TO TAKE - category
            final ArrayList<Quiz> listOfStudentQuizzes = db.selectStudentQuizes(getArguments().getString("txt"));

            ArrayList<String> quizCategories = new ArrayList<String>();
            final ArrayList<String> quizNames = new ArrayList<String>();


            for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                quizCategories.add(listOfStudentQuizzes.get(i).getQuizCategory());
                quizNames.add(listOfStudentQuizzes.get(i).getQuizName());
            }

//            quizCategories.add("Comp Sci");
//            quizCategories.add("Math");
//            quizCategories.add("Bio");
//            quizCategories.add("Physics");

            final ArrayAdapter<String> categoryAdapter;
            categoryAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizCategories);
            categoriesListView.setAdapter(categoryAdapter);


            // PLEASE SELECT A QUIZ TO TAKE - quiz name
            categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    TakeQuizDisplay parentActivity = (TakeQuizDisplay) getActivity();
                    parentActivity.setInvisibleSubmitButton();
                    categoryPosition = position;
                    ArrayAdapter<String> specificQuizAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizNames);
                    specificQuizzesListView.setAdapter(specificQuizAdapter);
                    specificQuizzesListView.setSelector(R.color.colorWhite);
                }


            });

            final View copyRootView = rootView; //can delete later

            specificQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    TakeQuizDisplay parentActivity = (TakeQuizDisplay) getActivity();
                    parentActivity.setVisibleSubmitButton();

                    specificQuizzesListView.setSelector(R.color.colorDarkerGray);
                    specificQuizPosition = position;

                    String categoryNameClicked = categoriesListView.getItemAtPosition(categoryPosition).toString();
                    String quizNameClicked = specificQuizzesListView.getItemAtPosition(specificQuizPosition).toString();
                    int quizId = -1;
                    for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                        if (listOfStudentQuizzes.get(i).getQuizName().equals(quizNameClicked) &&
                        listOfStudentQuizzes.get(i).getQuizCategory().equals(categoryNameClicked)) {
                            quizId = listOfStudentQuizzes.get(i).getId();
                        }
                    }
                    parentActivity.updateChosenOptions(categoryNameClicked, quizNameClicked, quizId);

//                    Toast toast = Toast.makeText(copyRootView.getContext(),
//                            "Category name: " +categoryNameClicked + "\nQuiz name: " + quizNameClicked + "\nQuizID: " + quizId,
//                            Toast.LENGTH_SHORT);
//                    toast.show();
                }
            });
            return rootView;
        }
    }
}
