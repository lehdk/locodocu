package dk.abandonship.gui.controller.PopUpController;


import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import dk.abandonship.gui.model.UserModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AssignRolesController implements Initializable {
    @FXML
    private CheckComboBox<Role> comboBoxRoles;
    @FXML
    private Button btnConfirm, btnCancel;
    private ControllerAssistant controllerAssistant;
    private UserModel userModel;
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();
        userModel = new UserModel();

        comboBoxRoles.setTitle("Roles");

        try {
            comboBoxRoles.getItems().addAll(userModel.getAllRoles());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    public void setUser(User user) throws SQLException {
        this.user = user;

        Set<Role> alreadyChecked = userModel.getAllRolesForUser(user);
        for (var r : comboBoxRoles.getItems()) {
            if (alreadyChecked.contains(r)) {
                comboBoxRoles.getCheckModel().check(r);
            }
        }
    }

    public void handleCancel() {
        close();
    }

    public void handleConfirm() {

    }

    public void close() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}