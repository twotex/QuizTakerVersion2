package com.example.quiztaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String QUIZ_TABLE = "QUIZ_TABLE";
    public static final String STUDENTS_TABLE = "STUDENTS_TABLE";
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String QUIZ_STUDENTS_TABLE = "QUIZ_STUDENTS_TABLE";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_PASSWORD = "COLUMN_PASSWORD";
    public static final String COLUMN_EMAIL = "COLUMN_EMAIL";
    public static final String QUIZ_RESULTS = "QUIZ_RESULTS";
    private static final String QUIZ_QUESTIONS_TABLE = "QUIZ_QUESTIONS_TABLE";
    private static final String ADMINS_TABLE = "ADMINS_TABLE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "quizTaker", null , 1);
    }


    // this is called the first time a database is accessed. The code in here will generate a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table "+ STUDENTS_TABLE + " (id integer primary key, username text, password text, email text, phone text)";
        db.execSQL(createTableStatement);

        String createTableStatement2 = "create table "+ CATEGORY_TABLE + " (id integer primary key, category text UNIQUE)";
        db.execSQL(createTableStatement2);

        String createTableStatement3 = "create table "+ QUIZ_TABLE + " (id integer primary key, category text, quiz_name text, minutes text, UNIQUE(category,quiz_name), FOREIGN KEY(category) REFERENCES CATEGORY_TABLE(id))";
        db.execSQL(createTableStatement3);

        String createTableStatement4 = "create table "+ QUIZ_STUDENTS_TABLE + " (id integer primary key, quiz_name text, category text, studentName text, UNIQUE(quiz_name,category,studentName))";
        db.execSQL(createTableStatement4);

        String createTableStatement5 = "create table "+ QUIZ_QUESTIONS_TABLE + " (id integer primary key, id_quiz integer, question text, answer text, option1 text, option2 text, option3 text, UNIQUE(id_quiz, question), FOREIGN KEY(id_quiz) REFERENCES QUIZ_TABLE(id))";
        db.execSQL(createTableStatement5);

        String createTableStatement6 = "create table "+ ADMINS_TABLE + " (id integer primary key, username text, password text, type text, UNIQUE(username,password))";
        db.execSQL(createTableStatement6);

        String createTableStatement7 = "create table "+ QUIZ_RESULTS + " (id integer primary key, username text, category text, quiz_id text, score text, quiz_name text, UNIQUE(username, category, quiz_id))";
        db.execSQL(createTableStatement7);
    }

    // Whenever the version number of the database changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Inserts student into the database
    public boolean insertStudent(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", user.getUsername());
        cv.put("password", user.getPassword());
        cv.put("email", user.getEmail());
        cv.put("phone", user.getPhone());

        db.insert(STUDENTS_TABLE,null,cv);
        return true;
    }

    // Checks to see if username is already in database, returns true if not found
    public String selectUsernames(String username){

        String query = "SELECT * FROM STUDENTS_TABLE WHERE username =  \'"+ username + "\'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        c.getCount();
        return String.valueOf(c.getCount());
    }

    // Checks login credentials of student
    public boolean checkStudentLogin(String username, String password) {
        String query = "SELECT * FROM STUDENTS_TABLE WHERE username = \'"+ username + "\' AND password = \'"+ password + "\'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        c.getCount();
        if (c.getCount() > 0) {
            return true;
        }
        return false;

    }

    public long insertCategory(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("category", name);

        long res = db.insert(CATEGORY_TABLE,null,cv);
        return res;
    }

    public long insertQuiz(String quizName, String category, String minutes) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("quiz_name", quizName);
        cv.put("category", category);
        cv.put("minutes", minutes);

        long res = db.insert(QUIZ_TABLE,null,cv);
        return res;
    }

    public ArrayList<String> selectCategory() {
        String query = "SELECT * FROM CATEGORY_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<String> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("category");
                String name = c.getString(index);
                list.add(name);
            }
        } finally {
            c.close();
        }
    return list;
    }

    public ArrayList<Quiz> selectQuizNames(String categoryName) {
        String query = "SELECT * FROM QUIZ_TABLE WHERE category = \'"+ categoryName + "\'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("quiz_name");
                String name = c.getString(index);

                int index2 = c.getColumnIndexOrThrow("category");
                String category = c.getString(index2);

                int index3 = c.getColumnIndexOrThrow("minutes");
                String minutes = c.getString(index3);

                list.add(new Quiz(name,category,minutes));
            }
        } finally {
            c.close();
        }
        return list;
    }

    public long updateQuizTime(String quizName, String quizCategory, String minutes) {
        String query = "UPDATE QUIZ_TABLE SET minutes = " + minutes + " WHERE quiz_name = \'"+ quizName + "\' AND category = \'"+ quizCategory + "\'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return 1;
    }

    public ArrayList<User> selectStudentInfo() {
        String query = "SELECT * FROM STUDENTS_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<User> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("id");
                int id = c.getInt(index);

                int index2 = c.getColumnIndexOrThrow("username");
                String username = c.getString(index2);

                list.add(new User(id,username));
            }
        } finally {
            c.close();
        }
        return list;
    }

    public long insetStudentQuiz(String name, String quizName, String category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("quiz_name", quizName);
        cv.put("category", category);
        cv.put("studentName", name);

        long res = db.insert(QUIZ_STUDENTS_TABLE,null,cv);
        return res;
    }

    public Quiz selectQuiz(String quizName, String quizCategory) {
        String query = "SELECT * FROM QUIZ_TABLE WHERE quiz_name = \'"+ quizName + "\' AND category = \'"+ quizCategory + "\'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        Quiz quiz = null;
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("id");
                int id = c.getInt(index);
                quiz = new Quiz(quizName, quizCategory, id);
            }
        } finally {
            c.close();
        }
        return quiz;
    }

    public long insertQuestion(Quiz quiz, String question, String[] options) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("id_quiz", quiz.getId());
        cv.put("question", question);
        cv.put("answer", options[0]);
        cv.put("option1", options[1]);
        cv.put("option2", options[2]);
        cv.put("option3", options[3]);

        long res = db.insert(QUIZ_QUESTIONS_TABLE,null,cv);
        return res;
    }

    public boolean insertAdmin(String username, String password, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("password", password);
        cv.put("type", type);


        db.insert(ADMINS_TABLE,null,cv);
        return true;
    }

    public ArrayList<Quiz> selectStudentQuizes(String username) {
        String query = "SELECT * FROM QUIZ_STUDENTS_TABLE WHERE studentName = \'"+ username +"\'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<Quiz> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("id");
                int id = c.getInt(index);

                int index2 = c.getColumnIndexOrThrow("quiz_name");
                String quizName = c.getString(index2);

                int index3 = c.getColumnIndexOrThrow("category");
                String quizCategory = c.getString(index3);
                list.add(new Quiz(quizName,quizCategory,id));
            }
        } finally {
            c.close();
        }
        return list;
    }

    public Quiz findQuizIdandTime(String quizCategory, String quizName) {
        String query = "SELECT * FROM QUIZ_TABLE WHERE quiz_name = \'"+ quizName + "\' AND category = \'"+ quizCategory + "\'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        Quiz quiz = null;
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("id");
                int id = c.getInt(index);

                int index2 = c.getColumnIndex("minutes");
                int min = c.getInt(index2);
                quiz = new Quiz(quizName, quizCategory, id, min);

            }
        } finally {
            c.close();
        }
        return quiz;
    }

    public ArrayList<Question> getQuestions(int quizId) {
        String query = "SELECT * FROM QUIZ_QUESTIONS_TABLE WHERE id_quiz = \'"+ quizId + "\'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<Question> listQuestions = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("question");
                String question = c.getString(index);

                int index1 = c.getColumnIndexOrThrow("answer");
                String answer = c.getString(index1);

                int index2 = c.getColumnIndexOrThrow("option1");
                String option1 = c.getString(index2);

                int index3 = c.getColumnIndexOrThrow("option2");
                String option2 = c.getString(index3);

                int index4 = c.getColumnIndexOrThrow("option3");
                String option3 = c.getString(index4);

                listQuestions.add(new Question(question,answer,option1,option2,option3));


            }
        } finally {
            c.close();
        }
        return listQuestions;
    }

    public long insertQuiz(String username, String category, String quizID, String score, String quizName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("category", category);
        cv.put("quiz_id", String.valueOf(quizID));
        cv.put("score", score);
        cv.put("quiz_name", quizName);

        long res = db.insert(QUIZ_RESULTS,null,cv);
        return res;
    }

    public ArrayList<Result> selectQuizResults() {
        String query = "SELECT * FROM QUIZ_RESULTS";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(query,null);
        ArrayList<Result> list = new ArrayList<>();
        try {
            while (c.moveToNext()) {
                int index = c.getColumnIndexOrThrow("username");
                String username = c.getString(index);

                int index2 = c.getColumnIndexOrThrow("category");
                String category = c.getString(index2);

                int index3 = c.getColumnIndexOrThrow("quiz_id");
                String quizID = c.getString(index3);

                int index4 = c.getColumnIndexOrThrow("score");
                String score = c.getString(index4);

                int index5 = c.getColumnIndex("quiz_name");
                String quizName = c.getString(index5);

                list.add(new Result(username, category, quizID, score, quizName));
            }
        } finally {
            c.close();
        }
        return list;
    }
}
