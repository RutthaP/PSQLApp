package jdbc_layer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daos.StudentDao;
import top_layer.Subject;
import top_layer.Student;

/*
 * Accesses the "students"-table
 * Table info:
 * Columns: id(integer, not null), fornavn(char, not null), etternavn(char, not null)
 */

public class StudentDaoImpl implements StudentDao<Student>{
	private String fornavn, etternavn;
	private String klasse;
	private int studentID;
	
	private DBConnection dbCon;
	
	public StudentDaoImpl(String dBase, String user, String password) {
		dbCon = new DBConnection(dBase, user, password);
	}
	
	@Override
	public Student getStudent(String navn) {
		String query = "select * from students where fornavn=? or etternavn=?";		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, navn);
			dbCon.prepStmnt.setString(2, navn);
			dbCon.resultSet = dbCon.prepStmnt.executeQuery();
			
			if(!dbCon.resultSet.isBeforeFirst()) {
				System.out.println("Student not found in system");
				return null;
			}
			dbCon.resultSet.next();
			Student s = new Student();
			s.setID(dbCon.resultSet.getInt("id"));
			s.setFornavn(dbCon.resultSet.getString("fornavn"));
			s.setEtternavn(dbCon.resultSet.getString("etternavn"));
			
			return s;
		} catch (SQLException e) {
			System.out.println("Error getting student.   " + e);
			return null;
		}
		finally {
			dbCon.closeAllConnections();
		}
	}
	
	
	
	

	@Override
	public boolean updateStudent(Student student) {
		String query = "update students set fornavn=?, etternavn=? "
				+ "where id=" + student.getID();

		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, student.getFornavn());
			dbCon.prepStmnt.setString(2, student.getEtternavn());
			
			int row = dbCon.prepStmnt.executeUpdate();
			
			if(row == 0) {
				System.out.println("Cant find student in system");
				return false;
			}
			System.out.println("Successfully updated student with id = " + student.getID());
			System.out.println("Rows affected " + row);
			return true;
		}catch (SQLException e) {
			System.out.println("Student not found in system, no students updated.   " + e);
			return false;
		}
		finally {
			dbCon.closeAllConnections();
		}
	}

	@Override
	public boolean deleteStudent(Student student) {
		Student studentInDB = getStudent(student.getFornavn());
		String query = "delete from students where id=" + studentInDB.getID();
		try {
			dbCon.statement = dbCon.connection.createStatement();
			int rows = dbCon.statement.executeUpdate(query);
			if(rows == 0) {
				System.out.println("Student doesn't exist in database");
				return false;
			}
			System.out.println("Successfully deleted student with id = " + studentInDB.getID());
		} catch (SQLException e) {
			System.out.println("Error in deletion of student with id = " + studentInDB.getID() + "   " + e);
		}
		
		return false;
	}

	@Override
	public boolean addStudent(Student student) {
		String query = "insert into students(fornavn, etternavn) "
				+ "values(?, ?)";

		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, student.getFornavn());
			dbCon.prepStmnt.setString(2, student.getEtternavn());
			
			int row = dbCon.prepStmnt.executeUpdate();
			if(row == 0) {
				System.out.println("Error adding student");
			}
			System.out.println("Added student " + student.getFornavn());
		} catch (SQLException e) {
			System.out.println("Error adding student.   " + e);
			return false;
		}
		finally {
			dbCon.closeAllConnections();
		}
		return false;
	}

	@Override
	public List<Student> getStudentsInSubject(Subject emne) {
		String query = "select se.student_id, s.fornavn, s.etternavn " 
				+ "from students s "
				+ "join student_emner se on s.id = se.student_id "
				+ "join emner e on se.emne_id = e.emne_id "
				+ "where e.navn = ?";
		
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(query);
			dbCon.prepStmnt.setString(1, emne.getName());
			dbCon.resultSet = dbCon.prepStmnt.executeQuery();
			if (!dbCon.resultSet.isBeforeFirst()) {
				System.out.println("No students in class " + emne.getName());
				return null;
			}
			
			List<Student> studentsInClass = new ArrayList<>();
			while(dbCon.resultSet.next()) {
				Student s = new Student();
				s.setFornavn(dbCon.resultSet.getString("fornavn"));
				s.setEtternavn(dbCon.resultSet.getString("etternavn"));
				s.setID(dbCon.resultSet.getInt("student_id"));
				
				studentsInClass.add(s);
			}
			return studentsInClass;
		}catch (SQLException e) {
			System.out.println(e);
			return null;
		}catch (NullPointerException e) {
			System.out.println("Emne is null.   " + e);
			return null;
		}
		finally {
			dbCon.closeAllConnections();	
		}
		
	}

	@Override
	public void addStudentToSubject(Student student, Subject emne) {
		String relQuery = "insert into student_emner(student_id, emne_id, emne_start) "
					+ "values(?, ?, now())";
		try {
			dbCon.establishConnection();
			dbCon.prepStmnt = dbCon.connection.prepareStatement(relQuery);
			dbCon.prepStmnt.setInt(1, student.getID());
			dbCon.prepStmnt.setInt(2, emne.getID());
			dbCon.prepStmnt.execute();
			System.out.println("Added emne with id " + emne.getID() + " to student " + student.getID());
		} catch (SQLException e) {
			System.out.println("Error adding emne with id " + emne.getID() + " to student " + 
					student.getID() + ".   " + e);
		} catch (NullPointerException e) {
			System.out.println();
		}
		finally {
			dbCon.closeAllConnections();
		}		
	}
	
}
