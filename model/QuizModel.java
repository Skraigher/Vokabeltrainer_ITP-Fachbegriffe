package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class QuizModel {
    private List<Fragen> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizModel(String questionFile, String answerFile) {
        this.questions = loadQuestionsFromFiles(questionFile, answerFile);
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    private List<Fragen> loadQuestionsFromFiles(String questionFile, String answerFile) {
        List<Fragen> loadedQuestions = new ArrayList<Fragen>();
        try {
            List<String> questionLines = Files.readAllLines(Paths.get(questionFile));
            List<String> answerLines = Files.readAllLines(Paths.get(answerFile));

            int maxQuestions = Math.min(10, Math.min(questionLines.size(), answerLines.size()));

            List<Integer> indices = new ArrayList<Integer>();
            for (int i = 0; i < maxQuestions; i++) {
                indices.add(i);
            }
            Collections.shuffle(indices);

            for (int i = 0; i < maxQuestions; i++) {
                int idx = indices.get(i);
                loadedQuestions.add(new Fragen(questionLines.get(idx), answerLines.get(idx)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedQuestions;
    }

    public Fragen getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean checkAnswer(String answer) {
        boolean isCorrect = getCurrentQuestion().isCorrect(answer);
        if (isCorrect) {
            score++;
        }
        currentQuestionIndex++;
        return isCorrect;
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
    }
}