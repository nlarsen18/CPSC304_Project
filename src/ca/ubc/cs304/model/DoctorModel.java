package ca.ubc.cs304.model;

public class DoctorModel {

    private final int ID;
    private final String Name;

    public DoctorModel(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    public int getID() { return ID; }

    public String getName() { return Name; }
}
