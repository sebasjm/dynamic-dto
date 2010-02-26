package ar.com.sebasjm.dev.dynamicdto;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import ar.com.sebasjm.dev.dynamicdto.model.Client;
import ar.com.sebasjm.dev.dynamicdto.model.ClientDTO;
import ar.com.sebasjm.dev.dynamicdto.model.ClientDTO1;
import ar.com.sebasjm.dev.dynamicdto.model.Contact;
import ar.com.sebasjm.dev.dynamicdto.model.Location;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestDynamicDTO {

    protected Client   client   = new Client();
    protected Location location = new Location();

    @BeforeMethod
    public void prepareValues(){
        client = new Client();
        location = new Location();

		client.setContacts( new ArrayList<Contact>() );

		location.setAltura(12);
		client.setLocation( location );
		client.getContacts().add( new Contact() );
    }

    @Test
    public void testChangeCompositeValue() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		ClientDTO dto = DynamicDTO.getDtoInstance(ClientDTO.class, client);
        assert dto != null;

        assert dto.getLocation().getAltura() == 12;

		location = new Location();
		location.setAltura(23);
		dto.setLocation(location);
        
		assert dto.getLocation().getAltura() == 23 : "changing a composite property doesnt work";
        
    }

    @Test
    public void testChangeCollectionValue() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		ClientDTO1 dto1 = DynamicDTO.getDtoInstance(ClientDTO1.class, client);
    
        assert dto1.getContacts().size() == 1;

		dto1.getContacts().add( new Contact());

        assert dto1.getContacts().size() == 2;
    }

    @Test
    public void testImplementedInterfaces() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		ClientDTO1 dto1 = DynamicDTO.getDtoInstance(ClientDTO1.class, client);

        List<Class> interfacesShouldBeImplemented = new ArrayList( Arrays.asList( new Class[]{
            ClientDTO1.class,
            DynamicDTOControl.class
        }));
        
        for (Class iface : dto1.getClass().getInterfaces()) {
            if ( interfacesShouldBeImplemented.contains( iface ) ) {
                interfacesShouldBeImplemented.remove( iface );
            }
        }

        assert interfacesShouldBeImplemented.size() == 0: "not all ifaces was implemented as espected";
    }

}
