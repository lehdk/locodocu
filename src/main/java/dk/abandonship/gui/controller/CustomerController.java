package dk.abandonship.gui.controller;

import dk.abandonship.Main;
import dk.abandonship.entities.Customer;
import dk.abandonship.entities.CustomerDTO;
import dk.abandonship.gui.controller.PopUpController.AddCustomerController;
import dk.abandonship.gui.model.CustomerModel;
import dk.abandonship.state.LoggedInUserState;
import dk.abandonship.utils.DefaultRoles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    private final CustomerModel customerModel;

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private HBox buttonsHBox;

    @FXML
    private VBox vbox;

    public CustomerController() {
        customerModel = new CustomerModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTableView.setItems(customerModel.getCustomerObservableList());

        buttonsHBox.setSpacing(10);

        if(LoggedInUserState.getInstance().hasRole(DefaultRoles.PROJECTMANAGER) || LoggedInUserState.getInstance().hasRole(DefaultRoles.SALESPERSON)) {
            Button addCustomerButton = new Button("Add Customer");
            addCustomerButton.setOnAction(event -> {
                try {
                    handleAddCustomer();
                } catch (IOException e) {
                    System.err.println("Could not open window!");
                    e.printStackTrace();
                }
            });
            buttonsHBox.getChildren().add(addCustomerButton);

            Button editCustomerButton = new Button("Edit Customer");
            buttonsHBox.getChildren().add(editCustomerButton);

            Button deleteCustomerButton = new Button("Delete Customer");
            buttonsHBox.getChildren().add(deleteCustomerButton);
        }
    }

    public void handleAddCustomer() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("gui/view/PopUps/AddCustomerView.fxml"));

        Parent root = loader.load();
        AddCustomerController controller = loader.getController();
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        popupStage.showAndWait();

        CustomerDTO result = controller.getResult();
        if(result == null) return;

        try {
            customerModel.addCustomer(result);
        } catch (SQLException e) {
            System.err.println("Could not add customer!");
            e.printStackTrace();
        }
    }
}
