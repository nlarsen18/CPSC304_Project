package ca.ubc.cs304.model;

public class NestedAgrResultModel {
    private final float avgR0;
    private final String type;

    public NestedAgrResultModel(float avgR0, String type){
        this.avgR0 = avgR0;
        this.type = type;
    }

    public float getAvgR0() { return avgR0; }

    public String getType() { return type; }
}
