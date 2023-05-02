package dk.abandonship.dataaccess.interfaces;

import dk.abandonship.entities.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDAO {

    /**
     * Gets all the customers from the datasource
     * @return A list of customers. Empty list if no customers found.
     */
    List<Customer> getAllCustomers() throws SQLException;

}
