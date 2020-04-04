package ca.ubc.cs304.model;

public class CountryModel {
    private final String Name;
    private final float LAT;
    private final float LON;
    private final String Leader;

    public CountryModel(String Name, float LAT, float LON, String Leader){
        this.Name = Name;
        this.LAT = LAT;
        this.LON = LON;
        this.Leader = Leader;
    }

    public String getName() { return Name; }

    public float getLAT() { return LAT; }

    public float getLON() { return LON; }

    public String getLeader() { return Leader; }
}
