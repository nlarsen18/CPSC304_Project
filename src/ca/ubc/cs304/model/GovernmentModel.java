package ca.ubc.cs304.model;

public class GovernmentModel {
    private final int Gov_ID;
    private final int Public_Health_Budget;

    public GovernmentModel(int Gov_ID, int Public_Health_Budget){
        this.Gov_ID = Gov_ID;
        this.Public_Health_Budget = Public_Health_Budget;
    }

    public int getGov_ID() { return Gov_ID; }

    public int getPublic_Health_Budget() { return Public_Health_Budget; }
}
