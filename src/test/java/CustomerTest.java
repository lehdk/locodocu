import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;
import dk.abandonship.gui.model.CustomerModel;
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
        String postalCode = "4212";

        Customer c = new Customer(id, name, email, phone, address, postalCode);

        assertEquals(id, c.getId());
        assertEquals(name, c.getName());
        assertEquals(email, c.getEmail());
        assertEquals(phone, c.getPhone());
        assertEquals(address, c.getAddress());
        assertEquals(postalCode, c.getPostalCode());
    }

    @Test
    void canLoadCustomersFromDatabase() {
        CustomerModel customerModel = new CustomerModel();

        var customers = customerModel.getCustomerObservableList().stream().toList();

        assertNotNull(customers);
    }

    @Test
    void canConvertCustomerDTOToCustomer() {
        int id = 42;
        String name = "Test Customer";
        String email = "test@customer.dk";
        String phone = "12345678";
        String address = "Test Road Nr. 42, Nashville";
        String postalCode = "89475";

        CustomerDTO customerDTO = new CustomerDTO(name, email, phone, address, postalCode);

        Customer customer = customerDTO.convertToCustomer(id);

        assertEquals(id, customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
        assertEquals(postalCode, customer.getPostalCode());
    }
}
