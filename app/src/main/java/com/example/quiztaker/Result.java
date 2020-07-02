package com.example.quiztaker;

public class Result {

    String username;
    String category;
    String quiz_id;
    String score;

    public Result(String username, String category, String quiz_id, String score) {
        this.username = username;
        this.category = category;
        this.quiz_id = quiz_id;
        this.score = score;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
