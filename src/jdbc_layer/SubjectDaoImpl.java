package jdbc_layer;
import java.sql.SQLException;

import daos.EmneDao;
import top_layer.Subject;

public class SubjectDaoImpl implements EmneDao<Subject> {
	
	private DBConnection dbCon;
	public SubjectDaoImpl(String dBase, String user, String password) {
		dbCon = new DBConnection(dBase, user, password);
	}

	@Override
	public void addEmne(Subject emne) {
		String query = "insert into emner(navn) values(?)";
		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, emne.getName());
			int rows = dbCon.prepStmnt.executeUpdate();
			if(rows == 0){
				System.out.println("Error inserting subject " + emne.getName());
				return;
			}
			System.out.println("Added subject " + emne.getName());
			System.out.println(rows + " rows effected");
		}catch(SQLException e) {
			System.out.println("Error inserting subject.	" + e);
		}
		finally {
			dbCon.closeAllConnections();
		}
	}

	@Override
	public void updateEmne(Subject emne) {
		String query = "update emner set navn=?"
				+ "where emne_id=" + emne.getID();
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, emne.getName());
			int rows = dbCon.prepStmnt.executeUpdate();
			
			if(rows == 0) {
				System.out.println("Invalid update to subject " + emne.getID());
				return;
			}
			System.out.println("Updated subject with id = " + emne.getID());
			System.out.println(rows + " rows effected");
		}catch(SQLException e) {
			System.out.println("Error in updating subject.	" + e);
		}
		finally {
			dbCon.closeAllConnections();
		}
		
	}

	@Override
	public void deleteEmne(Subject emne) {
		Subject emneInDB = getEmne(emne.getName());
		String query = "delete from emner where navn=?";
		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, emne.getName());
			int rows = dbCon.prepStmnt.executeUpdate();
			
			if(rows == 0) {
				System.out.println("Emne not found in database");
				return;
			}
			System.out.println("Successfully deleted emne with id = " + emneInDB.getID());
			System.out.println(rows+ " rows effected");
		} catch (SQLException e) {
			System.out.println("Error in deleting emne.   " + e);
		}
		
	}

	@Override
	public Subject getEmne(String emneName) {
		String query = "select * from emner where navn=?";
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, emneName);
			dbCon.resultSet = dbCon.prepStmnt.executeQuery();
			
			if(!dbCon.resultSet.isBeforeFirst()) {
				System.out.println(emneName + " is not in database");
				return null;
			}
			dbCon.resultSet.next();
			
			Subject emne = new Subject();
			emne.setName(dbCon.resultSet.getString("navn"));
			emne.setID(dbCon.resultSet.getInt("emne_id"));
			return emne;
		}catch(SQLException e) {
			System.out.println("Emne is not in database.   " + e);
			return null;
		}
		finally {
			dbCon.closeAllConnections();
		}
	}
}
