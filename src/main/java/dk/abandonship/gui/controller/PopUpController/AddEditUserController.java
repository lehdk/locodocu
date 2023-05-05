package dk.abandonship.gui.controller.PopUpController;

import dk.abandonship.entities.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditUserController implements Initializable {

    private AddEditCustomerController addEditCustomerController;
    public TextField txtPassword;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtPhone;
    public Button btnAdd;
    public Button btnCancel;

    public AddEditUserController() {
        addEditCustomerController = new AddEditCustomerController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.textProperty().addListener((obs, oldValue, newValue) -> addEditCustomerController.validateInputs());
        txtEmail.textProperty().addListener((obs, oldValue, newValue) -> addEditCustomerController.validateInputs());
        txtPassword.textProperty().addListener((obs, oldValue, newValue) -> addEditCustomerController.validateInputs());

        addEditCustomerController.validateInputs();
    }

    public void editMode(User user) {
        txtName.textProperty().setValue(user.getName());
        txtEmail.textProperty().setValue(user.getEmail());
        txtPhone.textProperty().setValue(user.getPhone());
        txtPassword.textProperty().setValue(user.getPassword());
    }

    public void handleAdd() {

    }

    public void handleCancel() {
        addEditCustomerController.closeWindow();
    }
}
