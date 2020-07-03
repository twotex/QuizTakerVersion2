package com.example.quiztaker;

//The Quiz class is used to represent a Quiz + its category and time limit constraints
public class Quiz {
    private String quizName;
    private String quizCategory;
    private String timeLimit;
    private int id; //The id is used to identify the exact quiz
    private int minutes;

    public Quiz(String quizName, String quizCategory, String timeLimit) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.timeLimit = timeLimit;
    }

    public Quiz(String quizName, String quizCategory, int id) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.id = id;
    }

    public Quiz(String quizName, String quizCategory, int id, int minutes) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.id = id;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(String quizCategory) {
        this.quizCategory = quizCategory;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
