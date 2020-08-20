package daos;
import java.util.List;

import top_layer.Payment;

public interface PaymentDao<T> {
	public void addPayment(T betaling, T... extraBetaling);
	public T getPayment(int betalingID);
	public void updatePayment(T betaling);
	public void deletePayment(int betalingID);
	
	public List<T> getStudentPayments(int studentID);
	public boolean checkTimeToPay(T betaling);
}
