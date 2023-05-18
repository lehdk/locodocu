package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.Customer;
import dk.abandonship.entities.ProjectDTO;
import dk.abandonship.gui.model.CustomerModel;
import dk.abandonship.utils.ControllerAssistant;
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

    @FXML private TextField fieldAddress, fieldPostalCode, fieldName;
    @FXML private ComboBox<Customer> comboBoxCustomer;
    @FXML private DatePicker datePicker;
    @FXML private Button btnCancel;

    private ControllerAssistant controllerAssistant;

    private ProjectDTO result = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant  = ControllerAssistant.getInstance();
        try {
            CustomerModel customerModel = new CustomerModel();

            comboBoxCustomer.setItems(customerModel.getCustomerObservableList());
            comboBoxCustomer.valueProperty().addListener(event -> setPostalCode());
        } catch (Exception e) {
            controllerAssistant.displayError(e);
        }
    }

    private void setPostalCode(){
        if (!fieldAddress.getText().isEmpty()) return;

        fieldAddress.setText(comboBoxCustomer.getValue().getAddress());
    }

    private boolean isDataValid(){
        if (fieldAddress.getText().isEmpty()) {
            controllerAssistant.displayAlert("Missing address");
            return false;
        }
        if (comboBoxCustomer.getValue() == null) {
            controllerAssistant.displayAlert("Missing a customer");
            return false;
        }
        if (datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now())) {
            controllerAssistant.displayAlert("Missing a proper date");
            return false;
        }

        return true;
    }
    public void handleConfirm(){
        if (!isDataValid()) return;

        String name = fieldName.getText();
        String address = fieldAddress.getText();
        String postalCode = fieldPostalCode.getText();
        Customer customer = comboBoxCustomer.getValue();
        LocalDate time = datePicker.getValue();

        result = new ProjectDTO(name, address, postalCode, customer, time);

        handleClose();
    }

    public void handleClose() {
        Stage stage  = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void handleCancel() {
        result = null;
        handleClose();
    }

    public ProjectDTO getResult() {
        return result;
    }
}
