package Model;

public class OfficialsStaff extends Staff {
    public OfficialsStaff(int staffID, Name name, StaffLevel level) {
        super(staffID, name, level);
    }
    public String getStaffType() { return "Officials";}
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
