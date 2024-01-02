package View;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private CompetitorList competitorList;

    public RegisterView(CompetitorList competitorList) {
        super("Competitor Registration");
        this.competitorList = competitorList;

        setSize(300, 200);
        setLayout(new GridLayout(6, 2));

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
                registerCompetitor(new BoxingCompetitor(getName(nameField), getCountry(countryField),
                        getLevel(levelComboBox), getAge(ageField), emailField.getText(), new int[]{0, 0, 0}));
            }
        });

        registerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCompetitor(new CyclingCompetitor(getName(nameField), getCountry(countryField),
                        getLevel(levelComboBox), getAge(ageField), emailField.getText(), new int[]{0, 0, 0}));
            }
        });

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(ageLabel);
        add(ageField);
        add(countryLabel);
        add(countryField);
        add(levelLabel);
        add(levelComboBox);
        add(registerButton);
        add(registerButton1);

        setVisible(true);
    }

    private Name getName(JTextField field) {
        return new Name(field.getText());
    }

    private String getCountry(JTextField field) {
        return field.getText();
    }

    private CompetitorLevel getLevel(JComboBox<String> comboBox) {
        return CompetitorLevel.valueOf((String) comboBox.getSelectedItem());
    }

    private int getAge(JTextField field) {
        return Integer.parseInt(field.getText());
    }

    private void registerCompetitor(Competitor competitor) {
        competitorList.registerCompetitor(competitor);
        competitorList.saveCompetitors("competitordata.txt");
        dispose();
    }
}
