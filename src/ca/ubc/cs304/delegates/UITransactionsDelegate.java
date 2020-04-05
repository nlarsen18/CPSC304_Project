package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.AgencyModel;
import ca.ubc.cs304.model.DiseaseModel;
import ca.ubc.cs304.model.NestedAgrResultModel;
import ca.ubc.cs304.model.TreatsModel;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

/**
 * This interface uses the delegation design pattern where instead of having
 * the UI class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class (in this case InfectiousDiseases).
 *
 * UI calls the methods that we have listed below but
 * InfectiousDiseases is the actual class that will implement the methods.
 */


public interface UITransactionsDelegate {
    /**
     * Operations we need;
     * Insert
     * Delete
     * Update
     * Selection
     * Projection
     * Join Query
     * Aggregation Query
     * Nested aggregation with group-by
     * Division Query
     */

    public void deleteAgency(String name) throws SQLException;
    public void insertAgency(AgencyModel model) throws SQLException;
    public void insertDisease(DiseaseModel model) throws SQLException;
    public void insertTreats(TreatsModel model) throws SQLException;
    public void updateAgency(String name, int num_of_employees);
    public ArrayList<String> selectDiseaseR0(double r0);
    public ArrayList<String> projectAgency(String col);
    public ArrayList<String> findHospitalsThatTreat(String disease_name);
    public ArrayList<Integer> countAgencies();
    public ArrayList<NestedAgrResultModel> avgR0PerType();
    public ArrayList<String> hospitalsTreatAllDisease();

}
