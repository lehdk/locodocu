package dk.abandonship.gui.controller;

import dk.abandonship.businesslogic.LoginManager;
import dk.abandonship.dataaccess.RoleDatabaseDAO;
import dk.abandonship.dataaccess.UserDatabaseDAO;
import dk.abandonship.dataaccess.interfaces.IRoleDAO;
import dk.abandonship.dataaccess.interfaces.IUserDAO;
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

    public LogInController() {
        IRoleDAO roleDAO = new RoleDatabaseDAO();
        IUserDAO userDAO = new UserDatabaseDAO(roleDAO);

        loginManager = new LoginManager(userDAO);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleLogIn() {
        try {
            boolean success = loginManager.login(fieldEmail.getText(), fieldPassword.getText());

            if(!success) {
                lblError.setText("Invalid username or password");
            }

            // TODO: Redirect to logged in page
        } catch (SQLException e) {
            e.printStackTrace();
            lblError.setText("Error logging in!");
        }
    }
}
