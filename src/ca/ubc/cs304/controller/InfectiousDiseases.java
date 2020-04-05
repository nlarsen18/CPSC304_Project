package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler76;
import ca.ubc.cs304.delegates.UITransactionsDelegate;
import ca.ubc.cs304.model.AgencyModel;
import ca.ubc.cs304.model.DiseaseModel;
import ca.ubc.cs304.model.NestedAgrResultModel;
import ca.ubc.cs304.model.TreatsModel;

import java.util.ArrayList;

/**
 * This is the main controller class that will orchestrate everything.
 */

public class InfectiousDiseases implements UITransactionsDelegate {
    private DatabaseConnectionHandler76 dbHandler = null;

    public InfectiousDiseases() { dbHandler = new DatabaseConnectionHandler76(); }

    /**
     * UIDelegate Implementation
     *
     * Delete agency with given agency name
     */
    public void deleteAgency(String name) { dbHandler.deleteAgency(name); }

    /**
     * UIDelegate Implementation
     *
     * Insert a agency with the given info
     */
    public void insertAgency(AgencyModel model) { dbHandler.insertAgency(model); }

    /**
     * UIDelegate Implementation
     *
     * Insert a disease with the given info
     */
    public void insertDisease(DiseaseModel model) { dbHandler.insertDisease(model); }

    /**
     * UIDelegate Implementation
     *
     * Insert a treats relationship with the given info
     */
    public void insertTreats(TreatsModel model) { dbHandler.insertTreats(model); }

    /**
     * UIDelegate Implementation
     *
     * Update the agency num_of_employees for a specific name
     */
    public void updateAgency(String name, int num_of_employees) { dbHandler.updateAgency(name, num_of_employees); }

    /**
     * UIDelegate Implementation
     *
     * Select all disease names with an R0 >= r0
     */
    public ArrayList<String> selectDiseaseR0(float r0) { return dbHandler.selectDiseaseR0(r0); }

    /**
     * UIDelegate Implementation
     *
     * Project Agency Names
     */
    public ArrayList<String> projectAgencyName() { return dbHandler.projectAgencyName(); }

    /**
     * UIDelegate Implementation
     *
     * Find the names of all hospitals that treat a specified disease (through disease_Scientific_Name)
     */
    public ArrayList<String> findHospitalsThatTreat(String disease_name) { return dbHandler.findHospitalsThatTreat(disease_name); }

    /**
     * UIDelegate Implementation
     *
     * Count the number of Agencies
     */
    public ArrayList<Integer> countAgencies() { return  dbHandler.countAgencies(); }

    /**
     * UIDelegate Implementation
     *
     * Find the Average R0 value per disease type
     */
    public ArrayList<NestedAgrResultModel> avgR0PerType() { return dbHandler.avgR0PerType(); }

    /**
     * UIDelegate Implementation
     *
     * Find the names of hospitals who treat all diseases
     */
    public ArrayList<String> hospitalsTreatAllDisease(){ return dbHandler.hospitalsTreatAllDisease(); }



    public void UIFinished(){
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    /**
     * DO WE NEED THIS??
     * public static void main(String args[]) {
     *     InfectiousDiseases infectiousdiseases = new InfectiousDiseases();
     * }
     */
}
