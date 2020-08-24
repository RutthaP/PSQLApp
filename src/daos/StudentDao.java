package daos;
import java.util.List;

import top_layer.Subject;
import top_layer.Student;

public interface StudentDao {
	public boolean addStudent(Student student);
	public List<Student> getStudent(String firstName);	
	public List<Student> getStudent(String firstName, String surname);		
	public boolean updateStudent(Student student);
	public boolean deleteStudent(Student student);
	
	public List<Student> getStudentsInSubject(Subject emne);
	public void addStudentToSubject(Student student, Subject emne);
	
	public List<Student> getAllStudents();
}
