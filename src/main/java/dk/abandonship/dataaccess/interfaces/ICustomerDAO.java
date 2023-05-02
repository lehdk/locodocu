package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDAO {

    /**
     * Gets all the customers from the datasource.
     * @return A list of customers. Empty list if no customers found.
     */
    List<Customer> getAllCustomers() throws SQLException;

    /**
     * Adds a customer to the data source.
     * @param customerDTO The DTO of the customer you want to add.
     * @return The newly added customer.
     */
    Customer addCustomer(CustomerDTO customerDTO) throws SQLException;
}
