package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    public Button openAddUserWindowButton;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        ObservableList<User> users = FXCollections.observableArrayList(
                new User(0, "Mærsk", "mærsk@mærsk.dk", "88888888", "1234", new Timestamp(1)),
                new User(0, "Møller", "mærsk@møller.dk", "00000000", "1234", new Timestamp(1)));

        userTable.setItems(users);
    }
    
    public void openNewUserWindow() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/NewUser.fxml"));
        Parent root = loader.load();
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();
    }
}
