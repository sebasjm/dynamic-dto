package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;

//@Entity
public class Project implements Serializable {

	private static final long serialVersionUID = 5940447225257389917L;
	
	private Integer id;
	private String nombre;
	private Client cliente;
	private List<Employee> integrantes = new ArrayList<Employee>();
	
	public Project(){}
	
//	@Id
//	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Persona)) return false;
		if (obj == null) return false;
		Project c = (Project) obj;
		return  c.id.equals(id);
	
	}

//	@ManyToMany
//	@JoinTable(name="proyecto_empleado")
	public List<Employee> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Employee> integrantes) {
		this.integrantes = integrantes;
	}

//	@ManyToOne
	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}
}
