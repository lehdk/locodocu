package dk.abandonship.entities;

import java.time.LocalDate;

public class ProjectDTO {
    private String address;
    private Customer customer;
    private LocalDate date;

    public ProjectDTO(String address, Customer customer, LocalDate date) {
        this.address = address;
        this.customer = customer;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
