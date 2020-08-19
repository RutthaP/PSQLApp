package daos;
import java.util.List;

import top_layer.Payment;

public interface BetalingDao<T> {
	public void addBetaling(T betaling, T... extraBetaling);
	public T getBetaling(int betalingID);
	public void updateBetaling(T betaling);
	public void deleteBetaling(int betalingID);
	
	public List<T> getStudentBetaling(int studentID);
	public boolean checkTimeToPay(T betaling);
}
