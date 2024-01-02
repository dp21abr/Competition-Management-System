package View;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class AttributesView extends JFrame {
    private CompetitorList competitorList;

    public AttributesView(CompetitorList competitorList) {
        super("View Competitors Details By Attributes");
        this.competitorList = competitorList;

        setSize(800, 600);
        setLayout(new BorderLayout());

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

        buttonPanel.add(viewButton);
        buttonPanel.add(viewButton1);
        buttonPanel.add(viewButton2);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}

