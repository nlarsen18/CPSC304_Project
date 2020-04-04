package ca.ubc.cs304.model;

public class RegionModel {
    private final String Name;
    private final float LAT;
    private final float LON;
    private final String Climate;

    public RegionModel(String Name, float LAT, float LON, String Climate){
        this.Name = Name;
        this.LAT = LAT;
        this.LON = LON;
        this.Climate = Climate;
    }

    public String getName() { return Name; }

    public float getLAT() { return LAT; }

    public float getLON() { return LON; }

    public String getClimate() { return Climate; }
}
