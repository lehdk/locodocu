package dk.abandonship.entities;

import java.time.LocalDate;

public class ProjectDTO {
    private String name;
    private String postalCode;
    private String address;
    private Customer customer;
    private LocalDate date;

    public ProjectDTO(String name, String address, String postalCode, Customer customer, LocalDate date) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
