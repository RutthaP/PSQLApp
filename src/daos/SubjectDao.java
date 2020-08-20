package daos;

public interface SubjectDao<T> {
	public void addSubject(T emne);
	public T getSubject(String emneName);
	public void updateSubject(T emne);
	public void deleteSubject(T emne);
}
