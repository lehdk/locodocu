package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.UserManager;
import dk.abandonship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private UserManager userManager;
    public UserModel() {
        userManager = new UserManager();
    }

    public ObservableList getAllTechnicians() throws Exception{
        ObservableList<User> technicians = FXCollections.observableList(userManager.getAllTechnicians());
        return technicians;
    }


}
