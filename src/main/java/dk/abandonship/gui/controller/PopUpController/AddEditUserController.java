package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditUserController implements Initializable {

    private User result;
    public TextField txtPassword;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPhone;
    public Button btnAdd, btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());
        txtEmail.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());
        txtPassword.textProperty().addListener((obs, oldValue, newValue) -> validateInputs());

        validateInputs();
    }

    public void editMode(User user) {
        txtName.textProperty().setValue(user.getName());
        txtEmail.textProperty().setValue(user.getEmail());
        txtPhone.textProperty().setValue(user.getPhone());
        txtPassword.textProperty().setValue(user.getPassword());
    }

    private void validateInputs() {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        boolean nameOk = txtName.getText().trim().length() > 3;

        Matcher emailMatcher = emailPattern.matcher(txtEmail.getText().trim());
        boolean emailOk = emailMatcher.matches();

        boolean passwordOk = txtPassword.getText().trim().length() > 5;

        btnAdd.setDisable(!(nameOk && emailOk && passwordOk));
    }

    public void handleAdd() {
        result = new User(
                1,
                txtName.getText().trim(),
                txtEmail.getText().trim(),
                txtPhone.getText().trim(),
                txtPassword.getText().trim(),
                null
        );

        closeWindow();
    }

    public void handleCancel() {
        result = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public User getResult() {
        return result;
    }
}
