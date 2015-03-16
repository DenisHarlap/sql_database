package com.mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Denis on 20.02.15.
 */
public class MySQLDatabase {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public MySQLDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/task_new","root","doping");
        } catch (Exception e) {
            System.err.println("Error on connection: " + e);
            closeConnection();
        }
    }

    public void printSelectResult() {
        try {
            statement = connection.createStatement();
            /*
             *Print tests results sorting by the test name and the date.
             */
            System.out.println("Print tests results sorting by the test name and the date");
            String query = "SELECT TESTID,DATE,MARK FROM MARKS,TESTS WHERE " +
                    "TESTID = IDTESTS ORDER BY DATE,NAME;";
            resultSet = statement.executeQuery(query);
            printResult();
        } catch (Exception e) {
            System.err.println("Error on connection: "+ e);
        } finally {
            closeStatement();
        }
    }

    public void createCollectionOnSelect() {
        try {
            statement = connection.createStatement();
            /*
             * Create the collection of tests results for the current month sorting on the date
             *ascending and then print it.
             */
            System.out.println();
            System.out.println("Create the collection of tests results for the current month " +
                    "sorting on the date ascending and then print it");
            String query = "SELECT * FROM MARKS " +
                    "WHERE MONTH(DATE) = MONTH(CURRENT_DATE) " +
                    "ORDER BY DATE;";
            resultSet = statement.executeQuery(query);
            List<TestResult> listMark = new ArrayList<TestResult>();
            while (resultSet.next()){
                listMark.add(new TestResult(resultSet.getInt(1),resultSet.getDate(2).toString(),
                        resultSet.getInt(3)));
            }
            for  (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "  ");
            }
            System.out.println();
            for (TestResult obj : listMark){
                System.out.println(obj.getTestId() + " " + obj.getDate() + " " + obj.getMark() + ";");
            }
        } catch (Exception e) {
            System.err.println("Error on connection: "+ e);
        } finally {
           closeStatement();
        }
    }

    public void deleteSelectResult() {
        try {
            statement = connection.createStatement();
            /*
             * Delete tests results for previous years.
             */
            System.out.println();
            System.out.println("Delete tests results for previous years.");
            String query = "DELETE FROM MARKS " +
                    "WHERE YEAR(DATE) < YEAR(CURRENT_DATE)";
            statement.executeUpdate(query);
            query = "SELECT * FROM MARKS ORDER BY DATE;";
            resultSet = statement.executeQuery(query);
            printResult();
        } catch (Exception e) {
            System.err.println("Error on connection: "+ e);
        } finally {
            closeStatement();
        }
    }

    public void updateSelectResult() {
        try {
            statement = connection.createStatement();
            /*
             * Decrease the year of all tests.
             */
            System.out.println();
            System.out.println("Decrease the year of all tests.");
            String query = "UPDATE MARKS SET DATE = DATE - INTERVAL 1 YEAR;";
            statement.executeUpdate(query);
            query = "SELECT * FROM MARKS ORDER BY DATE;";
            resultSet = statement.executeQuery(query);
            printResult();
        } catch (Exception e) {
            System.err.println("Error on connection: "+ e);
        } finally {
            closeStatement();
        }
    }

    private void printResult() throws SQLException {
        for  (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.print(resultSet.getMetaData().getColumnName(i) + "  ");
        }
        System.out.println();
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + "  " + resultSet.getDate(2) +
            " " + resultSet.getInt(3));
        }
    }

    private void closeStatement() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            System.err.println("Error on close statement: "+ e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("Error on close connection: "+ e);
        }
    }

}
