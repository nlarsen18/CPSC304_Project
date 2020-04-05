package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.UITransactionsDelegate;

import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class is responsible for handling ui text inputs
 */

public class UITransactionsOld extends JFrame implements ActionListener {

    private UITransactionsDelegate delegate = null;
    private static final int TEXT_FIELD_WIDTH = 10;

    public UITransactionsOld() {
        super("Pandemic Dashboard");
    }

    private JTextField testField;

    public void showFrame(UITransactionsDelegate delegate) {
        this.delegate = delegate;

        JLabel testLabel = new JLabel("Testing12 ");

        testField = new JTextField(TEXT_FIELD_WIDTH);

        JButton testButton = new JButton("Test");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setPreferredSize(new Dimension(800, 1000));

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(testLabel, c);
        contentPane.add(testLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(testField, c);
        contentPane.add(testField);

        /*
        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(passwordLabel, c);
        contentPane.add(passwordLabel);

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(passwordField, c);
        contentPane.add(passwordField);
        */

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(testButton, c);
        contentPane.add(testButton);

        // register login button with action event handler
        testButton.addActionListener(this);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);

        // place the cursor in the text field for the username
        testField.requestFocus();
    }

    public void handleDeleteOption(){
        //TODO

        //delegate.deleteAgency();
    }

    private void handleInsertOption(){
        //TODO

        //delegate.insertAgency();
    }

    private void handleUpdateOption(){
        //TODO

        //delegate.updateAgency();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Action" + String.valueOf(testField.getText()) + "performed!");
    }
}
