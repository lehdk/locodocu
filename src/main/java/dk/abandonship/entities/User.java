package dk.abandonship.entities;

import java.sql.Timestamp;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Timestamp disabledAt;

    public User(int id, String name, String email, String phone, String password, Timestamp disabledAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.disabledAt = disabledAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(Timestamp disabledAt) {
        this.disabledAt = disabledAt;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Two user objects are equal if their ids are the same.
     * @param obj The object to compare with.
     * @return If they are the same user.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;

        return getId() == other.getId();
    }
}
