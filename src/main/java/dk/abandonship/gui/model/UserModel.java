package dk.abandonship.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.businesslogic.UserManager;
import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

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

    public boolean editUser(User user) throws SQLException {
        return userManager.editUser(user);
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

    public List<Role> getAllRoles() throws SQLException {
        return userManager.getAllRoles();
    }
}
