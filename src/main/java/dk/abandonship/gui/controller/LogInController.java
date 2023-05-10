package dk.abandonship.gui.controller;

import dk.abandonship.gui.model.LoginModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML private TextField fieldEmail;
    @FXML private PasswordField fieldPassword;
    @FXML private Label lblError;
    private LoginModel model;
    private final ControllerAssistant controllerAssistant;

    public LogInController() {
        controllerAssistant = ControllerAssistant.getInstance();
        LoggedInUserState.getInstance().setLoggedInUser(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new LoginModel();
    }

    public void handleLogIn() {
        try {
            boolean success = model.logIn(fieldEmail.getText(), fieldPassword.getText());


            if(!success) lblError.setText("Wrong username or password");

            if(success){
                controllerAssistant.setTopFX("NavBar");
                controllerAssistant.setCenterFX("projectsView");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error logging in!");
        }
    }
}
