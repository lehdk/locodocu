import dk.abandonship.entities.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    void canCreateCustomerWithCorrectFields() {
        int id = 42;
        String name = "Mærsk";
        String email = "info@mærsk.dk";
        String phone = "42425242";
        String address = "Fantasivej 42, København";

        Customer c = new Customer(id, name, email, phone, address);

        assertEquals(c.getId(), id);
        assertEquals(c.getName(), name);
        assertEquals(c.getEmail(), email);
        assertEquals(c.getPhone(), phone);
        assertEquals(c.getAddress(), address);
    }

}
