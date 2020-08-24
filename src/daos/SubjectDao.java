package daos;

import top_layer.Subject;

public interface SubjectDao {
	public void addSubject(Subject emne);
	public Subject getSubject(String emneName);
	public void updateSubject(Subject emne);
	public void deleteSubject(Subject emne);
}
