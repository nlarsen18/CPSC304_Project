package ca.ubc.cs304.model;

public class Location_ContinentModel {
    private final float LAT;
    private final float LON;
    private final String Continent;

    public Location_ContinentModel(float LAT, float LON, String Continent){
        this.LAT = LAT;
        this.LON = LON;
        this.Continent = Continent;
    }

    public float getLAT() { return LAT; }

    public float getLON() { return LON; }

    public String getContinent() { return Continent; }
}
