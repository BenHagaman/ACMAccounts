package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCConnection {
	
	public JDBCConnection() {
		Connection conn = null;
	    Properties connectionProps = new Properties();
	
	    //connection information
	    connectionProps.put("user", "root");
	    connectionProps.put("password", "root");
	
	    //prepare the stuff for your queries
	    Statement stmt = null;
	    String query = "SELECT card_number FROM accounts;";
	    
	    System.out.println("-");
	
	    try {
		    System.out.println("--");
			Class.forName("com.mysql.jdbc.Driver");//.newInstance();
	    	//Class.forName("com.mysql.jdbc.Driver");
	    	//you'll want to check your database name, host name, and stuff
	    	conn = DriverManager.getConnection("jdbc:mysql:/Applications/MAMP/tmp/mysql/mysql.sock/CardReader", connectionProps);
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
	            String cardnumber = rs.getString("card_number");
	            System.out.println(cardnumber);
	        }
	    } catch (SQLException e) {
	        System.err.println(e.getSQLState());
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally {
	        if (stmt != null) { try {stmt.close();} catch(SQLException e) { System.err.println(e.getSQLState()); } }
	
		    /*try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
	    }
	
	    //cleanup!  Statement and ResultSet also have close methods, but we just let Java handle all that
	
	    
		/*try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
	}
}
