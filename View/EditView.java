package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import Model.*;

public class EditView extends JFrame {
    private CompetitorList competitorList;
    private StaffList staffList = new StaffList();

    public EditView(CompetitorList competitorList) {
        super("Edit Competitor Details");
        this.competitorList = competitorList;

        setSize(500, 400);

        JPanel editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);


        // Components
        JLabel staffIDLabel = new JLabel("StaffID:");
        JTextField staffIDField = new JTextField(25);

        JLabel competitorNumberLabel = new JLabel("Competitor Number:");
        JTextField competitorNumberField = new JTextField(25);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(25);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(25);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(25);

        JLabel countryLabel = new JLabel("Country:");
        JTextField countryField = new JTextField(25);

        JLabel levelLabel = new JLabel("Competitor Level:");
        JComboBox<String> levelComboBox = new JComboBox<>(new String[]{"NOVICE", "INTERMEDIATE", "EXPERT"});

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Boxing", "Cycling"});

        JLabel scoresLabel = new JLabel("Scores (comma-separated): ");
        JTextField scoresField = new JTextField(25);

        JButton editButton = new JButton("Edit Competitor Details");
        editButton.setPreferredSize(new Dimension(278, 40));

        // Add components to the panel
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        editPanel.add(competitorNumberLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(competitorNumberField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(staffIDLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(staffIDField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(nameField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(emailLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(emailField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(ageLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(ageField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(countryLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(countryField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(levelLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(levelComboBox, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(categoryLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(categoryComboBox, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        editPanel.add(scoresLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(scoresField, gridBagConstraints);
        // Add other components in a similar manner...

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        editPanel.add(editButton, gridBagConstraints);

        // Action listener for the edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                    int StaffID = Integer.parseInt(staffIDField.getText());

                    Staff staff = staffList.getStaffByID(StaffID);
                    if (staff.getStaffLevel() == StaffLevel.SENIOR) {
                        Competitor existingCompetitor = competitorList.getCompetitorByNumber(competitorNumber);

                        if (existingCompetitor != null) {
                            // Get the new values from the fields
                            String newName = (nameField.getText().isEmpty()) ? existingCompetitor.getName().getFullName() : nameField.getText();
                            String newCountry = (countryField.getText().isEmpty()) ? existingCompetitor.getCountry() : countryField.getText();
                            String newLevel = (String) levelComboBox.getSelectedItem();
                            String newCategory = (String) categoryComboBox.getSelectedItem();
                            int newAge = (ageField.getText().isEmpty()) ? existingCompetitor.getAge() : Integer.parseInt(ageField.getText());
                            String newEmail = (emailField.getText().isEmpty()) ? existingCompetitor.getEmail() : emailField.getText();
                            int[] newScores = (scoresField.getText().isEmpty()) ? existingCompetitor.getScoreArray() : Arrays.stream(scoresField.getText().split(","))
                                    .map(String::trim)
                                    .mapToInt(Integer::parseInt)
                                    .toArray();

                            // Edit the competitor details
                            competitorList.editCompetitorDetails(competitorNumber, new Name(newName), newCountry, newLevel, newAge, newEmail, newScores, newCategory);
                            JOptionPane.showMessageDialog(EditView.this, "Competitor details edited successfully.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(EditView.this, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(EditView.this, "The Staff with the Staff ID " + StaffID + " does not have the right to edit competitor");
                    }
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(EditView.this, "Invalid competitor number or input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
        });
        add(editPanel);
        setVisible(true);
    }
}

