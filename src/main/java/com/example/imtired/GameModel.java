package com.example.imtired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameModel {

    private HashMap<String, String> words;
    private List<String> wordsInList;
    private String targetWord;
    private final int maxNumOfTries;
    private final int maxNumOfLetters;
    private int currentRow;
    private List<Attempt> attemptsList;


    public GameModel(int maxNumOfTries, int maxNumOfLetters, String PATH_TO_FILE) {
        initializeWords(PATH_TO_FILE);
        this.wordsInList = new ArrayList<>(words.keySet());
        this.maxNumOfTries = maxNumOfTries;
        this.maxNumOfLetters = maxNumOfLetters;
        this.attemptsList = new ArrayList<>();
        generateRandomTargetWord();
    }


    private void initializeWords(String PATH_TO_FILE) {
        // Load words from an external source using IOUtils.ReadWords
        try {
            words = new HashMap<>();
            IOUtils.ReadWords(words, PATH_TO_FILE); // Load words from file
            wordsInList = new ArrayList<>(words.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String word){
        return words.containsKey(word);
    }



    public String getTargetWord() {
        return targetWord;
    }

    public boolean isGameOver() {
        return currentRow >= maxNumOfTries;
    }

    public boolean isWordGuessed(String guess) {
        return guess.equals(targetWord);
    }

    public void addAttempt(Attempt attempt) {
        attemptsList.add(attempt);
    }

    public void incrementRow() {
        currentRow++;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getMaxNumOfLetters(){
        return maxNumOfLetters;
    }

    public int getMaxNumOfTries(){
        return maxNumOfTries;
    }

    void generateRandomTargetWord() {
        Random rand = new Random();
        int randInd = rand.nextInt(wordsInList.size());
        targetWord = wordsInList.get(randInd);
    }

    public void resetGame() {
        generateRandomTargetWord();
        currentRow = 0;
    }
    public List<Attempt> getAttempts() {
        return attemptsList;
    }
}