package ca.ubc.cs304.model;

public class CityModel {
    private final String Name;
    private final float LAT;
    private final float LON;
    private final String Mayor;

    public CityModel(String Name, float LAT, float LON, String Mayor){
        this.Name = Name;
        this.LAT = LAT;
        this.LON = LON;
        this.Mayor = Mayor;
    }

    public String getName() { return Name; }

    public float getLAT() { return LAT; }

    public float getLON() { return LON; }

    public String getMayor() { return Mayor; }
}
