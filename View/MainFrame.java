package View;

import Controller.CompetitorController;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame{
    private CardLayout cardLayout;

    public MainFrame(){
        super("Competition Management System ");
        cardLayout = new CardLayout();

        CompetitorView view = new CompetitorView();
        setLayout(cardLayout);
        new CompetitorController(view);

        add(view, "Competitor View");

        view.viewTable(e -> cardLayout.show(MainFrame.this.getContentPane(), "Competitors Table"));
        view.registerCompetitor(e -> cardLayout.show(MainFrame.this.getContentPane(), "Competitor Registration"));
        view.viewAttributes(e -> cardLayout.show(MainFrame.this.getContentPane(), "Competitor Attributes"));
        view.viewFullDetails(e -> cardLayout.show(MainFrame.this.getContentPane(), "Competitor Full Details"));
        view.viewShortDetails(e -> cardLayout.show(MainFrame.this.getContentPane(), "Competitor Short Details"));
        view.editDetails(e -> cardLayout.show(MainFrame.this.getContentPane(), "Edit Competitor Details"));
        view.removeCompetitor(e -> cardLayout.show(MainFrame.this.getContentPane(), "Remove Competitor Details"));
        view.close(e -> cardLayout.show(MainFrame.this.getContentPane(), "Close Competitor"));

        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}