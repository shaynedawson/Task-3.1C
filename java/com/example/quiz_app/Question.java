package com.example.quiz_app;


public class Question {
    private String text;
    private String[] answers;
    private int correctAnswerIndex;

    // Constructor
    public Question(String text, String[] answers, int correctAnswerIndex) {
        this.text = text;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters
    public String getText() {
        return text;
    }


    public String[] getAnswers() {
        return answers;
    }


    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
