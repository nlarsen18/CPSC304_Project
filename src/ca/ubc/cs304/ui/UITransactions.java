package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.UITransactionsDelegate;

/**
 * The class is responsible for handling ui text inputs
 */

public class UITransactions {

    private UITransactionsDelegate delegate = null;

    public UITransactions(){
    }

    public void handleDeleteOption(){
        //TODO
        String bob;
        delegate.deleteAgency(bob);
    }

    private void handleInsertOption(){
        //TODO

        //delegate.insertAgency();
    }

    private void handleUpdateOption(){
        //TODO

        //delegate.updateAgency();
    }

}
