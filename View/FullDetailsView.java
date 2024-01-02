package View;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class FullDetailsView extends JFrame {
    private CompetitorList competitorList;

    public FullDetailsView(CompetitorList competitorList) {
        super("View Full Details");
        this.competitorList = competitorList;

        setSize(300, 200);
        setLayout(new GridLayout(4, 2));

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

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}

