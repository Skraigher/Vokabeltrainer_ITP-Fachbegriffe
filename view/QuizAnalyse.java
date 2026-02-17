package view;

import controller.HauptmenueController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class QuizAnalyse extends JFrame {
    public QuizAnalyse(int score, int total, int beantwortet,
                       List<String> richtigeFragen, List<String> falscheFragen,
                       boolean vorzeitigBeendet, HauptmenueController contr) {
        setTitle("Quiz Analyse");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // NORTH: Titel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        JLabel titleLabel = new JLabel("Quiz Analyse", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        if (vorzeitigBeendet) {
            JLabel hinweisLabel = new JLabel("(Quiz wurde vorzeitig beendet)", SwingConstants.CENTER);
            hinweisLabel.setFont(new Font("Arial", Font.ITALIC, 13));
            hinweisLabel.setForeground(Color.GRAY);
            titlePanel.add(hinweisLabel, BorderLayout.SOUTH);
        }

        add(titlePanel, BorderLayout.NORTH);

        // CENTER: Analyse-Inhalt
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Statistik-Panel
        JPanel statsPanel = new JPanel(new BorderLayout(5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Zusammenfassung"));

        JPanel zahlenPanel = new JPanel(new BorderLayout(5, 5));

        JLabel gesamtLabel = new JLabel("Fragen gesamt:       " + total, SwingConstants.LEFT);
        JLabel beantwortetLabel = new JLabel("Beantwortet:           " + beantwortet, SwingConstants.LEFT);
        JLabel richtigLabel = new JLabel("Richtig:                    " + score, SwingConstants.LEFT);
        JLabel falschLabel = new JLabel("Falsch:                     " + (beantwortet - score), SwingConstants.LEFT);

        int prozent = beantwortet > 0 ? (score * 100) / beantwortet : 0;
        JLabel prozentLabel = new JLabel("Trefferquote:           " + prozent + "%", SwingConstants.LEFT);
        prozentLabel.setFont(new Font("Arial", Font.BOLD, 14));

        if (prozent >= 75) {
            prozentLabel.setForeground(new Color(0, 130, 0));
        } else if (prozent >= 50) {
            prozentLabel.setForeground(new Color(200, 130, 0));
        } else {
            prozentLabel.setForeground(new Color(180, 0, 0));
        }

        gesamtLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        beantwortetLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        richtigLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        falschLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        zahlenPanel.add(gesamtLabel, BorderLayout.NORTH);

        JPanel mittelPanel = new JPanel(new BorderLayout(5, 5));
        mittelPanel.add(beantwortetLabel, BorderLayout.NORTH);
        mittelPanel.add(richtigLabel, BorderLayout.CENTER);

        JPanel untererMittelPanel = new JPanel(new BorderLayout(5, 5));
        untererMittelPanel.add(falschLabel, BorderLayout.NORTH);
        untererMittelPanel.add(prozentLabel, BorderLayout.CENTER);

        mittelPanel.add(untererMittelPanel, BorderLayout.SOUTH);
        zahlenPanel.add(mittelPanel, BorderLayout.CENTER);
        statsPanel.add(zahlenPanel, BorderLayout.CENTER);
        centerPanel.add(statsPanel, BorderLayout.NORTH);

        // Fragen-Panel: Richtige und Falsche Fragen
        JPanel fragenPanel = new JPanel(new BorderLayout(10, 10));

        // Richtige Fragen
        JPanel richtigPanel = new JPanel(new BorderLayout());
        richtigPanel.setBorder(BorderFactory.createTitledBorder("Richtig beantwortet (" + richtigeFragen.size() + ")"));
        JTextArea richtigArea = new JTextArea();
        richtigArea.setEditable(false);
        richtigArea.setFont(new Font("Arial", Font.PLAIN, 13));
        richtigArea.setForeground(new Color(0, 120, 0));
        richtigArea.setLineWrap(true);
        richtigArea.setWrapStyleWord(true);

        if (richtigeFragen.isEmpty()) {
            richtigArea.setText("Keine Fragen richtig beantwortet.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < richtigeFragen.size(); i++) {
                sb.append(i + 1).append(". ").append(richtigeFragen.get(i)).append("\n");
            }
            richtigArea.setText(sb.toString());
        }

        richtigPanel.add(new JScrollPane(richtigArea), BorderLayout.CENTER);

        // Falsche Fragen
        JPanel falschPanel = new JPanel(new BorderLayout());
        falschPanel.setBorder(BorderFactory.createTitledBorder("Falsch beantwortet (" + falscheFragen.size() + ")"));
        JTextArea falschArea = new JTextArea();
        falschArea.setEditable(false);
        falschArea.setFont(new Font("Arial", Font.PLAIN, 13));
        falschArea.setForeground(new Color(180, 0, 0));
        falschArea.setLineWrap(true);
        falschArea.setWrapStyleWord(true);

        if (falscheFragen.isEmpty()) {
            falschArea.setText("Alle Fragen richtig beantwortet!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < falscheFragen.size(); i++) {
                sb.append(i + 1).append(". ").append(falscheFragen.get(i)).append("\n");
            }
            falschArea.setText(sb.toString());
        }

        falschPanel.add(new JScrollPane(falschArea), BorderLayout.CENTER);

        fragenPanel.add(richtigPanel, BorderLayout.NORTH);
        fragenPanel.add(falschPanel, BorderLayout.CENTER);
        centerPanel.add(fragenPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // SOUTH: Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        JButton zurueckButton = new JButton("Zurück zum Hauptmenü");
        buttonPanel.add(zurueckButton, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        zurueckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                contr.getView().setVisible(true);
            }
        });

        setVisible(true);
    }
}