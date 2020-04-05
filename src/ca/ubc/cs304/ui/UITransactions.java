package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.InfectiousDiseases;
import ca.ubc.cs304.delegates.UITransactionsDelegate;
import ca.ubc.cs304.model.AgencyModel;
import ca.ubc.cs304.model.DiseaseModel;
import ca.ubc.cs304.model.TreatsModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class UITransactions extends JFrame {

    private UITransactionsDelegate delegate = null;
    private static final int TEXT_FIELD_WIDTH = 10;
    private InfectiousDiseases infectiousDiseases;

    private JTabbedPane tabbedPane;
    private JPanel insert;
    private JPanel selection;
    private JButton insertBtn;
    private JTextField tupleText;
    private JLabel myLabel;
    private JButton viewBtn;
    private JComboBox selectInsert;
    private JLabel selectATableFromLabel;
    private JPanel update;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JPanel delete;
    private JSlider r0Slider;
    private JLabel r0Label;
    private JList diseaseList;
    private JPanel project;
    private JComboBox projectComboBox;
    private JButton projectBtn;
    private JLabel projectLbl;
    private JList agencyList;
    private JLabel insertedLbl;

    public UITransactions(InfectiousDiseases infectiousDiseases) {
        super("Pandemic Dashboard");
        this.infectiousDiseases = infectiousDiseases;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(tabbedPane);
        this.pack();
        r0Slider.setMaximum(1500);

        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = tupleText.getText();
                String selection = String.valueOf(selectInsert.getSelectedItem());

                if (selection.equals("AGENCY")){

                    String[] splitInput = input.split(",", 2);
                    try {
                        AgencyModel agencyModel = new AgencyModel(splitInput[0], Integer.parseInt(splitInput[1]));
                        try {
                            infectiousDiseases.insertAgency(agencyModel);
                            insertedLbl.setText("Inserted " + input + " into AGENCY");
                        } catch (SQLException e) {
                            insertedLbl.setText(e.getMessage());
                        }
                    } catch (ArrayIndexOutOfBoundsException e){
                        insertedLbl.setText(e.getMessage());
                    }
                } else if (selection.equals("DISEASE")){
                    String[] splitInput = input.split(",", 3);
                    try {
                        DiseaseModel diseaseModel = new DiseaseModel(splitInput[0], splitInput[1], Float.valueOf(splitInput[2]));
                        try {
                            infectiousDiseases.insertDisease(diseaseModel);
                            insertedLbl.setText("Inserted " + input + " into DISEASE");
                        } catch (SQLException e) {
                            insertedLbl.setText(e.getMessage());
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        insertedLbl.setText(e.getMessage());
                    }
                } else {
                    String[] splitInput = input.split(",", 3);
                    try{
                        TreatsModel treatsModel = new TreatsModel(splitInput[0], splitInput[1]);
                        try {

                            infectiousDiseases.insertTreats(treatsModel);
                            insertedLbl.setText("Inserted " + input + " into TREATS");
                        } catch (SQLException e) {
                            insertedLbl.setText(e.getMessage());
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        insertedLbl.setText(e.getMessage());
                    }
                }
            }
        });
        viewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //get the r0Val from the slider
                double r0Val = r0Slider.getValue()/100.0;
                System.out.println(r0Val);
                try {
                    //get a list of names of diseases whose R0 values are at least r0Val
                    ArrayList<String> diseases = infectiousDiseases.selectDiseaseR0(r0Val);
                    //Make a DefaultListModel from the arraylist values so we can pass to our JList
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for(String val : diseases)
                        model.addElement(val);
                    diseaseList.setModel(model);
                } catch (NullPointerException e) {
                    System.out.println("[EXCEPTION] SQL/Connection Error");
                }

            }
        });
        //Change listener attached to the r0Slider so the R0 updates as we move the slider
        r0Slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                r0Label.setText("R0: "+ String.valueOf(r0Slider.getValue()/100.0));
            }
        });
        //Action listener to project a column from the Agency table
        projectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ArrayList<String> returnVals = infectiousDiseases.projectAgency(String.valueOf(projectComboBox.getSelectedItem()));
                    //Make a DefaultListModel from the arraylist values so we can pass to our JList
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for(String val : returnVals)
                        model.addElement(val);
                    agencyList.setModel(model);
                    //throw new NullPointerException();
                } catch (NullPointerException e) {
                    System.out.println("[EXCEPTION] SQL/Connection Error");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new UITransactions(new InfectiousDiseases());
        frame.setVisible(true);
    }

}
