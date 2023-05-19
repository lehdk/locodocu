package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.UserManager;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UserModel {
    private final UserManager userManager;
    private final ObservableList<User> userObservableList;

    public UserModel() {
        userManager = new UserManager();
        try {
            userObservableList = FXCollections.observableList(userManager.getAllUsers());
        } catch (SQLException e) {
            System.err.println("Could not load customers!");
            throw new RuntimeException(e);
        }
    }

    public ObservableList getAllTechnicians() throws Exception{
        ObservableList<User> technicians = FXCollections.observableList(userManager.getAllTechnicians());
        return technicians;
    }

    public ObservableList<User> getUserObserveableList() {
        return userObservableList;
    }

    public void deleteUser(User user) throws SQLException {
        boolean wasDeleted = userManager.deleteUser(user);

        if(wasDeleted) {
            userObservableList.remove(user);
        }
    }

    public boolean editUser(User user, User newData) throws SQLException {
        var result = userManager.editUser(user, newData);

        if (!result) {
            return false;
        }
        user.setName(newData.getName());
        user.setEmail(newData.getEmail());
        user.setPhone(newData.getPhone());
        user.setPassword(newData.getPassword());

        return true;
    }

    public void addUser(User user) throws SQLException {
        userManager.createUser(user);

        if(user != null) {
            userObservableList.add(user);
        }
    }

    public void addRole(User user, Role role) throws SQLException {
        userManager.addRole(user, role);
    }

    public void removeRole(User user, Role role) throws SQLException {
        userManager.removeRole(user, role);
    }

        public List<Role> getAllRoles() throws SQLException {
        return userManager.getAllRoles();
    }

    public Set<Role> getAllRolesForUser(User user) throws SQLException {
        return userManager.getAllRolesForUser(user);
    }
}
