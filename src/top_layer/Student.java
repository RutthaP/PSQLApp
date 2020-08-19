package top_layer;
import java.util.ArrayList;

public class Student {
	private String fornavn, etternavn;
	private int id;
	
	public String getFornavn() {
		return fornavn;
	}
	
	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}
	
	public String getEtternavn() {
		return etternavn;
	}
	
	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "ID: " +id+ ", Navn: " +fornavn+ " " +etternavn;
	}
}
