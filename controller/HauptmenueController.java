package controller;

import model.*;
import view.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HauptmenueController {
    private HauptmenueView view;
    private HauptmenueModel model;
    private String verzeichnis;

    public HauptmenueController(HauptmenueView view, HauptmenueModel model) {
        this.view = view;
        this.model = model;
        verzeichnis = "itp_programm";
        final HauptmenueController contr = this;
        final SpeichernLaden sl = new SpeichernLaden(contr);

        this.view.addLernkarteiListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.showMessage(model.getLernkarteiMessage());
                view.nextProgram();
                new KarteikartenController(new KarteikartenModel(), new KarteikartenView(), contr, verzeichnis);
            }
        });

        this.view.addQuizListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.showMessage(model.getQuizMessage());
                view.nextProgram();
                new QuizController(
                        new QuizModel(verzeichnis + "\\questions.txt", verzeichnis + "\\answers.txt"),
                        new QuizView(),
                        contr
                );
            }
        });

        this.view.addSpielListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.showMessage(model.getSpielMessage());
                view.nextProgram();
                String[] qa = getRandomQuestionAndAnswer(verzeichnis + "\\questions.txt", verzeichnis + "\\answers.txt");
                new Spiel(qa[0], qa[1], contr);
            }
        });

        this.view.addDateiListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.showMessage(model.getDateiMessage());
                view.nextProgram();
                sl.visibilty(true);
            }
        });

        this.view.addSchließenListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.closeProgram();
            }
        });
    }

    public HauptmenueView getView() {
        return view;
    }

    public String[] getRandomQuestionAndAnswer(String questionFile, String answerFile) {
        try {
            List<String> questions = Files.readAllLines(Paths.get(questionFile));
            List<String> answers = Files.readAllLines(Paths.get(answerFile));
            if (!questions.isEmpty() && !answers.isEmpty() && questions.size() == answers.size()) {
                int index = new Random().nextInt(questions.size());
                return new String[]{questions.get(index), answers.get(index)};
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{"Keine Frage verfügbar", "Keine Antwort"};
    }

    public void setVerzeichnis(String v) {
        this.verzeichnis = v;
    }

    public String getVerzeichnis() {
        return verzeichnis;
    }

    public static void main(String[] args) {
        HauptmenueModel model = new HauptmenueModel();
        HauptmenueView view = new HauptmenueView();
        new HauptmenueController(view, model);
        view.setVisible(true);
    }
}