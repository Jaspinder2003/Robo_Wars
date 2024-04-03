package rw.app;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rw.battle.Battle;
import rw.util.Reader;
import rw.util.Writer;

import java.io.File;
import java.io.IOException;

public class MainController {

    //Store the data of editor
    private Battle battle;

    /**
     * Set up the window state
     */
    @FXML
    public void initialize() {
    }
    @FXML
    private MenuBar menuBar; // Example if you're using a MenuBar for load/save

    private Stage primaryStage; // You'll need a reference to your primary stage

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void loadBattle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Battle File");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            battle = Reader.loadBattle(file);
            updateBattleView(); // You need to implement this method
        }
    }

    @FXML
    private void saveBattle() {
        if (battle == null) {
            // Show error or message indicating no battle to save
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Battle File");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                Writer.saveBattle(battle, file);
                // Show save success message
            } catch (IOException e) {
                // Handle exception, e.g., show error dialog
            }
        }
    }

    private void updateBattleView() {
        // Update your GUI to reflect the loaded battle
    }
}