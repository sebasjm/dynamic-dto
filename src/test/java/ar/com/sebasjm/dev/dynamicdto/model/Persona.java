package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;

public abstract class Persona implements Serializable, PersonaDTO {

	private Integer id;
	private String name;
	private String email;
	private String phone;
	
	public Persona() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    

}
