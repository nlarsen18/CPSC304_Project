package ca.ubc.cs304.model;

public class TreatsModel {
    private final String hospital_Address;
    private final String disease_Scientific_Name;

    public TreatsModel(String hospital_Address, String disease_Scientific_Name){
        this.hospital_Address = hospital_Address;
        this.disease_Scientific_Name = disease_Scientific_Name;
    }

    public String getHospital_Address() { return  hospital_Address; }

    public String getDisease_Scientific_Name() { return disease_Scientific_Name; }
}
