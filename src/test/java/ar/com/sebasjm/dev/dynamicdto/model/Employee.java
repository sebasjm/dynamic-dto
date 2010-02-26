package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;

//@Entity
//@DiscriminatorValue(value="E")
public class Employee extends Persona implements Serializable {

	private static final long serialVersionUID = 9168818640674394832L;

	private String especialidad;
	
	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Employee() {}

}
