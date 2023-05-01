package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.gui.model.ProjectModel;
import dk.abandonship.gui.model.UserModel;
import dk.abandonship.utils.ControllerAssistant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateProjectView implements Initializable {

    @FXML private TextField fieldAddress, fieldCustomer;
    @FXML private ComboBox comboBoxTechnicians;
    @FXML private DatePicker datePicker;
    @FXML private Button btnCancel, btnConfirm1;

    private ControllerAssistant controllerAssistant;

    private UserModel userModel;

    private ProjectModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant  = ControllerAssistant.getInstance();
        try {
            model = new ProjectModel();
            userModel = new UserModel();

            comboBoxTechnicians.setItems(userModel.getAllTechnicians());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    private boolean isDataValid(){
        if (fieldAddress.getText().isEmpty()) {
            controllerAssistant.displayAlert("Missing adress");
            return false;
        }
        if (fieldCustomer.getText().isEmpty()) {
            controllerAssistant.displayAlert("Missing a customer");
            return false;
        }
        if (datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now())) {
            controllerAssistant.displayAlert("Missing a proper date");
            return false;
        }
        if (comboBoxTechnicians.getValue() == null) {
            controllerAssistant.displayAlert("Missing a Technician");
            return false;
        }

        return true;
    }
    public void handleConfirm(ActionEvent actionEvent){
        if (!isDataValid()) return;

        String address  = fieldAddress.getText();
        String customer = fieldCustomer.getText();
        LocalDate time = datePicker.getValue();
        String technician = comboBoxTechnicians.getValue().toString();

        //TODO Create Project with Correct DATA

        System.out.println("created Project");
    }

    public void handleClose(ActionEvent actionEvent) {
        Stage stage  = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
