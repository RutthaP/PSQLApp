import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
			result = Helper.readFile("C:\\Users\\Ruttharakan\\Documents\\UNI\\Projects\\SqlApp\\src\\pswrd.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Password file not found " + e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dBase = result[0];
		String user = result[1];
		String password = result[2];
		
		StudentDao studentDAO = new StudentDaoImpl(dBase, user, password);
		SubjectDao emneDao = new SubjectDaoImpl(dBase, user, password);
		PaymentDao betalingDao = new PaymentDaoImpl(dBase, user, password);
		
		Main mainProgram = new Main();
		
		Scanner sc = new Scanner(System.in);
		
		String input = null;
		
		boolean canExit = false;
		
		while(canExit == false) {
			System.out.println("1. Se antall studenter\n" 
							 + "2. Legg til student\n"
							 + "3. Student info");
			
			input = sc.nextLine();

			if(input.equals("Exit") || input.equals("exit")) {
				canExit = true;
				System.out.println("Bye");
			}
			
			switch (input) {
			case "1":
				List<Student> allStudents = studentDAO.getAllStudents();
				for(Student s : allStudents) {
					System.out.println(s.getID() +": "+ s.getFornavn() +" "+ s.getEtternavn() 
					+", Emne: " + s.getSubject().getName());
				}
				break;
				
			case "2":				
				Student newStudent = mainProgram.getStudentInfo(sc);
				studentDAO.addStudent(newStudent);
				break;
			case "3":
			
				break;
			}
			
		}
		
		/****  TESTING  ****/
		
		/*// Gets student named Sam, should not be in system
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
		
		
		// Add student
		Student s2 = new Student();
		s2.setFornavn("Singalam");
		s2.setEtternavn("2");
		studentDao.addStudent(s2);
		*/
		
		/*Student[] students = studentDao.getStudent("Singalam", "2");
		for(Student s : students) {
			System.out.println(s.getID() + ": " + s.getFornavn() + " " + s.getEtternavn());
		}
		System.out.println();
		
		Student[] students2 = studentDao.getStudent("Singalam");
		for(Student s : students2) {
			System.out.println(s.getID() + ": " + s.getFornavn() + " " + s.getEtternavn());
		}
		System.out.println();
		
		Student[] students3 = studentDao.getStudent("Ruttharakan");
		for(Student s : students3) {
			System.out.println(s.getID() + ": " + s.getFornavn() + " " + s.getEtternavn());
		}
		
		// Add payment
		Payment p1 = new Payment();*/
		
	}

	private Student getStudentInfo(Scanner sc) {
		System.out.println("Navn?");
		String name = sc.nextLine();
		
		String[] bothNames = name.split(" ");
		
		Student newStudent = new Student();
		if(bothNames.length == 1) {
			newStudent.setFornavn(bothNames[0]);
		}
		else if(bothNames.length == 2) {
			newStudent.setFornavn(bothNames[0]);
			newStudent.setEtternavn(bothNames[1]);
		}
		
		return newStudent;
	}
}
