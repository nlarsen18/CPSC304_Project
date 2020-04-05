package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.InfectiousDiseases;
import ca.ubc.cs304.delegates.UITransactionsDelegate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JTable table2;
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
                System.out.println(selection);
                System.out.println(input);
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
