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

    public static final String STUDENTS_TABLE = "STUDENTS_TABLE";
    public static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    public static final String COLUMN_PASSWORD = "COLUMN_PASSWORD";
    public static final String COLUMN_EMAIL = "COLUMN_EMAIL";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "quiztaker", null , 1);
    }

    // this is called the first time a database is accessed. The code in here will generate a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "create table "+ STUDENTS_TABLE + " (id integer primary key, username text, password text, email text, phone text)";
        db.execSQL(createTableStatement);

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


}
