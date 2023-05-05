/*
Name: Isaiah Moore, Roshni Patel, Zaid Barakat
Date: 5-3-23
Project: this program is an Hangman style game that takes a word form an api and the player needs
to solve it
*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("simple.fxml")));
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
