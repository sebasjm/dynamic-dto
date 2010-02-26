package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;

public class Contact extends Persona implements Serializable {

	private String puesto;
	private Employee responsable = new Employee();
	
	public Contact() {}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Employee getResponsable() {
		return responsable;
	}

	public void setResponsable(Employee responsable) {
		this.responsable = responsable;
	}

}
