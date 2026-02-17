package model;

import java.util.ArrayList;
import java.util.List;

public class KarteikartenModel {
    private List<String> karteikarten;
    private List<String> loesungen;
    private int correctAnswers;
    private int wrongAnswers;

    public KarteikartenModel() {
        karteikarten = new ArrayList<String>();
        loesungen = new ArrayList<String>();
        correctAnswers = 0;
        wrongAnswers = 0;
    }

    public void addFlashcard(String card, String solution) {
        karteikarten.add(card);
        loesungen.add(solution);
    }

    public void incrementCorrect() {
        correctAnswers++;
    }

    public void incrementWrong() {
        wrongAnswers++;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void reset() {
        correctAnswers = 0;
        wrongAnswers = 0;
    }

    public List<String> getAllCards() {
        return new ArrayList<String>(karteikarten);
    }

    public List<String> getAllSolutions() {
        return new ArrayList<String>(loesungen);
    }
}