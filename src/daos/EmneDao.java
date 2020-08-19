package daos;

public interface EmneDao<T> {
	public void addEmne(T emne);
	public T getEmne(String emneName);
	public void updateEmne(T emne);
	public void deleteEmne(T emne);
}
