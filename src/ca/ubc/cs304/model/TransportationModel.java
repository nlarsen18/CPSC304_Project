package ca.ubc.cs304.model;

public class TransportationModel {
    private final String Transportation_ID;
    private final String Type;
    private final int Passenger_Count;

    public TransportationModel(String Transportation_ID, String Type, int Passenger_Count){
        this.Transportation_ID = Transportation_ID;
        this.Type = Type;
        this.Passenger_Count = Passenger_Count;
    }

    public String getTransportation_ID() { return Transportation_ID; }

    public String getType() { return Type; }

    public int getPassenger_Count() { return Passenger_Count; }
}
