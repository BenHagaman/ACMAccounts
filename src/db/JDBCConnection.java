package db;

import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import vending.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCConnection {

    private static Connection conn = null;
	
	private JDBCConnection() {
	    Properties connectionProps = new Properties();
	
	    //connection information
	    connectionProps.put("user", "enter-username-here");
	    connectionProps.put("password", "enter-password-here");
	
	    //prepare the stuff for your queries
	    Statement stmt = null;
	    //String query = "SELECT * FROM products;";
	    
	    //System.out.println("-");
	
	    try {
			Class.forName("com.mysql.jdbc.Driver");//.newInstance();
	    	//Class.forName("com.mysql.jdbc.Driver");
	    	//you'll want to check your database name, host name, and stuff
            System.out.println(DriverManager.getLoginTimeout());
            DriverManager.setLoginTimeout(3);
            System.out.println(DriverManager.getLoginTimeout());
	    	conn = DriverManager.getConnection("jdbc:mysql://xxx.xxx.xxx.xxx:xxxx/DBName", connectionProps);
	    	if (conn == null) {
	        	throw new Exception("Errorr establishing connection to db");
	        }
	      } catch(Exception e) {
	        System.err.println(e.getMessage());
		    System.out.println("==");
	      }
	
	    try {
	        //get ready to run the query
	        if (conn == null) {
	        	throw new Exception("Error establishing connection to db");
	        }
	        stmt = conn.createStatement();
	        /*ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            //make sure your data types match
	            String name = rs.getString("name");
                Double price = rs.getDouble("price");
                boolean inStock = rs.getBoolean("in_stock");
                String imgPath = rs.getString("image_path");
                System.out.println(name);
                System.out.println(price);
                System.out.println(inStock);
                System.out.println(imgPath);
	        }     */
	    } catch (SQLException e) {
	        System.err.println(e.getSQLState());
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally {
	        if (stmt != null) { try {stmt.close();} catch(SQLException e) { System.err.println(e.getSQLState()); } }
	    }
	}

    public static Connection getConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                return conn;
            } else {
                new JDBCConnection(); //reconnect
                return conn;
            }
        } catch (SQLException e) {
            System.err.println(e.getCause());
            new JDBCConnection(); //reconnect
            return conn;
        }
    }

    public static void close() {
        System.out.println("closing JDBC Connection");
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getCause());
        }
    }

    public static ResultSet query(String query) {
        try{
            return getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            return null;
        }
    }

    public static void update(String query) {
        try{
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }
    }

    public static ResultSet getUsers(String cardno) {

        String query = "SELECT * FROM accounts WHERE card_number= ? ";

        PreparedStatement prepStmt = null;
        try {
            prepStmt = getConnection().prepareStatement(query);
            prepStmt.setString(1, cardno);
            return prepStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public static void createUser(String firstName, String lastName, String cardno, String username, boolean acmMember, String email, String password) throws MySQLIntegrityConstraintViolationException {
        String query = "" +
                "INSERT INTO accounts " +
                "VALUES ( ? " + //card number
                ", ? " +  //first name
                ", ? " +   //last name
                ", ? " +   //username
                ", ? " +  //password
                ", ? " +
                ", " + 0.00 +
                ", ? " +
                ", " + true +
                ", NOW());";


        PreparedStatement prepStmt = null;
        try {
            prepStmt = getConnection().prepareStatement(query);
            prepStmt.setString(1, cardno);
            prepStmt.setString(2, firstName);
            prepStmt.setString(3, lastName);
            prepStmt.setString(4, username);
            prepStmt.setString(5, password);
            prepStmt.setString(6, email);
            prepStmt.setBoolean(7, acmMember);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            if (e instanceof MySQLIntegrityConstraintViolationException) {
                throw (MySQLIntegrityConstraintViolationException)e;
            }
        }

    }

    public static ResultSet login(String username, String password) {
        String query = ("SELECT * FROM accounts WHERE username= ? AND password= ?");

        PreparedStatement prepStmt = null;
        try {
            prepStmt = getConnection().prepareStatement(query);
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            return prepStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public static void makePurchase(BigDecimal cardno, BigDecimal amount, String productName, BigDecimal newBalance) {

    }
}
