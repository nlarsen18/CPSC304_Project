package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.InfectiousDiseases;
import ca.ubc.cs304.delegates.UITransactionsDelegate;
import ca.ubc.cs304.model.AgencyModel;
import ca.ubc.cs304.model.DiseaseModel;
import ca.ubc.cs304.model.NestedAgrResultModel;
import ca.ubc.cs304.model.TreatsModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class UITransactions extends JFrame {

    private UITransactionsDelegate delegate = null;
    private static final int TEXT_FIELD_WIDTH = 10;
    private InfectiousDiseases infectiousDiseases;

    private JTabbedPane view;
    private JPanel insert;
    private JPanel selection;
    private JButton insertBtn;
    private JTextField tupleText;
    private JLabel myLabel;
    private JButton viewBtn;
    private JComboBox selectInsert;
    private JLabel selectATableFromLabel;
    private JPanel update;
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
    private JTextField deleteIn;
    private JButton deleteBtn;
    private JLabel deleteLbl;
    private JTextField updateAName;
    private JTextField updateANOE;
    private JButton updateBtn;
    private JLabel updateEx;
    private JLabel findHospitalsLbl;
    private JList hospitalList;
    private JButton findHospitalsBtn;
    private JComboBox findHospitalComboBox;
    private JComboBox deleteComboBox;
    private JComboBox viewComboBox;
    private JTable viewTable;
    private JPanel viewTab;
    private JPanel findHospTab;

    public UITransactions(InfectiousDiseases infectiousDiseases) {
        super("Pandemic Dashboard");
        this.infectiousDiseases = infectiousDiseases;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(view);
        this.pack();
        r0Slider.setMaximum(1500);

        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input = tupleText.getText();
                String selection = String.valueOf(selectInsert.getSelectedItem());

                if (selection.equals("AGENCY")){

                    String[] splitInput = input.split(",|,_|/|/_", 2);
                    try {
                        AgencyModel agencyModel = new AgencyModel(splitInput[0], Integer.valueOf(splitInput[1]));
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
                    String[] splitInput = input.split(",|,_|/|/_", 3);
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
                    String[] splitInput = input.split("/|/_", 2);
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
                    int numAgencies = infectiousDiseases.countAgencies();
                    //Make a DefaultListModel from the arraylist values so we can pass to our JList
                    DefaultListModel<String> model = new DefaultListModel<>();
                    for(String val : returnVals)
                        model.addElement(val);
                    model.addElement("_____________________________");
                    model.addElement("Number of agencies: " + numAgencies);

                    agencyList.setModel(model);
                } catch (NullPointerException e) {
                    System.out.println("[EXCEPTION] SQL/Connection Error");
                }
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selection = deleteComboBox.getSelectedIndex();
                System.out.println(selection);

                if (selection == 0) {
                    String[] cleanIn = deleteIn.getText().split("[^A-Za-z0-9 ].");
                    if (!String.valueOf(cleanIn[0].charAt(cleanIn[0].length()-1)).matches("[A-Za-z0-9_]"))
                        cleanIn[0] = cleanIn[0].substring(0, cleanIn[0].length()-1);
                    String agencyName = cleanIn[0];
                    System.out.println(agencyName);

                    try {
                        infectiousDiseases.deleteAgency(agencyName);
                    } catch (SQLException e){
                        deleteLbl.setText(e.getMessage());
                        System.out.println(e.getMessage());
                    }
                } else {
                    String[] treatsKey = deleteIn.getText().split("/");
                    try {
                        infectiousDiseases.deleteTreats(treatsKey[0], treatsKey[1]);
                    } catch (SQLException e){
                        deleteLbl.setText(e.getMessage());
                        System.out.println(e.getMessage());
                    }
                }



            }
        });
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] agencyName = updateAName.getText().split("[^A-Za-z0-9 ].");
                if (!String.valueOf(agencyName[0].charAt(agencyName[0].length()-1)).matches("[A-Za-z0-9_]"))
                    agencyName[0] = agencyName[0].substring(0, agencyName[0].length()-1);
                String cleanAgencyName = agencyName[0];
                int agencyNOE = Integer.valueOf(updateANOE.getText());
                try {
                    infectiousDiseases.updateAgency(cleanAgencyName, agencyNOE);
                } catch (SQLException e) {
                    updateEx.setText(e.getMessage());
                }
            }
        });
        findHospitalsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String disease = String.valueOf(findHospitalComboBox.getSelectedItem());
                ArrayList<String> returnVals = infectiousDiseases.findHospitalsThatTreat(disease);
                //Make a DefaultListModel from the arraylist values so we can pass to our JList
                DefaultListModel<String> model = new DefaultListModel<>();
                for(String val : returnVals)
                    model.addElement(val);

                ArrayList<String> hospitalsThatTreatAll = infectiousDiseases.hospitalsTreatAllDisease();
                model.addElement("_____________________________");
                model.addElement("Hospitals that treat all disease: ");
                for(String val : hospitalsThatTreatAll)
                    model.addElement(val);
                hospitalList.setModel(model);
            }
        });
        viewComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent itemEvent) {
                int selection = viewComboBox.getSelectedIndex();
                if (selection == 1) {
                    ArrayList<AgencyModel> agencyInfo = infectiousDiseases.getAgencyInfo();
                    String[] col = {"Agency_Name", "Agency_Num_Of_Employees"};
                    DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                    viewTable.setModel(tableModel);
                    for (AgencyModel am : agencyInfo) {
                        Object[] obj = {am.getName(), am.getNum_of_employees()};
                        tableModel.addRow(obj);
                    }
                } else if (selection == 2){
                    ArrayList<DiseaseModel> diseaseInfo = infectiousDiseases.getDiseaseInfo();
                    String[] col = {"Disease_Scientific_Name", "Disease_Type", "Disease_R0"};
                    DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                    viewTable.setModel(tableModel);
                    for (DiseaseModel dm : diseaseInfo) {
                        Object[] obj = {dm.getScientific_Name(), dm.getType(), dm.getR0()};
                        tableModel.addRow(obj);
                    }
                } else if (selection == 3){
                    ArrayList<TreatsModel> treatsInfo = infectiousDiseases.getTreatsInfo();
                    String[] col = {"Hospital_Address", "Disease_Scientific_Name"};
                    DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                    viewTable.setModel(tableModel);
                    for (TreatsModel tm : treatsInfo) {
                        Object[] obj = {tm.getHospital_Address(), tm.getDisease_Scientific_Name()};
                        tableModel.addRow(obj);
                    }
                } else if (selection == 4) {
                    ArrayList<NestedAgrResultModel> nestedAgrResultModel = infectiousDiseases.avgR0PerType();
                    String[] col = {"Disease Type", "Average R0"};
                    DefaultTableModel tableModel = new DefaultTableModel(col, 0);
                    viewTable.setModel(tableModel);
                    for (NestedAgrResultModel narm : nestedAgrResultModel) {
                        Object[] obj = {narm.getType(), narm.getAvgR0()};
                        tableModel.addRow(obj);
                    }
                } else {
                    viewTable.setModel(new DefaultTableModel());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new UITransactions(new InfectiousDiseases());
        frame.setVisible(true);
    }

}
