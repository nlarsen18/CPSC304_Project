package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.AgencyModel;

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

    public void deleteAgency(String name);
    public void insertAgency(AgencyModel model);
    public void updateAgency(String name, int num_of_employees);

}
