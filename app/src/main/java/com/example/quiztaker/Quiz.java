package com.example.quiztaker;

public class Quiz {

    private String quizName;
    private String quizCategory;
    private String timeLimit;

    public Quiz(String quizName, String quizCategory, String timeLimit) {
        this.quizName = quizName;
        this.quizCategory = quizCategory;
        this.timeLimit = timeLimit;
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
}
