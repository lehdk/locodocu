package dk.abandonship.dataaccess.proxies;

import com.google.gson.Gson;
import dk.abandonship.dataaccess.CustomerDatabaseDAO;
import dk.abandonship.dataaccess.DatabaseLogDAO;
import dk.abandonship.dataaccess.interfaces.ICustomerDAO;
import dk.abandonship.dataaccess.interfaces.IDatabaseLogDAO;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public class CustomerDatabaseDAOProxy implements ICustomerDAO {

    private final IDatabaseLogDAO databaseLogDAO;
    private final ICustomerDAO customerDAO;


    public CustomerDatabaseDAOProxy() {
        databaseLogDAO = new DatabaseLogDAO();
        customerDAO = new CustomerDatabaseDAO();
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        var result = customerDAO.getAllCustomers();

        databaseLogDAO.insertToLog("getAllCustomers", null, new Gson().toJson(result));

        return result;
    }

    @Override
    public Customer addCustomer(CustomerDTO customerDTO) throws SQLException {
        var result = customerDAO.addCustomer(customerDTO);

        databaseLogDAO.insertToLog("addCustomer", new Gson().toJson(customerDTO), new Gson().toJson(result));

        return result;
    }

    @Override
    public boolean deleteCustomer(Customer customer) throws SQLException {
        var result = customerDAO.deleteCustomer(customer);

        databaseLogDAO.insertToLog("deleteCustomer", new Gson().toJson(customer), new Gson().toJson(result));

        return result;
    }

    @Override
    public boolean editCustomer(Customer customer, CustomerDTO newData) throws SQLException {
        var result = customerDAO.editCustomer(customer, newData);

        databaseLogDAO.insertToLog(
                "editCustomer",
                new Gson().toJson(customer) + new Gson().toJson(newData),
                new Gson().toJson(result)
        );

        return result;
    }
}
