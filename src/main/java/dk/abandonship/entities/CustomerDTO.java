package dk.abandonship.entities;

public class CustomerDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String postalCode;

    public CustomerDTO(String name, String email, String phone, String address, String postalCode) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Customer convertToCustomer(int id) {
        return new Customer(
                id,
                name,
                email,
                phone,
                address,
                postalCode
        );
    }
}
