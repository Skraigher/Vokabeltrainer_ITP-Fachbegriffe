package view;

import controller.HauptmenueController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ergebnis extends JFrame {
    public Ergebnis(int correct, int wrong, HauptmenueController contr) {
        setTitle("Ergebnis");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 15, 25));

        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Ihre Ergebnisse", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new BorderLayout(5, 5));
        JLabel correctLabel = new JLabel("Richtig: " + correct, SwingConstants.CENTER);
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JLabel wrongLabel = new JLabel("Falsch: " + wrong, SwingConstants.CENTER);
        wrongLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        statsPanel.add(correctLabel, BorderLayout.NORTH);
        statsPanel.add(wrongLabel, BorderLayout.CENTER);
        resultPanel.add(statsPanel, BorderLayout.CENTER);

        centerPanel.add(resultPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 20, 25));

        JButton backButton = new JButton("Zurück");
        buttonPanel.add(backButton, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                contr.getView().setVisible(true);
            }
        });
    }
}