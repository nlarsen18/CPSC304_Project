package ca.ubc.cs304.database;

import java.sql.*;
import java.util.ArrayList;

import ca.ubc.cs304.model.AgencyModel;
import ca.ubc.cs304.model.DiseaseModel;
import ca.ubc.cs304.model.NestedAgrResultModel;
import ca.ubc.cs304.model.TreatsModel;

import javax.swing.plaf.nimbus.State;

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

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
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

    public void deleteAgency(String name) throws SQLException {
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
            throw e;
        }
    }

    public void deleteTreats(String address, String scientific_Name) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM treats WHERE hospital_address = ? AND disease_Scientific_Name = ?");
            ps.setString(1, address);
            ps.setString(2, scientific_Name);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " hospital at " + address + " doesn't treat " + scientific_Name);
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }

    public void insertAgency(AgencyModel model) throws SQLException{
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO agency (AGENCY_NAME, AGENCY_NUM_OF_EMPLOYEES) VALUES (?,?)");
            ps.setString(1, model.getName());
            ps.setInt(2, model.getNum_of_employees());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }

    public void updateAgency(String name, int num_of_employees) throws SQLException{
        try {
            PreparedStatement ps = connection.prepareStatement( "UPDATE agency SET agency_num_of_employees = ? WHERE agency_name = ?");
            ps.setString(2, name);
            ps.setInt(1, num_of_employees);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Agency " + name + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }

    /**
     * We had to add these insert functions so that we could
     * dynamically enter info to prove our other queries are dynamic
     */
    public void insertDisease(DiseaseModel model) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO disease VALUES (?,?,?)");
            ps.setString(1, model.getScientific_Name());
            ps.setString(2, model.getType());
            ps.setFloat(3, model.getR0());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }

    public void insertTreats(TreatsModel model) throws SQLException{
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO treats VALUES (?,?)");
            ps.setString(1, model.getHospital_Address());
            ps.setString(2, model.getDisease_Scientific_Name());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw e;
        }
    }



    /**
     * This function performs the required Selection Query.
     * It allows you to select all disease R0's with an R0 >= input
     */
    public ArrayList<String> selectDiseaseR0(double r0){
        ArrayList<String> result = new ArrayList<String>();

        try{

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT disease_Scientific_Name FROM disease WHERE disease_R0 >= " + r0);

            while(rs.next()){
                result.add(rs.getString("disease_Scientific_Name"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    /**
     * This function performs the required projection Query.
     * It projects the names of the agencies.
     */
    public ArrayList<String> projectAgencyName(String col){
        ArrayList<String> result = new ArrayList<String>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + col + " FROM agency");

            while(rs.next()){
                System.out.println(rs.getString(col));
                result.add(rs.getString(col));
            }
            System.out.println(result);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    /**
     * This function performs the join query.
     * Find the names of all hospitals that treat a specified disease (through disease_Scientific_Name)
     * @param disease_name
     * @return
     */
    public ArrayList<String> findHospitalsThatTreat(String disease_name){
        ArrayList<String> result = new ArrayList<String>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT hospital_Name FROM hospital, treats WHERE disease_Scientific_Name = ? ");

            while(rs.next()){
                result.add(rs.getString("hospital_Name"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }


    /**
     * This function performs the aggregation query
     *
     * Count the number of Agencies
     */
    public ArrayList<Integer> countAgencies(){
        ArrayList<Integer> result = new ArrayList<Integer>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(agency_Name) FROM agency");

            while(rs.next()){
                result.add(rs.getInt("COUNT_agency_Name"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    /**
     * This function performs the nested aggregation with group-by
     *
     * Find the average R0 value per disease type
     */
    public ArrayList<NestedAgrResultModel> avgR0PerType(){
        ArrayList<NestedAgrResultModel> result = new ArrayList<NestedAgrResultModel>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT disease_Type, AVG(disease_R0) FROM Disease GROUP BY disease_Type");

            while(rs.next()){
                NestedAgrResultModel model = new NestedAgrResultModel(rs.getFloat("AVG_disease_R0"),
                                                                      rs.getString("disease_Type"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    /**
     * This function performs the Division Query
     *
     *Find the names of hospitals who treat all diseases
     */
    public ArrayList<String> hospitalsTreatAllDisease(){
        ArrayList<String> result = new ArrayList<String>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT hospital_Name FROM hospital H" +
                    "WHERE NOT EXISTS ((SELECT D.disease_Scientific_Name FROM disease D) EXCEPT (SELECT T.disease_Scientific_Name FROM treats T WHERE  T.hospital_Address = H.hospital_Address))");

            while(rs.next()){
                result.add(rs.getString("hospital_Name"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return result;
    }

    /**
     * This function allows us to view the treats table
     *
     * Conduct a SELECT * query on the treats table so that we can prove some of
     * the above queries are dynamic
     */
    public ArrayList<TreatsModel> getTreatsInfo(){
        ArrayList<TreatsModel> result = new ArrayList<TreatsModel>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM treats");

            while(rs.next()){
                TreatsModel model = new TreatsModel(rs.getString("hospital_Address"),
                                                    rs.getString("disease_Scientific_Name"));

                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result;
    }


    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
