package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JPanel {
    private JButton registerButton;
    private JButton viewScoresButton;
    private JButton viewTableButton;
    private JButton viewByAttributesButton;
    private JButton viewFullDetailsButton;
    private JButton viewShortDetailsButton;
    private JButton editDetailsButton;
    private JButton removeCompetitorButton;
    private JButton closeButton;

    public UserView() {
        registerButton = new JButton("Register Competitor");
        registerButton.setPreferredSize(new Dimension(278, 40));
        viewScoresButton = new JButton("View/Edit Scores");
        viewScoresButton.setPreferredSize(new Dimension(278, 40));
        viewTableButton = new JButton("View Competitors Table");
        viewTableButton.setPreferredSize(new Dimension(278, 40));
        viewByAttributesButton = new JButton("View Competitors by Attributes");
        viewByAttributesButton.setPreferredSize(new Dimension(278, 40));
        viewFullDetailsButton = new JButton("View Full Details");
        viewFullDetailsButton.setPreferredSize(new Dimension(278, 40));
        viewShortDetailsButton = new JButton("View short Details");
        viewShortDetailsButton.setPreferredSize(new Dimension(278, 40));
        editDetailsButton = new JButton("Edit Details");
        editDetailsButton.setPreferredSize(new Dimension(278, 40));
        removeCompetitorButton = new JButton("Remove Competitor");
        removeCompetitorButton.setPreferredSize(new Dimension(278, 40));
        closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(278, 40));

        Insets buttonInset = new Insets(0, 0, 10, 0);

        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = buttonInset;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = buttonInset;

        add(registerButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = buttonInset;

        add(viewScoresButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = buttonInset;

        add(viewTableButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = buttonInset;

        add(viewByAttributesButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = buttonInset;

        add(viewFullDetailsButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = buttonInset;

        add(viewShortDetailsButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = buttonInset;

        add(editDetailsButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = buttonInset;

        add(removeCompetitorButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = buttonInset;

        add(closeButton, gridBagConstraints);
    }

    public void registerCompetitor(ActionListener actionListener) {
        registerButton.addActionListener(actionListener);
    }
    public void viewScores(ActionListener actionListener) {
        viewScoresButton.addActionListener(actionListener);
    }
    public void viewTable(ActionListener actionListener) {
        viewTableButton.addActionListener(actionListener);
    }
    public void viewAttributes(ActionListener actionListener) {
        viewByAttributesButton.addActionListener(actionListener);
    }
    public void viewFullDetails(ActionListener actionListener) {
        viewFullDetailsButton.addActionListener(actionListener);
    }
    public void viewShortDetails(ActionListener actionListener) {
        viewShortDetailsButton.addActionListener(actionListener);
    }
    public void editDetails(ActionListener actionListener) {
        editDetailsButton.addActionListener(actionListener);
    }
    public void removeCompetitor(ActionListener actionListener) {
        removeCompetitorButton.addActionListener(actionListener);
    }
    public void close(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

}

