package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizController {
    private QuizModel model;
    private QuizView view;
    private HauptmenueController contr;
    private boolean tipUsed;
    private List<String> richtigeFragen;
    private List<String> falscheFragen;
    private int beantwortet;

    public QuizController(QuizModel model, QuizView view, HauptmenueController contr) {
        this.model = model;
        this.view = view;
        this.contr = contr;
        this.tipUsed = false;
        this.richtigeFragen = new ArrayList<String>();
        this.falscheFragen = new ArrayList<String>();
        this.beantwortet = 0;
        this.view.setController(this.contr);

        view.getSubmitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAnswer();
            }
        });

        view.getTipButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTip();
            }
        });

        // Quiz vorzeitig beenden
        view.getStopButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int antwort = JOptionPane.showConfirmDialog(view,
                        "Möchten Sie den Quiz wirklich beenden?",
                        "Quiz beenden",
                        JOptionPane.YES_NO_OPTION);

                if (antwort == JOptionPane.YES_OPTION) {
                    view.dispose();
                    new QuizAnalyse(
                            model.getScore(),
                            model.getTotalQuestions(),
                            beantwortet,
                            richtigeFragen,
                            falscheFragen,
                            true,
                            contr
                    );
                }
            }
        });

        view.getRetryButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartQuiz();
            }
        });

        view.getMainMenuButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                contr.getView().setVisible(true);
            }
        });

        updateView();
    }

    private void handleAnswer() {
        String frageText = model.getCurrentQuestion().getQuestion();
        String answer = view.getAnswer();
        String correctAnswer = model.getCurrentQuestion().getCorrectAnswer();
        boolean isCorrect = model.checkAnswer(answer);
        beantwortet++;

        // Frage zur richtigen oder falschen Liste hinzufügen
        if (isCorrect) {
            richtigeFragen.add(frageText);
            view.showAnswer("Richtig: " + correctAnswer);
        } else {
            falscheFragen.add(frageText);
            view.showAnswer("Falsch. Richtig wäre: " + correctAnswer);
        }

        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (model.hasMoreQuestions()) {
                    view.clearAnswerField();
                    view.hideAnswer();
                    tipUsed = false;
                    updateView();
                } else {
                    showResult();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();

        view.getSubmitButton().setEnabled(false);
        view.getTipButton().setEnabled(false);
        view.getStopButton().setEnabled(false);

        Timer enableTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.getSubmitButton().setEnabled(true);
                view.getTipButton().setEnabled(true);
                view.getStopButton().setEnabled(true);
            }
        });
        enableTimer.setRepeats(false);
        enableTimer.start();
    }

    private void showTip() {
        if (!tipUsed) {
            String answer = model.getCurrentQuestion().getCorrectAnswer();
            String tip = answer.length() <= 3 ?
                    "Länge: " + answer.length() + " Buchstaben" :
                    "Beginnt mit '" + answer.charAt(0) + "', Länge: " + answer.length();
            view.showTip(tip);
            tipUsed = true;
        } else {
            JOptionPane.showMessageDialog(view, "Tipp bereits verwendet.");
        }
    }

    private void updateView() {
        view.setQuestion(model.getCurrentQuestion().getQuestion());
        view.setProgress(beantwortet + 1, model.getTotalQuestions());
        view.clearAnswerField();
        view.hideAnswer();
    }

    private void showResult() {
        view.dispose();
        new QuizAnalyse(
                model.getScore(),
                model.getTotalQuestions(),
                beantwortet,
                richtigeFragen,
                falscheFragen,
                false,
                contr
        );
    }

    private void restartQuiz() {
        model.resetQuiz();
        view.dispose();
        new QuizController(
                new QuizModel(contr.getVerzeichnis() + "\\questions.txt", contr.getVerzeichnis() + "\\answers.txt"),
                new QuizView(),
                contr
        );
    }
}