package dk.abandonship.gui.controller.PopUpController;


import dk.abandonship.entities.Role;
import dk.abandonship.entities.User;
import dk.abandonship.gui.model.UserModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AssignRolesController implements Initializable {
    public CheckComboBox<Role> comboBoxRoles;
    public Button btnCancel;
    public Button btnConfirm;
    private ControllerAssistant controllerAssistant;
    private UserModel userModel;

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

    public void handleCancel() {
        close();
    }

    public void handleConfirm() {
        List<Role> selected = new ArrayList<>();

        for (Role u : comboBoxRoles.getItems()) {
            if(comboBoxRoles.getItemBooleanProperty(u).get()){
                selected.add(u);
            }
        }

        try {

            close();
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    public void close() {
        Stage stage  = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}

