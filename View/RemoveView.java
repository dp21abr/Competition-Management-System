package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.*;

public class RemoveView extends JFrame {
    private CompetitorList competitorList;
    private StaffList staffList = new StaffList();
    public RemoveView(CompetitorList competitorList) {
        super("Remove Competitor");
        this.competitorList = competitorList;

        setSize(400, 300);

        JPanel editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);

        // Components
        JLabel staffIDLabel = new JLabel("StaffID:");
        JTextField staffIDField = new JTextField(25);

        JLabel competitorNumberLabel = new JLabel("Competitor Number:");
        JTextField competitorNumberField = new JTextField(25);

        JButton removeCompetitorButton = new JButton("Remove Competitor");
        removeCompetitorButton.setPreferredSize(new Dimension(278, 40));

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        editPanel.add(competitorNumberLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(competitorNumberField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy++;
        editPanel.add(staffIDLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        editPanel.add(staffIDField, gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        editPanel.add(removeCompetitorButton, gridBagConstraints);

        removeCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the competitor number to remove
                int competitorNumberInput = Integer.parseInt(competitorNumberField.getText());
                int StaffID = Integer.parseInt(staffIDField.getText());
                // Validate the input
                Staff staff = staffList.getStaffByID(StaffID);
                if (staff.getStaffLevel() == StaffLevel.SENIOR) {
                    if (competitorNumberInput != 0) {
                        try {
                            int competitorNumber = competitorNumberInput;
                            competitorList.removeCompetitor(competitorNumber);
                            JOptionPane.showMessageDialog(RemoveView.this, "Competitor removed successfully.");
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(RemoveView.this, "Invalid Competitor Number", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(RemoveView.this, "Invalid Competitor Number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else{
                    JOptionPane.showMessageDialog(RemoveView.this, "The Staff with the Staff ID " + StaffID + " does not have the right to remove competitor");
                }
            }
        });
        add(editPanel);
        setVisible(true);
    }
}

