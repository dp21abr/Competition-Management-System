package Model;

public class Staff {
    private int staffID;
    private Name name;
    private StaffLevel level;

    public Staff(int staffID, Name name, StaffLevel level) {
        this.staffID = staffID;
        this.name = name;
        this.level = level;
    }

    // Getters and Setters
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public StaffLevel getStaffLevel() {
        return level;
    }

    public void setStaffLevel(StaffLevel level) {
        this.level = level;
    }

    public String toString() {
        return "Staff{" +
                "StaffID = " + getStaffID() +
                ", Name = " + getName() +
                ", StaffLevel = " + getStaffLevel() +
                '}';
    }
}
