package Model;

import java.io.*;
import java.util.ArrayList;

public class StaffList {

    private ArrayList<Staff> staffList;
    private CompetitorList competitorList;
    private ArrayList<Competitor> competitors;

    public StaffList() {
        staffList = new ArrayList<>();
        this.competitorList = new CompetitorList();
        this.competitors = new ArrayList<>();
    }

    // adds user to our collection
    public void addStaff(Staff staff) {
        staffList.add(staff);
    }

    public Staff getStaffByID(int staffID) {
        for (Staff staff : staffList) {
            if (staff.getStaffID() == staffID) {
                return staff;
            }
        }
        return null; // Staff not found
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    public void registerCompetitor(Staff staff, Competitor competitor) {
        if (staff.getStaffLevel() == StaffLevel.SENIOR) {
            getCompetitors().add(competitor);
            competitorList.registerCompetitor(competitor); // Delegate to CompetitorList
        }
    }

    public void removeCompetitor(Staff staff, int competitorNumber) {
        if (staff.getStaffLevel() == StaffLevel.SENIOR) {
            Competitor competitorToRemove = null;
            for (Competitor competitor : getCompetitors()) {
                if (competitor.getCompetitorNumber() == competitorNumber) {
                    competitorToRemove = competitor;
                    break;
                }
            }
            if (competitorToRemove != null) {
                getCompetitors().remove(competitorToRemove);
                competitorList.removeCompetitor(competitorNumber); // Delegate to CompetitorList
            }
        }
    }

    public void amendCompetitorDetails(Staff staff, int competitorNumber, Name newName, String newCountry,
                                       String newLevel, int newAge, String newEmail, int[] newScores, String newCategory) {
        if (staff.getStaffLevel() == StaffLevel.INTERMEDIATE) {
            competitorList.editCompetitorDetails(competitorNumber, newName, newCountry, newLevel, newAge, newEmail, newScores, newCategory);
        }
    }

    private Competitor getCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : getCompetitors()) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;


    }
}
