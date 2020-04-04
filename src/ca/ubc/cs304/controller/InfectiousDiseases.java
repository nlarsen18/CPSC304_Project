package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler76;
import ca.ubc.cs304.delegates.UITransactionsDelegate;
import ca.ubc.cs304.model.AgencyModel;

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
     * Update the agency num_of_employees for a specific name
     */
    public void updateAgency(String name, int num_of_employees) { dbHandler.updateAgency(name, num_of_employees); }

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
