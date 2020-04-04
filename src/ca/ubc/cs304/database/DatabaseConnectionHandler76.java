package ca.ubc.cs304.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ca.ubc.cs304.model.AgencyModel;

public class DatabaseConnectionHandler76 {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler76()  {
        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void deleteAgency(String name){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM agency WHERE agency_Name = ?");
            ps.setString(1, name);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Agency " + name + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertAgency(AgencyModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO agency VALUES (?,?)");
            ps.setString(1, model.getName());
            ps.setInt(2, model.getNum_of_employees());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateAgency(String name, int num_of_employees){
        try {
            PreparedStatement ps = connection.prepareStatement( "UPDATE agency SET agency_num_of_employees = ? WHERE agency_name = ?");
            ps.setString(1, name);
            ps.setInt(2, num_of_employees);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Agency " + name + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    /**
     * This function performs the required Selection Query.
     * It allows you to select all disease R0's with an R0 >= input
     */
    public void selectDiseaseR0(float input){
        try{
            PreparedStatement ps = connection.prepareStatement( "SELECT disease_Scientific_Name FROM disease WHERE disease_R0 >= ?");
            ps.setFloat(1, input);


        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    /**
     * This function performs the required projection Query.
     * It projects the names of the agencies.
     */
    public void projectAgencyName(){

    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
