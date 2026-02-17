package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HauptmenueView {
    private JFrame frame;
    private JButton lernkarteiButton, quizButton, spielButton, dateiButton, schliessenButton;

    public HauptmenueView() {
        frame = new JFrame("ITP Lernprogramm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout(15, 15));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JLabel titleLabel = new JLabel("ITP Lernprogramm", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel topButtons = new JPanel(new BorderLayout(10, 0));
        lernkarteiButton = new JButton("Lernkartei");
        quizButton = new JButton("Quiz");
        spielButton = new JButton("Spiel");

        topButtons.add(lernkarteiButton, BorderLayout.WEST);
        topButtons.add(quizButton, BorderLayout.CENTER);
        topButtons.add(spielButton, BorderLayout.EAST);

        JPanel bottomButtons = new JPanel(new BorderLayout(10, 10));
        dateiButton = new JButton("Dateien verwalten");
        schliessenButton = new JButton("Beenden");

        bottomButtons.add(dateiButton, BorderLayout.NORTH);
        bottomButtons.add(schliessenButton, BorderLayout.SOUTH);

        centerPanel.add(topButtons, BorderLayout.NORTH);
        centerPanel.add(bottomButtons, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void addLernkarteiListener(ActionListener listener) {
        lernkarteiButton.addActionListener(listener);
    }

    public void addQuizListener(ActionListener listener) {
        quizButton.addActionListener(listener);
    }

    public void addSpielListener(ActionListener listener) {
        spielButton.addActionListener(listener);
    }

    public void addDateiListener(ActionListener listener) {
        dateiButton.addActionListener(listener);
    }

    public void addSchließenListener(ActionListener listener) {
        schliessenButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void closeProgram() {
        int result = JOptionPane.showConfirmDialog(frame,
                "Möchten Sie das Programm wirklich beenden?",
                "Beenden",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    public void nextProgram() {
        frame.dispose();
    }
}