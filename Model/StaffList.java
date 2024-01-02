package Model;

import java.io.*;
import java.util.ArrayList;

public class StaffList {

    private ArrayList<Staff> staffList;

    private ArrayList<Competitor> competitors;

    private void loadStaff() {
        Name staffName1 = new Name("Lynn Roy Son");
        Name staffName2 = new Name("Nils Kim Jong");
        Name staffName3 = new Name("Donald Trump");
        Name staffName4 = new Name("Putin Maksim");
        Name staffName5 = new Name("Minul Don Sub");
        Name staffName6 = new Name("Don Jackson");
        Name staffName7 = new Name("John Luke");
        Name staffName8 = new Name("Devansh Mehtha");
        Name staffName9 = new Name("Sue Sevnchi");
        Name staffName10 = new Name("Himan Oku");

        Staff s1 = new OfficialsStaff(110, staffName1, StaffLevel.SENIOR);
        Staff s2 = new OfficialsStaff(111, staffName2, StaffLevel.SENIOR);
        Staff s3 = new OfficialsStaff(112, staffName3, StaffLevel.SENIOR);
        Staff s4 = new DataEntryStaff(113, staffName4, StaffLevel.INTERMEDIATE);
        Staff s5 = new DataEntryStaff(114, staffName5, StaffLevel.INTERMEDIATE);
        Staff s6 = new DataEntryStaff(115, staffName6, StaffLevel.ASSOCIATE);
        Staff s7 = new DataEntryStaff(116, staffName7, StaffLevel.ASSOCIATE);
        Staff s8 = new Staff(117, staffName8, StaffLevel.ASSOCIATE);
        Staff s9 = new Staff(118, staffName9, StaffLevel.INTERMEDIATE);
        Staff s10 = new Staff(119, staffName10, StaffLevel.SENIOR);

        staffList.add(s1);
        staffList.add(s2);
        staffList.add(s3);
        staffList.add(s4);
        staffList.add(s5);
        staffList.add(s6);
        staffList.add(s7);
        staffList.add(s8);
        staffList.add(s9);
        staffList.add(s10);

    }
    public StaffList() {
        staffList = new ArrayList<>();
        this.competitors = new ArrayList<>();
        loadStaff();
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

    private Competitor getCompetitorByNumber(int competitorNumber) {
        for (Competitor competitor : getCompetitors()) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;


    }
}
