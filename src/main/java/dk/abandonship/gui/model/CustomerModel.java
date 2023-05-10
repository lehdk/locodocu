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

    public void addCustomer(CustomerDTO customerDTO) throws SQLException {
        Customer customer = customerManager.addCustomer(customerDTO);

        if(customer != null) {
            customerObservableList.add(customer);
        }

    }

    public void deleteCustomer(Customer customer) throws SQLException {
        boolean wasDeleted = customerManager.deleteCustomer(customer);

        if(wasDeleted) {
            customerObservableList.remove(customer);
        }
    }

    public boolean editCustomer(Customer customer, CustomerDTO newData) throws SQLException {
        boolean wasEdited = customerManager.editCustomer(customer, newData);

        if(wasEdited) {
            customer.setName(newData.getName());
            customer.setEmail(newData.getEmail());
            customer.setAddress(newData.getAddress());
            customer.setPhone(newData.getPhone());
            customer.setPostalCode(newData.getPostalCode());
        }

        return wasEdited;
    }
}
