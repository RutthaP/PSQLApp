package jdbc_layer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

/*
 * Database connection class.
 * Public methods:
 * 			establishConnection()
 * 			closeAllConnections()
 */
public class DBConnection {
	Connection connection;
	Statement statement;
	PreparedStatement prepStmnt;
	ResultSet resultSet;
	
	private String dbName, username, password;
	
	/*
	 * Registers the driver
	 */
	public DBConnection(String dbName, String username, String password) {
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		connection = null;
		try {
			Class.forName("org.postgresql.Driver"); 
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void establishConnection() {
		try {
			connection = DriverManager.getConnection(dbName, username, password);
		} catch (SQLException e) {
			System.out.println("Error in establishing connection... " + e);
		}
	}
	
	
	public void closeAllConnections() {
		try {
			if(statement != null)
				statement.close();
			if(prepStmnt != null)
				prepStmnt.close();
			if(resultSet != null)
				resultSet.close();
			if(connection != null) 
				connection.close();
			
		} catch (SQLException e) {
			System.out.println("Error in closing connection... " + e);
		}
	}
	
	private void closeStatement() {
		try {
			statement.close();
		} catch (SQLException e) {
			System.out.println("Error in closing statement " + e);
		}
	}
	
	
	
	/*
	public void getAllData() {
		String query = "select * from students";
		try {
			establishConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			
			while(resultSet.next()) {
				String fornavn = resultSet.getString("fornavn");
				String etternavn = resultSet.getString("etternavn");
				String klasse = resultSet.getString("class");
				
				System.out.println(fornavn + " " + etternavn + ", " + klasse);
			}
			
		} catch (SQLException e) {
			System.out.println("Error in creating statement");
			e.printStackTrace();
		}
		finally {
			closeAllConnections();
		}
	}*/
	
}
