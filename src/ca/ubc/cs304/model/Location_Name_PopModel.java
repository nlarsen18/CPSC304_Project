package ca.ubc.cs304.model;

public class Location_Name_PopModel {
    private final String Name;
    private final float LAT;
    private final float LON;
    private final int Population;

    public Location_Name_PopModel(String Name, float LAT, float LON, int Population){
        this.Name = Name;
        this.LAT = LAT;
        this.LON = LON;
        this.Population = Population;
    }

    public String getName() { return Name; }

    public float getLAT() { return LAT; }

    public float getLON() { return LON; }

    public int getPopulation() { return Population; }
}
