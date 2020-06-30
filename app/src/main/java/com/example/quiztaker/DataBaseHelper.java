package com.example.quiztaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String QUIZ_TABLE = "QUIZ_TABLE";
    public static final String STUDENTS_TABLE = "STUDENTS_TABLE";
    public static final String CATEGORY_TABLE = "CATEGORY_TABLE";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_PASSWORD = "COLUMN_PASSWORD";
    public static final String COLUMN_EMAIL = "COLUMN_EMAIL";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "quiztakerDBB", null , 1);
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


}
