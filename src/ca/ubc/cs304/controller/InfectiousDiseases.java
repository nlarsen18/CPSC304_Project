package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler76;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.UITransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.UITransactions;
import ca.ubc.cs304.ui.UITransactionsOld;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

/**
 * This is the main controller class that will orchestrate everything.
 */

public class InfectiousDiseases implements UITransactionsDelegate, LoginWindowDelegate {
    private DatabaseConnectionHandler76 dbHandler = null;
    private LoginWindow loginWindow = null;

    private void start(){
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            UITransactions transaction = new UITransactions(this);
            transaction.setVisible(true);
            //transaction.showFrame(this);

        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public InfectiousDiseases() { dbHandler = new DatabaseConnectionHandler76(); }

    /**
     * UIDelegate Implementation
     *
     * Delete agency with given agency name
     */
    public void deleteAgency(String name) throws SQLException {
        try {
            dbHandler.deleteAgency(name);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Delete treats with a given address and disease scientific name
     */
    public void deleteTreats(String address, String scientific_Name) throws SQLException {
        try {
            dbHandler.deleteTreats(address, scientific_Name);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Insert a agency with the given info
     */
    public void insertAgency(AgencyModel model) throws SQLException{
        try{
            dbHandler.insertAgency(model);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Insert a disease with the given info
     */
    public void insertDisease(DiseaseModel model) throws SQLException{
        try {
            dbHandler.insertDisease(model);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Insert a treats relationship with the given info
     */
    public void insertTreats(TreatsModel model) throws SQLException{
        try {
            dbHandler.insertTreats(model);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Update the agency num_of_employees for a specific name
     */
    public void updateAgency(String name, int num_of_employees) throws SQLException {
        try {
            dbHandler.updateAgency(name, num_of_employees);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * UIDelegate Implementation
     *
     * Select all disease names with an R0 >= r0
     */
    public ArrayList<String> selectDiseaseR0(double r0) { return dbHandler.selectDiseaseR0(r0); }

    /**
     * UIDelegate Implementation
     *
     * Project Agency Names
     */
    public ArrayList<String> projectAgency(String col) { return dbHandler.projectAgencyName(col); }

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
    public int countAgencies() { return  dbHandler.countAgencies(); }

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

    /**
     * UIDelegate Implementation
     *
     * Get all the info for the treats table
     */
    public ArrayList<TreatsModel> getTreatsInfo() { return dbHandler.getTreatsInfo(); }

    /**
     * UIDelegate Implementation
     *
     * Get all the info for the agency table
     */
    public ArrayList<AgencyModel> getAgencyInfo() { return dbHandler.getAgencyInfo(); }

    /**
     * UIDelegate Implementation
     *
     * Get all the info for the disease table
     */
    public ArrayList<DiseaseModel> getDiseaseInfo() { return dbHandler.getDiseaseInfo(); }

    public void UIFinished(){
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }

    public static void main(String args[]) {
        InfectiousDiseases infectiousdiseases = new InfectiousDiseases();
        infectiousdiseases.start();
    }
}
