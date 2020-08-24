package daos;
import java.util.List;

import top_layer.Payment;

public interface PaymentDao {
	public void addPayment(Payment betaling, Payment... extraBetaling);
	public Payment getPayment(int betalingID);
	public void updatePayment(Payment betaling);
	public void deletePayment(int betalingID);
	
	public List<Payment> getStudentPayments(int studentID);
	public boolean checkTimeToPay(Payment betaling);
}
