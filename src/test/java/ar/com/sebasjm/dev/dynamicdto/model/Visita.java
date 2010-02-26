package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;

//@Entity
public class Visita implements Serializable {

	private static final long serialVersionUID = 8595414609504538308L;
	
	private Integer id;
	private Date fecha;
	private String motivo;
	private List<Contact> contactos = new ArrayList<Contact>();
	private List<Employee> participantes = new ArrayList<Employee>();
	private Client cliente = new Client();
	private String comentarios;
	
	public Visita() {}

//	@Id	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	public Client getCliente() {
		return cliente;
	}

	public void setCliente(Client cliente) {
		this.cliente = cliente;
	}

//	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
//	@JoinTable(name="visita_contactos")
	public List<Contact> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contact> contactos) {
		this.contactos = contactos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

//	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
//	@JoinTable(name="visita_empleados")
	public List<Employee> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Employee> participantes) {
		this.participantes = participantes;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
