package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UserController {

    private String databaseFile = "competitordata.csv";
    private CompetitorList competitorList = new CompetitorList();
    private UserView view;

    private void addCompetitors() {
        //load staff data from file
        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new FileReader("competitordata.csv"));
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


    public UserController(UserView view){
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
            new RegisterView(competitorList);
        });

        this.view.viewScores(e -> {
            new ScoreView(competitorList);
        });

        this.view.viewAttributes(e -> {
            new AttributesView(competitorList);
        });

        this.view.viewShortDetails(e -> {
            new ShortDetailsView(competitorList);
        });

        this.view.viewFullDetails(e -> {
            new FullDetailsView(competitorList);
        });

        this.view.editDetails(e -> {
            new EditView(competitorList);
        });

        this.view.removeCompetitor(e -> {
            new RemoveView(competitorList);
        });

        this.view.close(e -> {
            competitorList.generateFinalReport("final_report.txt");
            System.exit(0);
        });
    }
}