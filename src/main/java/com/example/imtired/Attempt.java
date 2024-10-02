package com.example.imtired;

import javafx.util.Duration;

public class Attempt {
    private int numberOfTries;
    private boolean win;
    private String targetWord;
    private Duration elapsedTime;

    private int numberOfAttempt;


    public Attempt(int numberOfTries, boolean isWin, String targetWord, Duration elapsedTime, int numberOfAttempt){
        this.numberOfTries = numberOfTries;
        this.numberOfAttempt = numberOfAttempt;
        this.win = isWin;
        this.targetWord = targetWord;
        this.elapsedTime = elapsedTime;
    }

    public int getNumberOfAttempt(){
        return numberOfAttempt;
    }

    // Getter and setter methods for the instance variables

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public double getElapsedTime() {
        return elapsedTime.toSeconds();
    }

    public void setElapsedTime(Duration elapsedTime) {
        this.elapsedTime = elapsedTime;
    }


}