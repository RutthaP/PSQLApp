package top_layer;
import java.sql.Timestamp;

public class Payment {
	int betalingID, studentID, emneID;
	int belop; 
	Timestamp date;
	
	public int getBetalingID() {
		return betalingID;
	}

	public void setBetalingID(int betalingID) {
		this.betalingID = betalingID;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public int getEmneID() {
		return emneID;
	}

	public void setEmneID(int emneID) {
		this.emneID = emneID;
	}

	public int getBelop() {
		return belop;
	}

	public void setBelop(int belop) {
		this.belop = belop;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
}