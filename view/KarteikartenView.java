package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KarteikartenView extends JFrame {
    private JLabel cardLabel, solutionLabel;
    private JButton knowButton, nextButton, dontKnowButton, addButton, exitButton;

    public KarteikartenView() {
        setTitle("Lernkartei");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel cardPanel = new JPanel(new BorderLayout(5, 5));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        cardLabel = new JLabel("", SwingConstants.CENTER);
        cardLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        cardLabel.setPreferredSize(new Dimension(0, 120));

        solutionLabel = new JLabel("", SwingConstants.CENTER);
        solutionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        solutionLabel.setForeground(new Color(0, 120, 0));
        solutionLabel.setPreferredSize(new Dimension(0, 80));

        cardPanel.add(cardLabel, BorderLayout.CENTER);
        cardPanel.add(solutionLabel, BorderLayout.SOUTH);
        add(cardPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JPanel topButtons = new JPanel(new BorderLayout(10, 0));
        knowButton = new JButton("Gewusst");
        nextButton = new JButton("Nächste Karte");
        dontKnowButton = new JButton("Nicht gewusst");

        topButtons.add(knowButton, BorderLayout.WEST);
        topButtons.add(nextButton, BorderLayout.CENTER);
        topButtons.add(dontKnowButton, BorderLayout.EAST);

        JPanel bottomButtons = new JPanel(new BorderLayout(10, 0));
        exitButton = new JButton("Beenden");
        addButton = new JButton("Karte hinzufügen");

        bottomButtons.add(exitButton, BorderLayout.WEST);
        bottomButtons.add(addButton, BorderLayout.EAST);

        buttonPanel.add(topButtons, BorderLayout.NORTH);
        buttonPanel.add(bottomButtons, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setCardText(String text) {
        cardLabel.setText("<html><div style='text-align: center; padding: 10px;'>" + text + "</div></html>");
        solutionLabel.setText("");
    }

    public void showSolution(String solution) {
        solutionLabel.setText("<html><div style='text-align: center;'><b>Lösung:</b> " + solution + "</div></html>");
    }

    public void hideSolution() {
        solutionLabel.setText("");
    }

    public void addNextListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void addCorrectListener(ActionListener listener) {
        knowButton.addActionListener(listener);
    }

    public void addWrongListener(ActionListener listener) {
        dontKnowButton.addActionListener(listener);
    }

    public void addAddCardListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
}