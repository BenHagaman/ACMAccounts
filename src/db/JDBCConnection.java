package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCConnection {

    private Connection conn = null;
	
	public JDBCConnection() {
	    Properties connectionProps = new Properties();
	
	    //connection information
	    connectionProps.put("user", "haga0062");
	    connectionProps.put("password", "dieJ8ugi");
	
	    //prepare the stuff for your queries
	    Statement stmt = null;
	    String query = "SELECT * FROM products;";
	    
	    System.out.println("-");
	
	    try {
		    System.out.println("--");
			Class.forName("com.mysql.jdbc.Driver");//.newInstance();
	    	//Class.forName("com.mysql.jdbc.Driver");
	    	//you'll want to check your database name, host name, and stuff
	    	conn = DriverManager.getConnection("jdbc:mysql://160.94.179.135:3306/prod_financial_accounts", connectionProps);
		    System.out.println("---");
	    	if (conn == null) {
	        	throw new Exception("Errorr establishing connection to db");
	        }
	        System.out.println("." + conn.toString());
	      } catch(Exception e) {
	        System.err.println(e.getMessage());
		    System.out.println("==");
	      }
	
	    try {
	        //get ready to run the query
		    System.out.println("....");
	        if (conn == null) {
	        	throw new Exception("Error establishing connection to db");
	        }
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
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
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getSQLState());
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally {
	        if (stmt != null) { try {stmt.close();} catch(SQLException e) { System.err.println(e.getSQLState()); } }
	    }
	}

    public ResultSet execute(String query) {
        try{
            return conn.createStatement().executeQuery(query);
        } catch (SQLException e) {
            return null;
        }
    }
}
