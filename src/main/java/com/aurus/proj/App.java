package com.aurus.proj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class App {
    private static JFrame mainFrame;
    private static List<String> allPenalties = App.initPenaltyList();

    public static void main(String[] args) {
        App.initFrame();
        mainFrame.getContentPane().add(initMainPanel());
        mainFrame.pack();
    }

    public static void initFrame() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Random Penalty Generator");
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
    }

    private static JPanel initMainPanel() {
        JPanel mainPanel = new JPanel();
        JLabel mainTitle1 = new JLabel("Random Penalty", JLabel.CENTER);
        JLabel mainTitle2 = new JLabel("Generator", JLabel.CENTER);
        JButton generateButton = new JButton("Generate");
        JProgressBar generateProgressBar = new JProgressBar(0, 100);

        mainPanel.setPreferredSize(new Dimension(600, 400));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        mainTitle1.setBounds(0, 80, 600, 60);
        mainTitle1.setFont(new Font("Ariel", Font.PLAIN, 45));
        mainTitle1.setForeground(Color.white);

        mainTitle2.setBounds(0, 120, 600, 60);
        mainTitle2.setFont(new Font("Ariel", Font.PLAIN, 45));
        mainTitle2.setForeground(Color.white);

        generateButton.setBounds(225, 300, 150, 25);
        generateButton.setBackground(Color.white);
        generateButton.setFont(new Font("Ariel", Font.PLAIN, 12));
        generateButton.setBorder(BorderFactory.createLineBorder(Color.white));
        generateButton.addActionListener((ActionEvent e) -> {
            generateButton.setVisible(false);
            generateProgressBar.setVisible(true);
            App.generatingProgressBar(generateProgressBar, generateButton, mainTitle1, mainTitle2);
        });

        generateProgressBar.setBounds(50, 350, 500, 25);
        generateProgressBar.setValue(0);
        generateProgressBar.setString("Generating...");
        generateProgressBar.setStringPainted(true);
        generateProgressBar.setFont(new Font("Ariel", Font.PLAIN, 12));
        generateProgressBar.setVisible(false);
        generateProgressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() {
                return Color.white;
            }

            @Override
            protected Color getSelectionForeground() {
                return Color.white;
            }
        });

        mainPanel.add(mainTitle1);
        mainPanel.add(mainTitle2);
        mainPanel.add(generateButton);
        mainPanel.add(generateProgressBar);
        return mainPanel;
    }

    private static void generatingProgressBar(JProgressBar generateProgressBar, JButton generateButton,
            JLabel mainTitle1, JLabel mainTitle2) {
        Thread pBarLoadingThread = new Thread(() -> {
            int i = 0;
            try {
                while (i < 100) {
                    generateProgressBar.setValue(i + 1);
                    Thread.sleep(100);
                    i += 1;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            generateProgressBar.setVisible(false);
            generateButton.setVisible(true);
            mainTitle1.setText("Penalty");
            mainTitle2.setFont(new Font("Ariel", Font.PLAIN, 13));
            mainTitle2.setText(String.format("\"%s\"", allPenalties.get(getRandomNumber())));
        });
        pBarLoadingThread.start();
    }

    private static List<String> initPenaltyList() {
        Path pathToFile = Paths
                .get("/path/to/file/");
        List<String> allPenalties = new ArrayList<>();

        try {
            allPenalties.addAll(Files.readAllLines(pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allPenalties;
    }

    private static int getRandomNumber() {
        Random randomNumberGenerator = new Random();
        return randomNumberGenerator.nextInt(allPenalties.size());
    }
}
