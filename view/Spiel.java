package view;

import controller.HauptmenueController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spiel extends JFrame {
    private JLabel wordLabel, triesLabel, questionLabel;
    private JButton nextWordButton, backButton;
    private JPanel keyboardPanel;
    private int tries;
    private String answer;
    private String question;
    private char[] guessedAnswer;
    private JButton[] letterButtons;
    private HauptmenueController contr;

    public Spiel(String question, String answer, HauptmenueController contr) {
        this.answer = answer.trim().toUpperCase();
        this.question = question;
        this.guessedAnswer = new char[answer.length()];
        this.tries = 10;
        this.contr = contr;

        for (int i = 0; i < guessedAnswer.length; i++) {
            guessedAnswer[i] = (answer.charAt(i) == ' ') ? ' ' : '_';
        }

        setTitle("Hangman");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        questionLabel = new JLabel(question, SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel wordPanel = new JPanel(new BorderLayout());
        wordLabel = new JLabel(new String(guessedAnswer), SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 28));
        wordLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        wordLabel.setPreferredSize(new Dimension(0, 70));
        wordPanel.add(wordLabel, BorderLayout.CENTER);
        centerPanel.add(wordPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        triesLabel = new JLabel("Versuche übrig: " + tries, SwingConstants.CENTER);
        triesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(triesLabel, BorderLayout.CENTER);
        centerPanel.add(infoPanel, BorderLayout.CENTER);

        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 5));
        letterButtons = new JButton[26];

        for (char c = 'A'; c <= 'Z'; c++) {
            final char letter = c;
            JButton letterButton = new JButton(String.valueOf(c));
            letterButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    checkLetter(letter, (JButton) e.getSource());
                }
            });
            letterButtons[c - 'A'] = letterButton;
            keyboardPanel.add(letterButton);
        }
        centerPanel.add(keyboardPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        backButton = new JButton("Zurück");
        nextWordButton = new JButton("Nächstes Wort");

        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(nextWordButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                contr.getView().setVisible(true);
            }
        });

        nextWordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] next = contr.getRandomQuestionAndAnswer(
                        contr.getVerzeichnis() + "\\questions.txt",
                        contr.getVerzeichnis() + "\\answers.txt");
                updateGame(next[0], next[1]);
            }
        });

        setVisible(true);
    }

    private void checkLetter(char letter, JButton button) {
        boolean correct = false;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == letter) {
                guessedAnswer[i] = letter;
                correct = true;
            }
        }

        button.setEnabled(false);

        if (!correct) {
            tries--;
        }

        wordLabel.setText(new String(guessedAnswer));
        triesLabel.setText("Versuche übrig: " + tries);

        if (isAnswerGuessed()) {
            showWinningMessage();
        } else if (tries <= 0) {
            showLosingMessage();
        }
    }

    private void updateGame(String newQuestion, String newAnswer) {
        this.question = newQuestion;
        this.answer = newAnswer.trim().toUpperCase();
        this.guessedAnswer = new char[answer.length()];
        this.tries = 10;

        for (int i = 0; i < guessedAnswer.length; i++) {
            guessedAnswer[i] = (answer.charAt(i) == ' ') ? ' ' : '_';
        }

        wordLabel.setText(new String(guessedAnswer));
        questionLabel.setText(question);
        triesLabel.setText("Versuche übrig: " + tries);

        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
    }

    private boolean isAnswerGuessed() {
        for (char c : guessedAnswer) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private void showWinningMessage() {
        JOptionPane.showMessageDialog(this, "Richtig! Das Wort war: " + answer);
        disableAllButtons();
    }

    private void showLosingMessage() {
        JOptionPane.showMessageDialog(this, "Leider verloren. Das Wort war: " + answer);
        disableAllButtons();
    }

    private void disableAllButtons() {
        for (JButton button : letterButtons) {
            button.setEnabled(false);
        }
    }
}