package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KarteikartenController {
    private KarteikartenModel model;
    private KarteikartenView view;
    private boolean showSolution;
    private List<String> questions;
    private List<String> answers;
    private Random random;
    private String currentQuestion;
    private HauptmenueController ctrl;
    private boolean changesMade;

    public KarteikartenController(KarteikartenModel model, KarteikartenView view,
                                  HauptmenueController contr, String verz) {
        this.model = model;
        this.view = view;
        this.random = new Random();
        this.ctrl = contr;
        this.showSolution = false;
        this.changesMade = false;

        // Lädt alle bestehenden Fragen und Antworten aus den Dateien
        loadQuestionsAndAnswers();

        currentQuestion = getRandomQuestion();
        view.setCardText(currentQuestion);

        view.addNextListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentQuestion = getRandomQuestion();
                view.setCardText(currentQuestion);
                showSolution = false;
            }
        });

        view.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Wenn neue Karten hinzugefügt wurden, fragen ob gespeichert werden soll
                if (changesMade) {
                    int antwort = JOptionPane.showConfirmDialog(view,
                            "Sie haben neue Karten hinzugefügt.\nMöchten Sie diese speichern?",
                            "Speichern?",
                            JOptionPane.YES_NO_OPTION);

                    if (antwort == JOptionPane.YES_OPTION) {
                        speichereAlleKarten();
                    }
                }
                new Ergebnis(model.getCorrectAnswers(), model.getWrongAnswers(), ctrl).setVisible(true);
                view.dispose();
                model.reset();
            }
        });

        view.addCorrectListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!showSolution) {
                    model.incrementCorrect();
                    view.showSolution(getAnswerForCurrentQuestion());
                    showSolution = true;
                }
            }
        });

        view.addWrongListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!showSolution) {
                    model.incrementWrong();
                    view.hideSolution();
                    showSolution = true;
                }
            }
        });

        view.addAddCardListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newQuestion = JOptionPane.showInputDialog(view, "Neue Frage:");
                if (newQuestion != null && !newQuestion.isEmpty()) {
                    String newAnswer = JOptionPane.showInputDialog(view, "Antwort:");
                    if (newAnswer != null && !newAnswer.isEmpty()) {
                        // Neue Karte zur Liste hinzufügen
                        questions.add(newQuestion);
                        answers.add(newAnswer);
                        model.addFlashcard(newQuestion, newAnswer);
                        changesMade = true;

                        // Sofort fragen ob gespeichert werden soll
                        int antwort = JOptionPane.showConfirmDialog(view,
                                "Karte hinzugefügt!\nMöchten Sie die Karte sofort in die Datei speichern?",
                                "Speichern?",
                                JOptionPane.YES_NO_OPTION);

                        if (antwort == JOptionPane.YES_OPTION) {
                            speichereAlleKarten();
                            // changesMade zurücksetzen, da bereits gespeichert
                            changesMade = false;
                        }
                    }
                }
            }
        });

        view.setVisible(true);
    }

    // Lädt die bestehenden Karten aus den Dateien
    private void loadQuestionsAndAnswers() {
        questions = new ArrayList<String>();
        answers = new ArrayList<String>();
        try {
            BufferedReader qReader = new BufferedReader(
                    new FileReader(ctrl.getVerzeichnis() + "\\terms.txt"));
            BufferedReader aReader = new BufferedReader(
                    new FileReader(ctrl.getVerzeichnis() + "\\definitions.txt"));

            String qLine, aLine;
            while ((qLine = qReader.readLine()) != null && (aLine = aReader.readLine()) != null) {
                if (!qLine.trim().isEmpty() && !aLine.trim().isEmpty()) {
                    questions.add(qLine.trim());
                    answers.add(aLine.trim());
                }
            }
            qReader.close();
            aReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Fehler beim Laden der Dateien.");
        }
    }

    // Überschreibt die Dateien mit ALLEN Karten (alte + neue)
    private void speichereAlleKarten() {
        try {
            String pfad = ctrl.getVerzeichnis();

            // Überschreibt terms.txt mit allen Fragen
            BufferedWriter termsWriter = new BufferedWriter(
                    new FileWriter(pfad + "\\terms.txt", false)); // false = überschreiben

            // Überschreibt definitions.txt mit allen Antworten
            BufferedWriter defsWriter = new BufferedWriter(
                    new FileWriter(pfad + "\\definitions.txt", false)); // false = überschreiben

            // Schreibt alle Fragen und Antworten (bestehende + neue)
            for (int i = 0; i < questions.size(); i++) {
                termsWriter.write(questions.get(i));
                termsWriter.newLine();
                defsWriter.write(answers.get(i));
                defsWriter.newLine();
            }

            termsWriter.close();
            defsWriter.close();

            JOptionPane.showMessageDialog(view,
                    "Gespeichert!\n" + questions.size() + " Karten insgesamt in der Datei.",
                    "Gespeichert",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(view,
                    "Fehler beim Speichern:\n" + e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getRandomQuestion() {
        if (questions.isEmpty()) {
            return "Keine Fragen verfügbar";
        }
        return questions.get(random.nextInt(questions.size()));
    }

    private String getAnswerForCurrentQuestion() {
        int index = questions.indexOf(currentQuestion);
        if (index != -1) {
            return answers.get(index);
        }
        return "";
    }
}