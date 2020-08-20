package jdbc_layer;
import top_layer.Payment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import daos.PaymentDao;

/*
 * Accesses the "Betaling"-table
 * Table info:
 * Colums: betaling_id(integer, not null), student_id(integer, not null), 
 * 		   emne_id(integer, not null), belop(integer, not null), 
 * 		   dato(timestamp w/o time zone, not null)
 * 
 */

public class PaymentDaoImpl implements PaymentDao<Payment> {

	DBConnection dbCon;
	
	public PaymentDaoImpl(String dBase, String user, String password) {
		dbCon = new DBConnection(dBase, user, password);
	}
	
	
	
	@Override
	public void addPayment(Payment payment, Payment... extraPayment) {
		String query = "insert into betaling(student_id, emne_id, belop, dato) values(?,?,?,?)";
		
		int extra = extraPayment.length;
		for(int i = 0; i < extra-1; i++) {
			query += ", values(?,?,?,?)";
		}
		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			int prepIndex = 0;
			for(Payment b : extraPayment) {
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
	public Payment getPayment(int paymentId) {
		String query = "select * from betaling where betaling_id=" + paymentId;
		
		try {
			dbCon.establishConnection();
			dbCon.statement = dbCon.connection.createStatement();
			dbCon.resultSet = dbCon.statement.executeQuery(query);
			
			if(!dbCon.resultSet.isBeforeFirst()) {
				System.out.println("Betaling with id=" +paymentId+ " not found in database");
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
	public List<Payment> getStudentPayments(int studentID) {
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
	public void updatePayment(Payment betaling) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePayment(int betalingID) {
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

	@Override
	public boolean checkTimeToPay(Payment betaling) {
		// TODO Auto-generated method stub
		return false;
	}

}
