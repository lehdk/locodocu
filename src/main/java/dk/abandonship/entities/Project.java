package dk.abandonship.entities;

import dk.abandonship.utils.DefaultRoles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Project {

    private int id;
    private String name;
    private Timestamp createdAt;
    private Customer customer;

    private List<User> assignedTechnicians;

    private Set<Documentation> documentations;

    public Project(int id, String name, Timestamp createdAt, Customer customer) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.customer = customer;
        documentations = new HashSet<>();
        assignedTechnicians = new ArrayList<>();
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

    public Set<Documentation> getDocumentations() {
        return documentations;
    }

    public void setDocumentations(Set<Documentation> documentations) {
        this.documentations = documentations;
    }

    public void addDocumentation(Documentation documentation) {
        documentations.add(documentation);
    }

    public void removeDocumentation(Documentation documentation) {
        documentations.remove(documentation);
    }

    public List<User> getAssignedTechnicians() {
        return assignedTechnicians;
    }

    public void setAssignedTechnicians(List<User> assignedTechnicians) {
        this.assignedTechnicians = assignedTechnicians;
    }

    public void assignTechnician(User user) {
        if(!user.hasRole(DefaultRoles.TECHNICIAN)) return;

        assignedTechnicians.add(user);
    }

    public void removeTechnician(User user) {
        assignedTechnicians.remove(user);
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name;
    }
}
