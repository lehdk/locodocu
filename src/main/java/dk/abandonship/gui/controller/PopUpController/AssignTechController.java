package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.User;
import dk.abandonship.gui.model.UserModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignTechController implements Initializable {
    @FXML private Button bntCancel, btnConfirm;
    @FXML private CheckComboBox<User> comboBoxTech;

    private UserModel userModel;
    private ControllerAssistant controllerAssistant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();
        userModel = new UserModel();

        comboBoxTech.setTitle("Technicians");

        try {
            comboBoxTech.getItems().addAll(userModel.getAllTechnicians());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage  = (Stage) bntCancel.getScene().getWindow();
        stage.close();
    }

    public void handleConfirm(ActionEvent actionEvent) {
        //TODO Save Technicians
    }
}
