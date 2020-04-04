package ca.ubc.cs304.model;

public class HospitalModel {
    private final String Address;
    private final int Capacity;
    private final String Name;
    private final String Type;

    public HospitalModel(String Address, int Capacity, String Name, String Type){
        this.Address = Address;
        this.Capacity = Capacity;
        this.Name = Name;
        this.Type = Type;
    }

    public String getAddress() { return Address; }

    public int getCapacity() { return Capacity; }

    public String getName() { return Name; }

    public String getType() { return Type; }
}
