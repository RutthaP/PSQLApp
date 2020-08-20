package daos;
import java.util.List;

import top_layer.Subject;
import top_layer.Student;

public interface StudentDao<T> {
	public boolean addStudent(T student);
	public Student getStudent(String name);		
	public boolean updateStudent(T student);
	public boolean deleteStudent(T student);
	
	public List<T> getStudentsInSubject(Subject emne);
	public void addStudentToSubject(T student, Subject emne);
}
