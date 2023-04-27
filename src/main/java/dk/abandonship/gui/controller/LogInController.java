package dk.abandonship.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML private PasswordField fieldPassword;
    @FXML private TextField fieldUserName;
    @FXML private Button btnLogIn;
    @FXML private Label lblError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleLogIn(ActionEvent actionEvent) {
        lblError.setText("Invalid username or password");
    }
}
