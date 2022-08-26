package hu.unideb.inf.foxandhounds.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.unideb.inf.foxandhounds.ExcludeFromGeneratedReport;
import hu.unideb.inf.foxandhounds.game.FoxGame;
import hu.unideb.inf.foxandhounds.game.Vector2;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.tinylog.Logger;

import java.io.*;
import java.util.List;
import java.util.Optional;

/**
 * A grafikus felületet vezérlő osztály.
 * Felelős a grafikus felület felépítéséért, helyes megjelenítéséért
 * és a JSon importálásáért és exportálásáért
 */
@ExcludeFromGeneratedReport
public class GameManager {
    private FoxGame game;
    private int fromX;
    private int fromY;

    private GameData data;

    @FXML
    private GridPane grid;

    @FXML
    private Label turn;

    @FXML
    private Label stats;

    @FXML
    private Button newGame;

    @FXML
    private Button save;

    @FXML
    private Button load;

    @FXML
    private void initialize() {
        int length = 8;
        int width = (int) grid.getWidth();
        data = new GameData();
        data.houndsWins = 0;
        data.foxWins = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                StackPane stackPane = new StackPane();
                stackPane.setId("grid-" + i + "-" + j);
                stackPane.setPrefSize(width / length, width / length);
                stackPane.setOnMouseClicked(this::handleMouseClick);
                grid.add(stackPane, i, j);
            }
        }
        newGame.setOnAction(event -> resetGame());
        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Játék Mentés");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSon", "*.json"));
            fileChooser.setInitialFileName("game.json");
            File selectedFile = fileChooser.showSaveDialog(null);
            try {
                if (selectedFile != null) {
                    try (FileWriter fileWriter = new FileWriter(selectedFile)) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        gson.toJson(data, fileWriter);
                    }
                    Logger.debug("Játék elmentve: " + selectedFile.getAbsolutePath() + "\n" +
                            data.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        load.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Játék Betöltés");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSon", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(null);
            try {
                Gson gson = new Gson();
                if(selectedFile != null) {
                    data = gson.fromJson(new FileReader(selectedFile), GameData.class);
                    Logger.debug("Játék betöltve: " + selectedFile.getAbsolutePath() + "\n" +
                            data.toString());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Hibás adat!", ButtonType.OK);
                alert.showAndWait();
            }
            updateStatistics();
        });
        resetGame();
    }

    private void resetGame() {
        Logger.debug("Játék újraindítása");

        game = new FoxGame();
        recolorChessBoard();
        resetImages(game.getBoard().length);
        fromX = -1;
        fromY = -1;

        updateStatistics();
        loop();
    }

    private void resetImages(int length) {
        Logger.debug("Képek frissítése");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                StackPane stackPane = (StackPane) grid.getChildren().get(i * length + j);
                stackPane.getChildren().clear();
                if(game.getBoard()[i][j] == FoxGame.EMPTY) continue;

                ImageView imageView = new ImageView(getClass().getResource("/" + (game.getBoard()[i][j] == FoxGame.FOX ? "roka" : "kutya") + ".png").toString());
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.maxWidth(50);
                imageView.maxHeight(50);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                imageView.setId(String.valueOf(i * length + j));
                stackPane.getChildren().add(imageView);
            }
        }
    }

    private void recolorChessBoard() {
        Logger.debug("Sakktábla újraszínezése");
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                StackPane stackPane = (StackPane) grid.getChildren().get(i * 8 + j);
                stackPane.setStyle("-fx-background-color: " + (i % 2 == j % 2 ? "white" : "black"));
            }
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if(game.won) return;
        Node source = (Node) event.getSource();
        int i = GridPane.getColumnIndex(source);
        int j = GridPane.getRowIndex(source);
        Logger.debug("Kattintás: " + i + ", " + j + " cella");
        Logger.debug(game.getBoard()[i][j] + ", " + game.turn);
        if(game.getBoard()[i][j] == FoxGame.EMPTY) {
            Logger.debug("Lépés: " + i + ", " + j);
            if(fromX == -1) return;
            List<Vector2> vectors = game.getPossiblePositions(fromX, fromY);
            if(vectors.contains(new Vector2(i, j))) {
                game.movePlayer(fromX, fromY, i, j);
            }

            recolorChessBoard();
            resetImages(game.getBoard().length);
            fromX = -1;
            fromY = -1;
            loop();
            return;
        }
        if (game.getBoard()[i][j] != game.turn) return;
        List<Vector2> vectors = game.getPossiblePositions(i, j);
        recolorChessBoard();
        fromX = i;
        fromY = j;
        Logger.debug("Átszínezés");
        for (Vector2 vector : vectors) {
            StackPane stackPane = (StackPane) grid.lookup("#grid-" + vector.x() + "-" + vector.y());
            stackPane.setStyle("-fx-background-color: #55a555");
        }
    }

    private void loop() {
        game.checkWinCondition();

        turn.setText("Következik:"
                + "\n" + (game.turn == FoxGame.FOX ? "Róka" : "Kutya"));

        if(!game.won) return;

        if(game.winner == FoxGame.FOX) {
            data.foxWins++;
        } else {
            data.houndsWins++;
        }

        Logger.debug("Játék vége, " + (game.winner == FoxGame.FOX ? "Róka" : "Kutya") + " nyert");

        updateStatistics();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Játéknak Vége");
        alert.setHeaderText(null);
        alert.setContentText("A játék vége!\nNyertes: " + (game.winner == FoxGame.FOX ? "Róka" : "Kutya"));
        ButtonType buttonNewGame = new ButtonType("Új Játék");
        ButtonType buttonRageQuit = new ButtonType("Rage Quit");
        alert.getButtonTypes().setAll(buttonNewGame, buttonRageQuit);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonNewGame) {
            resetGame();
        } else if (result.get() == buttonRageQuit) {
            System.exit(0);
        }
    }

    private void updateStatistics() {
        stats.setText("Statisztika:"
                + "\nRóka: " + data.foxWins
                + "\nKutya: " + data.houndsWins);
    }
}
