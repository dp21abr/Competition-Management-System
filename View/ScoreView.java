package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import Model.*;

public class ScoreView extends JFrame {
    private CompetitorList competitorList;
    private StaffList staffList = new StaffList();

    public ScoreView(CompetitorList competitorList) {
        super("View/Edit Scores");
        this.competitorList = competitorList;

        setSize(400, 300);
        setLayout(new GridLayout(5, 3));

        JLabel competitorNumberLabel = new JLabel("Competitor Number:");
        JTextField competitorNumberField = new JTextField();

        JLabel scoresLabel = new JLabel("Scores:");
        JTextField scoresField = new JTextField();

        JLabel staffIDLabel = new JLabel("StaffID:");
        JTextField staffIDField = new JTextField();

        JButton viewScoresButton = new JButton("View Scores");
        JButton editScoresButton = new JButton("Edit Scores");
        JButton calculateOverallScoreButton = new JButton("Calculate Overall Score");
        JButton viewOverallScoreButton = new JButton("View Overall Score");

        viewScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewScores(competitorNumberField.getText());
            }
        });

        editScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editScores(staffIDField.getText(), competitorNumberField.getText(), scoresField.getText());
            }
        });

        calculateOverallScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateOverallScore(competitorNumberField.getText(), scoresField.getText());
            }
        });

        viewOverallScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOverallScore(competitorNumberField.getText());
            }
        });

        add(staffIDLabel);
        add(staffIDField);
        add(competitorNumberLabel);
        add(competitorNumberField);
        add(scoresLabel);
        add(scoresField);
        add(viewScoresButton);
        add(editScoresButton);
        add(calculateOverallScoreButton);
        add(viewOverallScoreButton);

        setVisible(true);
    }

    private void viewScores(String competitorNumber) {
        int number = Integer.parseInt(competitorNumber);
        Competitor competitor = competitorList.getCompetitorByNumber(number);

        if (competitor != null) {
            JOptionPane.showMessageDialog(this, "Competitor Scores: " + Arrays.toString(competitor.getScoreArray()));
        } else {
            showError("Competitor not found with number: " + number);
        }
    }

    private void editScores(String staffId, String competitorNumber, String scoresInput) {
        int StaffID = Integer.parseInt(staffId);
        int number = Integer.parseInt(competitorNumber);
        Competitor competitor = competitorList.getCompetitorByNumber(number);

        Staff staff = staffList.getStaffByID(StaffID);
        if (staff.getStaffLevel() == StaffLevel.SENIOR || staff.getStaffLevel() == StaffLevel.INTERMEDIATE) {
            if (competitor != null) {
                String[] scoreStrings = scoresInput.split(",");
                int[] scores = Arrays.stream(scoreStrings).map(String::trim).mapToInt(Integer::parseInt).toArray();
                competitor.setScoreArray(scores);
                competitorList.saveCompetitors("competitordata.txt");
                showSuccess("Scores updated successfully.");
            } else {
                showError("Competitor not found with number: " + number);
            }
        } else {
            showError("The Staff with the Staff ID " + StaffID + " does not have the right to edit score");
        }
    }


    private void calculateOverallScore(String competitorNumber, String scoresInput) {
        int number = Integer.parseInt(competitorNumber);
        Competitor competitor = competitorList.getCompetitorByNumber(number);

        if (competitor != null) {
            String[] scoreStrings = scoresInput.split(",");
            int[] newScores = Arrays.stream(scoreStrings).map(String::trim).mapToInt(Integer::parseInt).toArray();
            competitor.setScoreArray(newScores);
            double overallScore = competitor.getOverallScore();
            competitor.setOverallScore(overallScore);
            competitorList.saveCompetitors("competitordata.txt");
            showSuccess("Competitor Overall Score: " + overallScore);
        } else {
            showError("Competitor not found with number: " + number);
        }
    }

    private void viewOverallScore(String competitorNumber) {
        int number = Integer.parseInt(competitorNumber);
        Competitor competitor = competitorList.getCompetitorByNumber(number);

        if (competitor != null) {
            JOptionPane.showMessageDialog(this, "Competitor Overall Score: " + competitor.getOverallScore());
        } else {
            showError("Competitor not found with number: " + number);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
