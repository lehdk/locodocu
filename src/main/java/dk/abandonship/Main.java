package dk.abandonship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/view/Index.fxml")));

        primaryStage.setTitle("Locodocu");

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/icon.png"))));

        primaryStage.show();
    }
}