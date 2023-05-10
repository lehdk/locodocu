package dk.abandonship.gui.controller;

import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {

    @FXML private HBox navBar;
    private ControllerAssistant controllerAssistant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();

        createButtons();
    }

    private void createButtons() {

        navBar.setSpacing(20);

        Button btn1 = new Button("Project");
        btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> projects());

        navBar.getChildren().add(btn1);

        navBar.setSpacing(20);

        Button customerButton = new Button("Customers");
        customerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                controllerAssistant.setCenterFX("CustomerView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        navBar.getChildren().add(customerButton);

        Button userButton = new Button("Users");
        userButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                controllerAssistant.setCenterFX("UsersView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        navBar.getChildren().add(userButton);

        Button logoutButton = new Button("Log out");
        logoutButton.setOnAction(event -> {
            try {
                controllerAssistant.setTopFX(null);
                controllerAssistant.setCenterFX("LogIn");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        navBar.getChildren().add(logoutButton);

    }

    private void projects() {
        try {
            controllerAssistant.setCenterFX("projectsView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void users() {
        try {
            controllerAssistant.setCenterFX("UsersView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
