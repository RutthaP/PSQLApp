import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Scanner;

import daos.BetalingDao;
import daos.EmneDao;
import daos.StudentDao;
import jdbc_layer.PaymentDaoImpl;
import jdbc_layer.SubjectDaoImpl;
import jdbc_layer.StudentDaoImpl;
import top_layer.Payment;
import top_layer.Subject;
import top_layer.Student;

public class Main {
	public static void main(String[] args) {
		System.out.println("Working?");
		
		String[] result = readFile("C:\\Users\\Ruttharakan\\Documents\\UNI\\Projects\\SqlApp\\src\\pswrd.txt");
		String dBase = result[0];
		String user = result[1];
		String password = result[2];
		
		StudentDao<Student> studentDao = new StudentDaoImpl(dBase, user, password);
		EmneDao<Subject> emneDao = new SubjectDaoImpl(dBase, user, password);
		BetalingDao<Payment> betalingDao = new PaymentDaoImpl(dBase, user, password);
		
		studentDao.getStudent("Sam");
		
		Subject r2 = new Subject();
		r2.setName("R2");
		for (Student s : studentDao.getStudentsInEmne(r2)){
			System.out.println(s);
		}
		
		Student s1 = studentDao.getStudent("Singalam");
		studentDao.addStudentToEmne(s1, emneDao.getEmne("Kjemi1"));
		
		Student ruttha = studentDao.getStudent("Ruttharakan");
		System.out.println(ruttha.getID());
		for(Payment b : betalingDao.getStudentBetaling(ruttha.getID())) {
			System.out.println(b);
		}
		
		/* 
		 * Jobb med mer exception handling - NullPointerException
		 */
		
		// TODO betaling for flere mnder samtidig. Kanskje legge inn et beløp, så vil programmet kalkulere
		// hvor mange mnder det betales for. Men noen ganger blir det gitt discounts, så kanskje ikke gjør det.
		
		// TODO Lag en database med den seneste betalingen for hver student
		
		
		//Emne f1 = new Emne();
		//f1.setName("Fysikk1");
		//emneDao.addEmne(f1);
		
		//Emne f3 = new Emne();
		//f3.setName("Fysikk2");
		//emneDao.addEmne(f3);
		
		Subject f1 = emneDao.getEmne("Fysikk1");
		System.out.println(f1);
		
		/*Student s2 = new Student();
		s2.setFornavn("Jallaneser");
		s2.setEtternavn("Helvete");
		studentDao.addStudent(s2);*/
		
		/* Add multiple payments */
		//Betaling one = new Betaling();
		//one.setBelop(500);
		//one.setEmneID(3);
		//one.setStudentID(3);
		
		
	}
	
	
	private static String[] readFile(String path) {
		String[] result = new String[3];
		File file = new File(path);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		while(sc.hasNextLine()) {
			result[i++] = sc.nextLine();
		}
		
		return result;
	}
	
}
