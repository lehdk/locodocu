package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.CustomerDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomerController implements Initializable {

    private CustomerDTO result;

    @FXML
    private TextField txtName, txtEmail, txtPhone, txtAddress;

    @FXML
    private Button btnAdd, btnCancel;

    public AddCustomerController() {
        result = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());
        txtEmail.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());
        txtAddress.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());

        validateInputs();
    }

    private void validateInputs() {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        boolean nameOk = txtName.getText().trim().length() > 3;

        Matcher emailMatcher = emailPattern.matcher(txtEmail.getText().trim());
        boolean emailOk = emailMatcher.matches();

        boolean addressOk = txtAddress.getText().trim().length() > 5;

        btnAdd.setDisable(!(nameOk && emailOk && addressOk));
    }

    public void handleCancel() {
        result = null;
        closeWindow();
    }

    public void handleAdd() {

        result = new CustomerDTO(
                txtName.getText().trim(),
                txtEmail.getText().trim(),
                txtPhone.getText().trim(),
                txtAddress.getText().trim()
        );

        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public CustomerDTO getResult() {
        return result;
    }
}
