package view;

import controller.HauptmenueController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizView extends JFrame {
    private JLabel questionLabel, answerLabel, progressLabel;
    private JTextField answerField;
    private JButton submitButton, tipButton, retryButton, mainMenuButton, backButton, stopButton;
    private JPanel mainPanel;
    private HauptmenueController contr;

    public QuizView() {
        setTitle("Quiz");
        setSize(650, 550);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // NORTH: Fortschritt und Frage
        JPanel northPanel = new JPanel(new BorderLayout(5, 5));

        progressLabel = new JLabel("", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        progressLabel.setForeground(Color.GRAY);
        northPanel.add(progressLabel, BorderLayout.NORTH);

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        northPanel.add(questionPanel, BorderLayout.CENTER);

        mainPanel.add(northPanel, BorderLayout.NORTH);

        // CENTER: Eingabe und Antwortanzeige
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        answerField.setPreferredSize(new Dimension(0, 40));
        centerPanel.add(answerField, BorderLayout.NORTH);

        answerLabel = new JLabel("", SwingConstants.CENTER);
        answerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        answerLabel.setPreferredSize(new Dimension(0, 40));
        centerPanel.add(answerLabel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // SOUTH: Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));

        // Obere Buttons: Tipp, Bestätigen, Beenden
        JPanel topButtonRow = new JPanel(new BorderLayout(10, 0));
        tipButton = new JButton("Tipp");
        submitButton = new JButton("Bestätigen");
        stopButton = new JButton("Quiz beenden");

        topButtonRow.add(tipButton, BorderLayout.WEST);
        topButtonRow.add(submitButton, BorderLayout.CENTER);
        topButtonRow.add(stopButton, BorderLayout.EAST);
        buttonPanel.add(topButtonRow, BorderLayout.NORTH);

        // Untere Buttons: nach Quiz-Ende
        JPanel bottomButtonPanel = new JPanel(new BorderLayout(10, 0));
        retryButton = new JButton("Neu starten");
        mainMenuButton = new JButton("Hauptmenü");
        backButton = new JButton("Zurück");

        retryButton.setVisible(false);
        mainMenuButton.setVisible(false);
        backButton.setVisible(false);

        bottomButtonPanel.add(retryButton, BorderLayout.WEST);
        bottomButtonPanel.add(mainMenuButton, BorderLayout.CENTER);
        bottomButtonPanel.add(backButton, BorderLayout.EAST);

        buttonPanel.add(bottomButtonPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (contr != null) {
                    dispose();
                    contr.getView().setVisible(true);
                }
            }
        });

        setVisible(true);
    }

    public void clearLayout() {
        mainPanel.removeAll();
    }

    public void setQuestion(String question) {
        questionLabel.setText("<html><div style='text-align: center;'>" + question + "</div></html>");
        answerLabel.setText("");
    }

    public void setProgress(int current, int total) {
        progressLabel.setText("Frage " + current + " von " + total);
    }

    public void showAnswer(String answer) {
        answerLabel.setText(answer);
        answerLabel.setForeground(new Color(0, 120, 0));
    }

    public void showTip(String tip) {
        answerLabel.setText("Tipp: " + tip);
        answerLabel.setForeground(new Color(50, 100, 200));
    }

    public void hideAnswer() {
        answerLabel.setText("");
    }

    public void clearAnswerField() {
        answerField.setText("");
    }

    public String getAnswer() {
        return answerField.getText();
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getTipButton() {
        return tipButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getRetryButton() {
        return retryButton;
    }

    public JButton getMainMenuButton() {
        return mainMenuButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setController(HauptmenueController con) {
        this.contr = con;
    }
}