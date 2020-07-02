package com.example.quiztaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AdminResults extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results_admin);

        DataBaseHelper dataBaseHelper = new DataBaseHelper((getApplicationContext()));
        ArrayList<Result> queryRes = dataBaseHelper.selectQuizResults();    // your going to call this query

        ArrayList<String> lvString = new ArrayList<>();

        listView = findViewById(R.id.listView);
        Result test = new Result("Bryan","Math","10","9/10","Q1");  // THIS IS DUMMY DATA TEST
        for (int i = 0; i < queryRes.size(); i++) {
            lvString.add(queryRes.get(i).getUsername() + " - " + queryRes.get(i).getQuizName() + " - " + queryRes.get(i).getCategory() + " - " +
                    queryRes.get(i).getScore());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                lvString );

        listView.setAdapter(arrayAdapter);


    }
}
