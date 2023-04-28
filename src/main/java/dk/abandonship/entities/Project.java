package dk.abandonship.entities;

import java.sql.Timestamp;

public class Project {

    private int id;
    private String name;
    private Timestamp createdAt;
    private Customer customer;

    public Project(int id, String name, Timestamp createdAt, Customer customer) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name;
    }
}
