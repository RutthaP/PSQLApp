import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

import daos.PaymentDao;
import daos.SubjectDao;
import daos.StudentDao;
import jdbc_layer.PaymentDaoImpl;
import jdbc_layer.SubjectDaoImpl;
import jdbc_layer.StudentDaoImpl;
import top_layer.Payment;
import top_layer.Subject;
import top_layer.Student;

public class Main {
	
	/*
	 * Finds connection info using a text file that consists of 3 lines,
	 * (1) database url
	 * (2) username
	 * (3) password
	 * 
	 */
	
	// TODO Lag en database med den seneste betalingen for hver student
	
	public static void main(String[] args) {	
		String[] result = null;
		try {
			result = readFile("C:\\Users\\Ruttharakan\\Documents\\UNI\\Projects\\SqlApp\\src\\pswrd.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Password file not found " + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dBase = result[0];
		String user = result[1];
		String password = result[2];
		
		StudentDao<Student> studentDao = new StudentDaoImpl(dBase, user, password);
		SubjectDao<Subject> emneDao = new SubjectDaoImpl(dBase, user, password);
		PaymentDao<Payment> betalingDao = new PaymentDaoImpl(dBase, user, password);
		
		/****  TESTING  ****/
		
		// Gets student named Sam, should not be in system
		Student sam = studentDao.getStudent("Sam");
		
		// Get students in subject R2
		Subject r2 = new Subject();
		r2.setName("R2");
		for (Student s : studentDao.getStudentsInSubject(r2)){
			System.out.println(s);
		}
		
		
		// Add student to non existing subject
		Student s1 = studentDao.getStudent("Singalam");
		studentDao.addStudentToSubject(s1, emneDao.getSubject("Kjemi1"));
		
		
		// Gets student named Ruttharakan, should get result
		Student ruttha = studentDao.getStudent("Ruttharakan");
		System.out.println(ruttha.getID());
		for(Payment b : betalingDao.getStudentPayments(ruttha.getID())) {
			System.out.println(b);
		}
		
		// Add subject
		Subject f11 = new Subject();
		f11.setName("Fysikk1");
		emneDao.addSubject(f11);
		
		// Get subject
		Subject f1 = emneDao.getSubject("Fysikk1");
		System.out.println(f1.getName());		
		
	}
	
	
	private static String[] readFile(String path) throws Exception {
		String[] result = new String[3];
		File file = new File(path);
		Scanner sc =  new Scanner(file);		
		
		int i = 0;
		while(sc.hasNextLine()) {
			result[i++] = sc.nextLine();
		}
		
		sc.close();
		return result;
	}
	
}
