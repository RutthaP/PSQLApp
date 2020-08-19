package jdbc_layer;
import top_layer.Payment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import daos.BetalingDao;

public class PaymentDaoImpl implements BetalingDao<Payment> {

	DBConnection dbCon;
	
	public PaymentDaoImpl(String dBase, String user, String password) {
		dbCon = new DBConnection(dBase, user, password);
	}
	
	@Override
	public void addBetaling(Payment betaling, Payment... extraBetaling) {
		String query = "insert into betaling(student_id, emne_id, belop, dato) values(?,?,?,?)";
		
		int extra = extraBetaling.length;
		for(int i = 0; i < extra-1; i++) {
			query += ", values(?,?,?,?)";
		}
		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			int prepIndex = 0;
			for(Payment b : extraBetaling) {
				dbCon.prepStmnt.setInt(++prepIndex, b.getStudentID());
				dbCon.prepStmnt.setInt(++prepIndex, b.getEmneID());
				dbCon.prepStmnt.setInt(++prepIndex, b.getBelop());				
				
				LocalDateTime now = LocalDateTime.now();
				Timestamp timestamp = Timestamp.valueOf(now);
				b.setDate(timestamp);
				
				dbCon.prepStmnt.setTimestamp(++prepIndex, timestamp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			dbCon.closeAllConnections();
		}
		
	}

	@Override
	public Payment getBetaling(int betalingID) {
		String query = "select * from betaling where betaling_id=" + betalingID;
		
		try {
			dbCon.establishConnection();
			dbCon.statement = dbCon.connection.createStatement();
			dbCon.resultSet = dbCon.statement.executeQuery(query);
			
			if(!dbCon.resultSet.isBeforeFirst()) {
				System.out.println("Betaling with id=" +betalingID+ " not found in database");
				return null;
			}
			dbCon.resultSet.next();
			Payment b = new Payment();
			b.setBetalingID(dbCon.resultSet.getInt("betaling_id"));
			b.setStudentID(dbCon.resultSet.getInt("student_id"));
			b.setEmneID(dbCon.resultSet.getInt("emne_id"));
			b.setBelop(dbCon.resultSet.getInt("belop"));
			b.setDate(dbCon.resultSet.getTimestamp("dato"));
			
			return b;
		} catch (SQLException e) {
			System.out.println("Error in getting payment.   " + e);
			return null;
		}
		finally {
			dbCon.closeAllConnections();
		}
		
	}
	
	@Override
	public List<Payment> getStudentBetaling(int studentID) {
		String query = "select b.betaling_id, e.emne_id, e.navn, b.belop, b.dato " 
				+ "from betaling b " 
				+ "join emner e " 
				+ "on b.emne_id=e.emne_id " 
				+ "where b.student_id=" + studentID;
		
		try {
			dbCon.establishConnection();
			dbCon.statement = dbCon.connection.createStatement();
			dbCon.resultSet = dbCon.statement.executeQuery(query);
			
			if(!dbCon.resultSet.isBeforeFirst()) {
				System.out.println("No results for student with id = " + studentID);
				return null;
			}
			List<Payment> betalinger = new ArrayList<>();
			while(dbCon.resultSet.next()) {
				Payment b = new Payment();
				b.setBetalingID(dbCon.resultSet.getInt("betaling_id"));
				b.setStudentID(studentID);
				b.setEmneID(dbCon.resultSet.getInt("emne_id"));
				b.setBelop(dbCon.resultSet.getInt("belop"));
				b.setDate(dbCon.resultSet.getTimestamp("dato"));
				
				betalinger.add(b);
			}				
			return betalinger;
			
		} catch (SQLException e) {
			System.out.println("Error finding betaling for student with id = " + studentID);
			return null;
		}
		finally {
			dbCon.closeAllConnections();
		}
	}

	@Override
	public void updateBetaling(Payment betaling) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBetaling(int betalingID) {
		String query = "delete from betaling where betaling_id=" +betalingID;
		
		dbCon.establishConnection();
		try {
			dbCon.statement = dbCon.connection.createStatement();
			int rows = dbCon.statement.executeUpdate(query);
			
			System.out.println(rows + " rows were effected");
		} catch (SQLException e) {
			System.out.println("Error finding betaling.   " + e);
		}
		finally {
			dbCon.closeAllConnections();
		}
		
	}

	public void something() {
		DBConnection dbCon = new DBConnection("jdbc:postgresql://localhost:5432/test", "postgres", "themaskguy999");
		String query = "select student_id, dato from betaling where student_id=1";
		try {
			dbCon.establishConnection();
			dbCon.statement = dbCon.connection.createStatement();
			dbCon.resultSet = dbCon.statement.executeQuery(query);
			
			if(dbCon.resultSet.isBeforeFirst()) {
				dbCon.resultSet.next();
				Timestamp date1 = dbCon.resultSet.getTimestamp("dato");
				dbCon.resultSet.next();
				Timestamp date2 = dbCon.resultSet.getTimestamp("dato");
				LocalDateTime lastPayment = date2.toLocalDateTime();
				LocalDateTime now = LocalDateTime.now();
				Duration duration = Duration.between(lastPayment, now);
				long diff = duration.toDays();
				System.out.println("Diff = " + diff);
				System.out.println(date2 + ", " + lastPayment);
				if(diff >= 30) {
					System.out.println("Time to pay");
				}
				 
				
			}
		}catch(SQLException e) {
			System.out.println("Something went wrong... ");
			e.printStackTrace();
		}
		finally {
			dbCon.closeAllConnections();
		}
	}

	@Override
	public boolean checkTimeToPay(Payment betaling) {
		// TODO Auto-generated method stub
		return false;
	}

}
