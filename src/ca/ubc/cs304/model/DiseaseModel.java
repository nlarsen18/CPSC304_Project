package ca.ubc.cs304.model;

public class DiseaseModel {
    private final String Scientific_Name;
    private final String Type;
    private final float R0;

    public DiseaseModel(String Scientific_Name, String Type, float R0){
        this.Scientific_Name = Scientific_Name;
        this.Type = Type;
        this.R0 = R0;
    }

    public String getScientific_Name() { return Scientific_Name; }

    public String getType() { return Type; }

    public float getR0() { return R0; }
}
