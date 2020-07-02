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
import java.util.Arrays;
import java.util.List;

public class QuizMenuFragment extends Fragment {

    private String activityToDo;
    private int categoryPosition;
    private int specificQuizPosition;
    private String studentUsername;


    public QuizMenuFragment(String actionToTake, String userName) {
        activityToDo = actionToTake;
        studentUsername = userName;
        categoryPosition = 0;
        specificQuizPosition = 0;
    }


    public static QuizMenuFragment newInstance(String actionToTake, String userName) {
        QuizMenuFragment fragment = new QuizMenuFragment(actionToTake, userName);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DataBaseHelper db = new DataBaseHelper(getContext());

        if (activityToDo.equals("Display All Quizzes")) { //This code is executed when the user is looking up his grades by all quizzes
            View rootView = inflater.inflate(R.layout.quiz_list_view_combined, container, false);
            ListView theListView = rootView.findViewById(R.id.listViewAllQuizzes);

            final ArrayList<Quiz> listOfStudentQuizzes = db.selectStudentQuizes(studentUsername);

            final ArrayList<String> quizCategories = new ArrayList<String>();
            final ArrayList<String> quizNames = new ArrayList<String>();
            final ArrayList<String> quizAndCategoryCombined = new ArrayList<String>();


            for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                quizAndCategoryCombined.add(listOfStudentQuizzes.get(i).getQuizCategory()
                        + " - " + listOfStudentQuizzes.get(i).getQuizName());
            }
            final ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1,
                    quizAndCategoryCombined);
            theListView.setAdapter(adapter);

            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                    VariableGradeReport parentActivity = (VariableGradeReport) getActivity();
                    String usersScore = "3/15"; //UPDATE THIS WITH QUERY RESULTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    parentActivity.updateStats(findGrade(usersScore), usersScore, findPercentage(usersScore)); //UPDATE THIS WITH QUERY RESULTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
            });
            return rootView;
        }

        else if (activityToDo.equals("Display Category Quizzes")) { //This code is executed when the user is looking up his grades by category quizzes
            final View rootView = inflater.inflate(R.layout.quiz_list_view_categorized, container, false);
            final ListView categoriesListView = rootView.findViewById(R.id.listViewCategories);
            final ListView specificQuizzesListView = rootView.findViewById(R.id.listViewSpecificQuizzes);

            final ArrayList<Quiz> listOfStudentQuizzes = db.selectStudentQuizes(studentUsername);

            final ArrayList<String> quizCategories = new ArrayList<String>();
            final ArrayList<String> quizNames = new ArrayList<String>();

            for (int i = 0; i < listOfStudentQuizzes.size(); i++) {

                if (!quizCategories.contains(listOfStudentQuizzes.get(i).getQuizCategory())) {
                    quizCategories.add(listOfStudentQuizzes.get(i).getQuizCategory());
                }
            }

            final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizCategories);
            categoriesListView.setAdapter(categoryAdapter);

            final ArrayAdapter<String> specificQuizAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizNames);
            specificQuizzesListView.setAdapter(specificQuizAdapter);

            categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    specificQuizAdapter.clear();
                    VariableGradeReport parentActivity = (VariableGradeReport) getActivity();
                    parentActivity.updateStats("", "", "");
                    categoryPosition = position;
                    String theCategoryClicked = quizCategories.get(categoryPosition);

                    for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                        if (listOfStudentQuizzes.get(i).getQuizCategory().equals(theCategoryClicked)) {
                            specificQuizAdapter.add(listOfStudentQuizzes.get(i).getQuizName());
                        }
                    }
                    specificQuizzesListView.setSelector(R.color.colorWhite);
                }
            });

            specificQuizzesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    VariableGradeReport parentActivity = (VariableGradeReport) getActivity();
                    String usersScore = "3/15"; //UPDATE THIS WITH QUERY RESULTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    parentActivity.updateStats(findGrade(usersScore), usersScore, findPercentage(usersScore));
                    specificQuizzesListView.setSelector(R.color.colorDarkerGray);
                }
            });
            return rootView;
        }

        else { //This code is executed when activityToDo.equals("Take a Quiz")
            final View rootView = inflater.inflate(R.layout.quiz_list_view_categorized, container, false);
            final ListView categoriesListView = rootView.findViewById(R.id.listViewCategories);
            final ListView specificQuizzesListView = rootView.findViewById(R.id.listViewSpecificQuizzes);

            // PLEASE SELECT A QUIZ TO TAKE - category
            final ArrayList<Quiz> listOfStudentQuizzes = db.selectStudentQuizes(studentUsername);

            //Quiz object contains quiz name, quiz category, and id

            final ArrayList<String> quizCategories = new ArrayList<String>();
            final ArrayList<String> quizNames = new ArrayList<String>();

            for (int i = 0; i < listOfStudentQuizzes.size(); i++) {

                if (!quizCategories.contains(listOfStudentQuizzes.get(i).getQuizCategory())) {
                    quizCategories.add(listOfStudentQuizzes.get(i).getQuizCategory());
                }
            }

            final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizCategories);
            categoriesListView.setAdapter(categoryAdapter);

            final ArrayAdapter<String> specificQuizAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, quizNames);
            specificQuizzesListView.setAdapter(specificQuizAdapter);


            // PLEASE SELECT A QUIZ TO TAKE - quiz name
            categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    TakeQuizDisplay parentActivity = (TakeQuizDisplay) getActivity();
                    parentActivity.setInvisibleSubmitButton();
                    categoryPosition = position;
                    specificQuizAdapter.clear();
                    String theCategoryClicked = quizCategories.get(categoryPosition);

                    for (int i = 0; i < listOfStudentQuizzes.size(); i++) {
                        if (listOfStudentQuizzes.get(i).getQuizCategory().equals(theCategoryClicked)) {
                            specificQuizAdapter.add(listOfStudentQuizzes.get(i).getQuizName());
                        }
                    }
                    specificQuizzesListView.setSelector(R.color.colorWhite);
                }


            });

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
                    parentActivity.updateChosenOptions(categoryNameClicked, quizNameClicked);
                }
            });
            return rootView;
        }
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
