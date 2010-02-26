package ar.com.sebasjm.dev.dynamicdto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client extends Persona implements Serializable {

	private Location location = new Location();
	private List<Contact> contacts = new ArrayList<Contact>();

	public Client() {}

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	


}
