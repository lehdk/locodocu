package dk.abandonship.gui.controller;

import dk.abandonship.businesslogic.LoginManager;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
import dk.abandonship.gui.model.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    private final LoginManager loginManager;

    @FXML private TextField fieldEmail;
    @FXML private PasswordField fieldPassword;
    @FXML private Label lblError;
    private LoginModel model;

    public LogInController() {

        loginManager = new LoginManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new LoginModel();
    }

    public void handleLogIn() {
        try {
            lblError.setText(model.logIn(fieldEmail.getText(), fieldPassword.getText()));

            // TODO: Redirect to logged in page
        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error logging in!");
        }
    }
}
