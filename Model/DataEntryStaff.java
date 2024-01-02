package Model;

public class DataEntryStaff extends Staff {
    public DataEntryStaff(int staffID, Name name, StaffLevel level) {
        super(staffID, name, level);
    }
    public String getStaffType() { return "Data Entry";}
    @Override
    public String toString() {
        return "Staff{" +
                "StaffID = " + getStaffID() +
                ", Name = " + getName() +
                ", Staff Level = " + getStaffLevel() +
                ", Staff Type = " + getStaffType() +
                '}';
    }

}
