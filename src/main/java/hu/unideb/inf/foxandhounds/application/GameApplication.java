package hu.unideb.inf.foxandhounds.application;

import hu.unideb.inf.foxandhounds.ExcludeFromGeneratedReport;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Grafikus felület megjelenítéséért felelős osztály.
 */
@ExcludeFromGeneratedReport
public class GameApplication extends Application {
    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui.fxml")));
        stage.setTitle("Rókafogó");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
