package dk.abandonship.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.abandonship.businesslogic.UserManager;
import dk.abandonship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class UserModel {
    private UserManager userManager;

    private ObservableList<User> userObservableList;

    public UserModel() {
        userManager = new UserManager();
    }

    public ObservableList getAllTechnicians() throws Exception{
        ObservableList<User> technicians = FXCollections.observableList(userManager.getAllTechnicians());
        return technicians;
    }

    public ObservableList<User> getUserObserveableList() {
        return userObservableList;
    }


    public boolean deleteUser(User user) throws SQLException {
        return userManager.deleteUser(user);
    }

    public boolean editUser(User user) {
        return true;
    }

    public void addUser(User user) throws SQLServerException {
        userManager.createUser(user);
    }
}
