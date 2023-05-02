package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.CustomerManager;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class CustomerModel {

    private final CustomerManager customerManager;

    private final ObservableList<Customer> customerObservableList;

    public CustomerModel() {
        customerManager = new CustomerManager();
        try {
            customerObservableList = FXCollections.observableList(customerManager.getAllCustomers());
        } catch (SQLException e) {
            System.err.println("Could not load customers!");
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Customer> getCustomerObservableList() {
        return customerObservableList;
    }

    public Customer addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = customerManager.addCustomer(customerDTO);

        if(customer != null) {
            customerObservableList.add(customer);
        }

        return customer;
    }
}
