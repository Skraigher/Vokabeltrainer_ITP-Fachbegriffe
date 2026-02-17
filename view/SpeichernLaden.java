package view;

import controller.HauptmenueController;
import model.StandardInhalte;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class SpeichernLaden extends JFrame {
    private JButton speichernButton, ladenButton, zurueckButton, infoButton;
    private String[] dateien = {"terms.txt", "definitions.txt", "questions.txt", "answers.txt"};

    public SpeichernLaden(HauptmenueController ctrl) {
        setTitle("Dateiverwaltung");
        setSize(420, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        speichernButton = new JButton("Speichern");
        ladenButton = new JButton("Laden");
        infoButton = new JButton("Inhalte anzeigen");
        zurueckButton = new JButton("Zurück");

        JPanel mainButtons = new JPanel(new BorderLayout(10, 10));
        JPanel topButtons = new JPanel(new BorderLayout(10, 10));

        topButtons.add(speichernButton, BorderLayout.NORTH);
        topButtons.add(ladenButton, BorderLayout.SOUTH);
        mainButtons.add(topButtons, BorderLayout.NORTH);
        mainButtons.add(infoButton, BorderLayout.CENTER);

        buttonPanel.add(mainButtons, BorderLayout.NORTH);
        buttonPanel.add(zurueckButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.CENTER);

        speichernButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speichern(ctrl);
            }
        });

        ladenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                laden(ctrl);
            }
        });

        infoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String info = StandardInhalte.getStatistiken() + "\n\nDiese Inhalte werden für neue Dateien verwendet.";
                JOptionPane.showMessageDialog(SpeichernLaden.this, info, "Standard-Inhalte", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        zurueckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ctrl.getView().setVisible(true);
            }
        });
    }

    private void speichern(HauptmenueController ctrl) {
        String pfad = JOptionPane.showInputDialog(this, "Zielordner eingeben:");
        if (pfad == null || pfad.isEmpty()) {
            return;
        }

        File ordner = new File(pfad);
        try {
            if (!ordner.exists()) {
                ordner.mkdirs();
            }

            String quelle = ctrl.getVerzeichnis();
            int kopiert = 0;

            for (String datei : dateien) {
                File quellDatei = new File(quelle, datei);
                File zielDatei = new File(ordner, datei);
                if (quellDatei.exists()) {
                    kopiereDatei(quellDatei, zielDatei);
                    kopiert++;
                }
            }

            if (kopiert > 0) {
                JOptionPane.showMessageDialog(this,
                        "Gespeichert!\n" + kopiert + " von " + dateien.length + " Dateien kopiert.",
                        "Erfolg",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Keine Dateien zum Kopieren gefunden.",
                        "Warnung",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern:\n" + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void laden(HauptmenueController ctrl) {
        String pfad = JOptionPane.showInputDialog(this, "Ordner zum Laden eingeben:");
        if (pfad == null || pfad.isEmpty()) {
            return;
        }

        File ordner = new File(pfad);
        if (!ordner.exists()) {
            int antwort = JOptionPane.showConfirmDialog(this,
                    "Ordner nicht gefunden.\n\nMit Standard-Inhalten erstellen?",
                    "Ordner erstellen",
                    JOptionPane.YES_NO_OPTION);

            if (antwort == JOptionPane.YES_OPTION) {
                if (ordnerErstellen(pfad)) {
                    ctrl.setVerzeichnis(pfad);
                }
            }
            return;
        }

        int gefunden = 0;
        StringBuilder fehlt = new StringBuilder();
        for (String datei : dateien) {
            File f = new File(ordner, datei);
            if (f.exists()) {
                gefunden++;
            } else {
                fehlt.append("- ").append(datei).append("\n");
            }
        }

        if (gefunden == dateien.length) {
            ctrl.setVerzeichnis(pfad);
            JOptionPane.showMessageDialog(this,
                    "Ordner geladen!\nAlle Dateien gefunden.",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            int antwort = JOptionPane.showConfirmDialog(this,
                    "Fehlende Dateien:\n" + fehlt + "\nErstellen?",
                    "Dateien fehlen",
                    JOptionPane.YES_NO_OPTION);

            if (antwort == JOptionPane.YES_OPTION) {
                if (dateienErstellen(pfad)) {
                    ctrl.setVerzeichnis(pfad);
                    JOptionPane.showMessageDialog(this,
                            "Dateien erstellt und geladen!",
                            "Erfolg",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private boolean ordnerErstellen(String pfad) {
        try {
            File ordner = new File(pfad);
            ordner.mkdirs();
            dateienErstellen(pfad);
            JOptionPane.showMessageDialog(this,
                    "Ordner erstellt mit Standard-Inhalten!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Fehler: " + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean dateienErstellen(String pfad) {
        try {
            for (String datei : dateien) {
                File f = new File(pfad, datei);
                if (!f.exists()) {
                    dateiErstellen(f, datei);
                }
            }
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Fehler: " + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void dateiErstellen(File datei, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(datei));
        List<String> inhalt = null;

        if (name.equals("terms.txt")) {
            inhalt = StandardInhalte.getStandardTerms();
        } else if (name.equals("definitions.txt")) {
            inhalt = StandardInhalte.getStandardDefinitions();
        } else if (name.equals("questions.txt")) {
            inhalt = StandardInhalte.getStandardQuestions();
        } else if (name.equals("answers.txt")) {
            inhalt = StandardInhalte.getStandardAnswers();
        }

        if (inhalt != null) {
            for (String zeile : inhalt) {
                writer.write(zeile);
                writer.newLine();
            }
        }
        writer.close();
    }

    private void kopiereDatei(File quelle, File ziel) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(quelle));
        BufferedWriter writer = new BufferedWriter(new FileWriter(ziel));
        String zeile;
        while ((zeile = reader.readLine()) != null) {
            writer.write(zeile);
            writer.newLine();
        }
        reader.close();
        writer.close();
    }

    public void visibilty(boolean g) {
        setVisible(g);
    }
}