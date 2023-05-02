package dk.abandonship.gui.controller;

import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.ControllerAssistant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NavBarController implements Initializable {


    @FXML private HBox navBar;
    private ControllerAssistant controllerAssistant;
    private LoggedInUserState userState;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerAssistant = ControllerAssistant.getInstance();
        userState = LoggedInUserState.getInstance();

        createButtons(userState);
    }

    private void  createButtons(LoggedInUserState userState) {

        navBar.setSpacing(20);

        if (userState != null){
            Button btn1 = new Button("Project");
            btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> projects());

            navBar.getChildren().add(btn1);

            navBar.setSpacing(20);
        }

        if (userState.getLoggedInUser() != null){
            Button btn1 = new Button("test");
            btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> test());

            navBar.getChildren().add(btn1);

            navBar.setSpacing(20);
        }

        Button customerButton = new Button("Customers");
        customerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                controllerAssistant.setCenterFX("CustomerView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        navBar.getChildren().add(customerButton);


    }



    private void projects() {
        try {
            controllerAssistant.setCenterFX("projectsView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() {
        try {
            controllerAssistant.setCenterFX("LogIn");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
