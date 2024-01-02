package Controller;

import Model.*;
import View.CompetitorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CompetitorController {

    private String databaseFile = "competitordata.txt";
    private CompetitorList competitorList = new CompetitorList();
    private CompetitorView view;

    private void addCompetitors() {
        //load staff data from file
        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new FileReader("competitordata.txt"));
            String inputLine = buff.readLine();  //read first line
            while(inputLine != null){
                processLine(inputLine);
                //read next line
                inputLine = buff.readLine();
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        finally  {
            try{
                buff.close();
            }
            catch (IOException ioe) {
                //don't do anything
            }
        }
    }

    private void processLine(String inputLine) {
        // Split line into parts
        String values[] = inputLine.split(",(?![^\\[]*\\])");

        // Ensure the case-insensitive match for CompetitorLevel
        CompetitorLevel level;
        try {
            // Trim the string to remove leading/trailing spaces
            level = CompetitorLevel.valueOf(values[3].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid CompetitorLevel: " + values[3]);
            return;
        }

        // Extract other values
        int compNo = Integer.parseInt(values[0].trim());
        Name name = new Name(values[1].trim());
        String country = values[2].trim();
        int age = Integer.parseInt(values[4].trim());
        String email = values[5].trim();
        double overallScore = Double.parseDouble(values[8].trim());

        // Extract scores from the array excluding the last element
        String scoresString = values[6].trim();  // assuming scores start at index 6
        scoresString = scoresString.substring(1, scoresString.length() - 1);  // remove leading '[' and trailing ']'
        int[] scores = Arrays.stream(scoresString.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();

        // Determine the category and create the appropriate competitor type
        Competitor competitor;
        if (values[values.length - 2].trim().equals("Boxing")) {
            competitor = new BoxingCompetitor(name, country, level, age, email, scores);
        } else if (values[values.length - 2].trim().equals("Cycling")) {
            competitor = new CyclingCompetitor(name, country, level, age, email, scores);
        } else {
            // Handle unknown category or provide appropriate fallback
            System.out.println("Unknown category: " + values[values.length - 1]);
            return;
        }

        competitor.setCompetitorNumber(compNo);
        competitor.setOverallScore(overallScore);
        // Add to the list
        competitorList.addDetails(competitor);
    }


    public CompetitorController(CompetitorView view){
        this.view = view;
        addCompetitors();

        this.view.viewTable(e -> {
            JFrame tableFrame = new JFrame("Competitors Table");
            tableFrame.setSize(600, 400);

            // Get the list of competitors
            ArrayList<Competitor> competitors = competitorList.getCompetitors();

            // Create a table model with column names
            String[] columnNames = {"Competitor Number", "Name", "Country", "Level", "Age", "Email", "Category", "Score", "Overall Score"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            // Populate the table model with competitor data
            for (Competitor competitor : competitors) {
                Object[] rowData = {
                        competitor.getCompetitorNumber(),
                        competitor.getName().getFullName(),
                        competitor.getCountry(),
                        competitor.getLevel(),
                        competitor.getAge(),
                        competitor.getEmail(),
                        competitor.getCategory(),
                        Arrays.toString(competitor.getScoreArray()),
                        competitor.getOverallScore()
                };
                tableModel.addRow(rowData);
            }

            // Create a JTable with the table model
            JTable table = new JTable(tableModel);

            // Enable sorting
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            table.setRowSorter(sorter);

            JScrollPane scrollPane = new JScrollPane(table);
            tableFrame.add(scrollPane);

            // Add sorting options
            JComboBox<String> sortingOptions = new JComboBox<>(new String[]{
                    "Competitor Number Asc", "Competitor Number Desc",
                    "Name Asc", "Name Desc",
                    "Overall Score Asc", "Overall Score Desc"
            });

            sortingOptions.addActionListener(event -> {
                String selectedOption = (String) sortingOptions.getSelectedItem();
                switch (selectedOption) {
                    case "Competitor Number Asc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
                        break;
                    case "Competitor Number Desc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));
                        break;
                    case "Name Asc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
                        break;
                    case "Name Desc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
                        break;
                    case "Overall Score Asc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(8, SortOrder.ASCENDING)));
                        break;
                    case "Overall Score Desc":
                        sorter.setRowFilter(null);
                        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(8, SortOrder.DESCENDING)));
                        break;
                    default:
                        sorter.setRowFilter(null);
                }
            });

            tableFrame.add(sortingOptions, BorderLayout.NORTH);

            tableFrame.setVisible(true);
        });

        this.view.registerCompetitor(e -> {
            JFrame registrationFrame = new JFrame("Competitor Registration");
            registrationFrame.setSize(300, 200);
            registrationFrame.setLayout(new GridLayout(6, 2));

            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();

            JLabel emailLabel = new JLabel("Email:");
            JTextField emailField = new JTextField();

            JLabel ageLabel = new JLabel("Age:");
            JTextField ageField = new JTextField();

            JLabel countryLabel = new JLabel("Country:");
            JTextField countryField = new JTextField();

            JLabel levelLabel = new JLabel("Competitor Level:");
            JComboBox<String> levelComboBox = new JComboBox<>(new String[]{"NOVICE", "INTERMEDIATE", "EXPERT"});

            JButton registerButton = new JButton("Register Boxing Competitor");

            JButton registerButton1 = new JButton("Register Cycling Competitor");

            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String country = countryField.getText();
                    String level = (String) levelComboBox.getSelectedItem();

                    // Create a Competitor object and register it
                    Competitor competitor = new BoxingCompetitor(new Name(name), country, CompetitorLevel.valueOf(level), age, email, new int[]{0, 0, 0});
                    competitorList.registerCompetitor(competitor);

                    // Call the new method to save competitors to the file
                    competitorList.saveCompetitors("competitordata.txt");

                    // Close the registration frame
                    registrationFrame.dispose();
                }
            });

            registerButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String country = countryField.getText();
                    String level = (String) levelComboBox. getSelectedItem();

                    // Create a Competitor object and register it
                    Competitor competitor = new CyclingCompetitor(new Name(name), country, CompetitorLevel.valueOf(level), age, email, new int[]{0, 0, 0});
                    competitorList.registerCompetitor(competitor);

                    // Call the new method to save competitors to the file
                    competitorList.saveCompetitors("competitordata.txt");

                    // Close the registration frame
                    registrationFrame.dispose();
                }
            });

            registrationFrame.add(nameLabel);
            registrationFrame.add(nameField);
            registrationFrame.add(emailLabel);
            registrationFrame.add(emailField);
            registrationFrame.add(ageLabel);
            registrationFrame.add(ageField);
            registrationFrame.add(countryLabel);
            registrationFrame.add(countryField);
            registrationFrame.add(levelLabel);
            registrationFrame.add(levelComboBox);
            registrationFrame.add(registerButton);
            registrationFrame.add(registerButton1);
            registrationFrame.setVisible(true);
        });

        this.view.viewScores(e -> {
            JFrame scoresFrame = new JFrame("View/Edit Scores");
            scoresFrame.setSize(300, 200);
            scoresFrame.setLayout(new GridLayout(4, 2));

            JLabel competitorNumberLabel = new JLabel("Competitor Number:");
            JTextField competitorNumberField = new JTextField();

            JLabel scoresLabel = new JLabel("Scores:");
            JTextField scoresField = new JTextField();

            JButton viewScoresButton = new JButton("View Scores");
            JButton editScoresButton = new JButton("Edit Scores");
            JButton calculateOverallScoreButton = new JButton("Calculate Overall Score");
            JButton viewOverallScoreButton = new JButton("View Overall Score");

            viewScoresButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                    Competitor competitor = competitorList.getCompetitorByNumber(competitorNumber);

                    if (competitor != null) {
                        // Display scores in a message dialog
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor Scores: " + Arrays.toString(competitor.getScoreArray()));
                    } else {
                        // Display an error message if the competitor is not found
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            viewOverallScoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                    Competitor competitor = competitorList.getCompetitorByNumber(competitorNumber);

                    if (competitor != null) {
                        // Display scores in a message dialog
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor Scores: " + competitor.getOverallScore());
                    } else {
                        // Display an error message if the competitor is not found
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            editScoresButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                    Competitor competitor = competitorList.getCompetitorByNumber(competitorNumber);

                    if (competitor != null) {
                        // Update scores and display a success message
                        String[] scoreStrings = scoresField.getText().split(",");
                        int[] scores = new int[scoreStrings.length];
                        for (int i = 0; i < scoreStrings.length; i++) {
                            scores[i] = Integer.parseInt(scoreStrings[i].trim());
                        }
                        competitor.setScoreArray(scores);
                        competitorList.saveCompetitors("competitordata.txt");

                        JOptionPane.showMessageDialog(scoresFrame, "Scores updated successfully.");
                    } else {
                        // Display an error message if the competitor is not found
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            calculateOverallScoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int competitorNumber = Integer.parseInt(competitorNumberField.getText());
                    Competitor competitor = competitorList.getCompetitorByNumber(competitorNumber);


                    if (competitor != null) {
                        // Get the input scores
                        String scoresInput = scoresField.getText();
                        String[] scoreStrings = scoresInput.split(",");
                        int[] newScores = Arrays.stream(scoreStrings)
                                .map(String::trim)
                                .mapToInt(Integer::parseInt)
                                .toArray();

                        // Update competitor's score array
                        competitor.setScoreArray(newScores);

                        // Calculate overall score
                        double overallScore = competitor.getOverallScore();

                        // Update competitor's overall score
                        competitor.setOverallScore(overallScore);

                        // Update the data in the file
                        competitorList.saveCompetitors("competitordata.txt");

                        // Display overall score in a message dialog
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor Overall Score: " + overallScore);
                    } else {
                        // Display an error message if the competitor is not found
                        JOptionPane.showMessageDialog(scoresFrame, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            scoresFrame.add(competitorNumberLabel);
            scoresFrame.add(competitorNumberField);
            scoresFrame.add(scoresLabel);
            scoresFrame.add(scoresField);
            scoresFrame.add(viewScoresButton);
            scoresFrame.add(editScoresButton);
            scoresFrame.add(calculateOverallScoreButton);
            scoresFrame.add(viewOverallScoreButton);

            scoresFrame.setVisible(true);
        });

        this.view.viewAttributes(e -> {
            JFrame attributesFrame = new JFrame("View Competitors Details By Attributes");
            attributesFrame.setSize(800, 600);
            attributesFrame.setLayout(new BorderLayout());

            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JPanel buttonPanel = new JPanel(new FlowLayout());

            JTextArea resultTextArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(resultTextArea);

            JLabel categoryLabel = new JLabel("Enter Category:");
            JTextField categoryField = new JTextField();

            JLabel levelLabel = new JLabel("Enter Level:");
            JTextField levelField = new JTextField();

            JLabel ageLabel = new JLabel("Enter Age:");
            JTextField ageField = new JTextField();

            JButton viewButton = new JButton("View Competitors By Category");
            JButton viewButton1 = new JButton("View Competitors By Level");
            JButton viewButton2 = new JButton("View Competitors By Age");

            viewButton.addActionListener(e1 -> {
                String category = categoryField.getText().trim();
                String categoryDetails = competitorList.viewDetailsByCategory(category);
                resultTextArea.setText(categoryDetails);
            });

            viewButton1.addActionListener(e2 -> {
                try {
                    CompetitorLevel level = CompetitorLevel.valueOf(levelField.getText().toUpperCase());
                    String levelDetails = competitorList.viewDetailsByLevel(level);
                    resultTextArea.setText(levelDetails);
                } catch (IllegalArgumentException ex) {
                    resultTextArea.setText("Invalid Competitor Level");
                }
            });

            viewButton2.addActionListener(e3 -> {
                try {
                    int age = Integer.parseInt(ageField.getText().trim());
                    String ageDetails = competitorList.viewDetailsByAge(age);
                    resultTextArea.setText(ageDetails);
                } catch (NumberFormatException ex) {
                    resultTextArea.setText("Invalid Age");
                }
            });

            inputPanel.add(categoryLabel);
            inputPanel.add(categoryField);
            inputPanel.add(levelLabel);
            inputPanel.add(levelField);
            inputPanel.add(ageLabel);
            inputPanel.add(ageField);

            buttonPanel.add(viewButton1);
            buttonPanel.add(viewButton2);
            buttonPanel.add(viewButton);

            attributesFrame.add(inputPanel, BorderLayout.NORTH);
            attributesFrame.add(buttonPanel, BorderLayout.SOUTH);
            attributesFrame.add(scrollPane, BorderLayout.CENTER);

            attributesFrame.setVisible(true);
        });

        this.view.viewShortDetails(e -> {
            JFrame detailsFrame = new JFrame("View Short Details");
            detailsFrame.setSize(300, 200);
            detailsFrame.setLayout(new GridLayout(4, 2));

            JLabel competitorNumberLabel = new JLabel("Competitor Number:");
            JTextField competitorNumberField = new JTextField();

            JTextArea resultTextArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JPanel buttonPanel = new JPanel(new FlowLayout());

            JButton viewButton = new JButton("View Short Details of the Competitor");

            viewButton.addActionListener(e1 -> {
                try {
                    int compNo = Integer.parseInt(competitorNumberField.getText().trim());
                    String fullDetailsByNumberDetails = competitorList.viewShortDetailsByNumber(compNo);
                    resultTextArea.setText(fullDetailsByNumberDetails);
                } catch (NumberFormatException ex) {
                    resultTextArea.setText("Invalid Competitor Number");
                }
            });

            inputPanel.add(competitorNumberLabel);
            inputPanel.add(competitorNumberField);

            buttonPanel.add(viewButton);

            detailsFrame.add(inputPanel, BorderLayout.NORTH);
            detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
            detailsFrame.add(scrollPane, BorderLayout.CENTER);

            detailsFrame.setVisible(true);
        });

        this.view.viewFullDetails(e -> {
            JFrame detailsFrame = new JFrame("View Full Details");
            detailsFrame.setSize(300, 200);
            detailsFrame.setLayout(new GridLayout(4, 2));

            JLabel competitorNumberLabel = new JLabel("Competitor Number:");
            JTextField competitorNumberField = new JTextField();

            JTextArea resultTextArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            JPanel buttonPanel = new JPanel(new FlowLayout());

            JButton viewButton = new JButton("View Full Details of the Competitor");

            viewButton.addActionListener(e2 -> {
                try {
                    int compNo = Integer.parseInt(competitorNumberField.getText().trim());
                    String fullDetailsByNumberDetails = competitorList.viewFullDetailsByNumber(compNo);
                    resultTextArea.setText(fullDetailsByNumberDetails);
                } catch (NumberFormatException ex) {
                    resultTextArea.setText("Invalid Competitor Number");
                }
            });

            inputPanel.add(competitorNumberLabel);
            inputPanel.add(competitorNumberField);

            buttonPanel.add(viewButton);

            detailsFrame.add(inputPanel, BorderLayout.NORTH);
            detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
            detailsFrame.add(scrollPane, BorderLayout.CENTER);

            detailsFrame.setVisible(true);
        });

        this.view.editDetails(e -> {
            JFrame editFrame = new JFrame("Edit Competitor Details");
            editFrame.setSize(400, 300);

            JPanel editPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);

            // Components
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

                        // Get the existing competitor
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
                            JOptionPane.showMessageDialog(editFrame, "Competitor details edited successfully.");
                            editFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(editFrame, "Competitor not found with number: " + competitorNumber, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(editFrame, "Invalid competitor number or input.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                };
            });
            editFrame.add(editPanel);
            editFrame.setVisible(true);
        });

        this.view.removeCompetitor(e -> {
            JFrame editFrame = new JFrame("Remove Competitor");
            editFrame.setSize(400, 300);

            JPanel editPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);

            // Components
            JLabel competitorNumberLabel = new JLabel("Competitor Number:");
            JTextField competitorNumberField = new JTextField(25);

            JButton removeCompetitorButton = new JButton("Remove Competitor");
            removeCompetitorButton.setPreferredSize(new Dimension(278, 40));

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            editPanel.add(competitorNumberLabel, gridBagConstraints);

            gridBagConstraints.gridx = 1;
            editPanel.add(competitorNumberField, gridBagConstraints);

            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 2;
            editPanel.add(removeCompetitorButton, gridBagConstraints);


            removeCompetitorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Prompt the user for the competitor number to remove

                    int competitorNumberInput = Integer.parseInt(competitorNumberField.getText());

                    // Validate the input
                    if (competitorNumberInput != 0) {
                        try {
                            int competitorNumber = competitorNumberInput;
                            competitorList.removeCompetitor(competitorNumber);
                            JOptionPane.showMessageDialog(editFrame, "Competitor removed successfully.");
                            editFrame.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(editFrame, "Invalid Competitor Number", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(editFrame, "Invalid Competitor Number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            editFrame.add(editPanel);
            editFrame.setVisible(true);
        });

        this.view.close(e -> {
            competitorList.generateFinalReport("final_report.txt");
            System.exit(0);
        });
    }
}