package ca.ubc.cs304.model;

/**
 * The intent for this class is to update/store information about a single Agency
 */

public class AgencyModel {
    private final String Name;
    private final int num_of_employees;

    public AgencyModel(String Name, int num_of_employees){
        this.Name = Name;
        this.num_of_employees = num_of_employees;
    }

    public String getName() { return Name; }

    public int getNum_of_employees() { return num_of_employees; }

}
