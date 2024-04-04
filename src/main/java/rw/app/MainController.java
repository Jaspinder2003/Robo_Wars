/**
 * Name: Jaspinder Singh Maan
 * Tutorial : TUT 15
 * UCID: 30209953
 */

package rw.app;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import rw.battle.Battle;
import javafx.stage.Stage;
import rw.battle.Entity;
import rw.battle.Maximal;
import rw.battle.PredaCon;
import rw.enums.WeaponType;
import rw.util.Reader;
import rw.util.Writer;

import java.io.File;
import java.util.Optional;
import java.util.Set;

public class MainController {

    //Store the data of editor
    private Battle battle;

    /**
     * Set up the window state
     */
    @FXML
    public void initialize() {

        weaponbox.getItems().addAll("Claws", "Teeth", "Laser");

    }

    /**
     * used to connect suing ids
     * add method to action to connect
     */
    @FXML
    private TextField rownumber;

    @FXML
    private TextField columnnumber;
    @FXML
    private TextField Predsymbol;
    @FXML
    private TextField Predname;
    @FXML
    private TextField Predhealth;
    @FXML
    private TextField MaxiHealth;
    @FXML
    private TextField Maxisymbol;
    @FXML
    private TextField Maxiname;
    @FXML
    private TextField MaxiArmour;
    @FXML
    private TextField Maxiweapon;
    @FXML
    private Button BattleButton;
    @FXML
    private MenuItem saveo;
    @FXML
    private MenuItem SaveAso;
    @FXML
    private MenuItem openo;
    @FXML
    private MenuItem quito;
    @FXML
    private MenuItem abouto;

    @FXML
    private ScrollPane gridScrollPane;
    @FXML
    private Button newButton;
    @FXML
    private RadioButton Predbutton;
    @FXML
    private RadioButton Maxibutton;
    @FXML
    private Button showDetailButton;


    @FXML
    private ComboBox weaponbox;

    @FXML
    private TextArea infoViewerTextArea;

    @FXML
    private Label leftStatusLabel;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * File Loader used to load files(open files)
     * Extension is used to create restriction
     *
     */
    @FXML
    protected void fileLoader() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Battle File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt")
        );

        // Pre-populate the weapon combo box only once to avoid duplicates
        if (weaponbox.getItems().isEmpty()) {
            weaponbox.getItems().addAll("Claws", "Teeth", "Laser");
        }

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            loadBattleFromFile(file);
            // Automatically prepare and display the new grid after loading file.
            prepareAndDisplayGrid();
        }
    }

    /**
     * used to  create a grid automatically while loading
     * it is further used in fileLoader
     */
    private void prepareAndDisplayGrid() {
        int rows = Integer.parseInt(rownumber.getText());
        int columns = Integer.parseInt(columnnumber.getText());
        clearBattleGrid(); // Clear the existing grid first.
        GridPane battleGrid = prepareBattleGrid(rows, columns);
        gridScrollPane.setContent(battleGrid);
    }

    /**
     * this actually loads battle and creates the grid and updates entity
     * @param file
     */
    private void loadBattleFromFile(File file) {
        battle = Reader.loadBattle(file);
        rownumber.setText(String.valueOf(battle.getRows()));
        columnnumber.setText(String.valueOf(battle.getColumns()));

        for (int row = 0; row < battle.getRows(); row++) {
            for (int column = 0; column < battle.getColumns(); column++) {
                Entity entity = battle.getEntity(row, column);
                if (entity != null) {
                    updateEntityDetails(entity);
                }
            }
        }
    }

    /**
     * updates entities
     * @param entity
     */
    private void updateEntityDetails(Entity entity) {
        if (entity instanceof PredaCon) {
            updatePredaConDetails((PredaCon) entity);
        } else if (entity instanceof Maximal) {
            updateMaximalDetails((Maximal) entity);
        }
    }

    /**
     * updates Predacon details
     * @param pred
     */
    private void updatePredaConDetails(PredaCon pred) {
        Predsymbol.setText(String.valueOf(pred.getSymbol()));
        System.out.println(pred.getName());
        Predname.setText(pred.getName());
        Predhealth.setText(String.valueOf(pred.getHealth()));

        String weaponChoice = switch (pred.getWeaponType()) {
            case CLAWS -> "Claws";
            case TEETH -> "Teeth";
            case LASER -> "Laser";
            default -> "";
        };
        weaponbox.getSelectionModel().select(weaponChoice);
    }

    /**
     * updates Maximal detailis
     * @param maximal
     */
    private void updateMaximalDetails(Maximal maximal) {
        Maxisymbol.setText(String.valueOf(maximal.getSymbol()));
        Maxiname.setText(maximal.getName());
        MaxiHealth.setText(String.valueOf(maximal.getHealth()));
        Maxiweapon.setText(String.valueOf(maximal.weaponStrength()));
        MaxiArmour.setText(String.valueOf(maximal.armorStrength()));

        try {
            Integer.parseInt(String.valueOf(maximal.weaponStrength()));
            Integer.parseInt(String.valueOf(maximal.armorStrength()));

        } catch (NumberFormatException e) {
            updateLeftStatusLabel("Invalid Weapon or Armour Strenght");
            return ;
        }



    }

    /**
     * creates new grid completely which is also empty
     */
    @FXML
    private void createNewBattle() {
        clearBattleGrid(); // Clears any existing grid content
        int numberOfRows = getNumberOfRows();
        int numberOfColumns = getNumberOfColumns();

        if(numberOfRows < 0 || numberOfColumns <0){
            updateLeftStatusLabel("Invalid number of Row or column");
            return;
        }





        GridPane battleGrid = new GridPane();

        for (int i = 0; i < numberOfRows + 2; i++) {
            for (int j = 0; j < numberOfColumns + 2; j++) {
                TextField cell = new TextField();
                cell.setPrefWidth(30);
                cell.setMinSize(20, 20);

                if (isBorderCell(i, j, numberOfRows, numberOfColumns)) {
                    cell.setText("#");
                } else {
                    cell.setText(".");
                }

                battleGrid.add(cell, j, i);
            }
        }

        gridScrollPane.setContent(battleGrid);
    }

    /**
     * clears battlegrid
     */
    private void clearBattleGrid() {
        gridScrollPane.setContent(null);
    }

    private int getNumberOfRows() {
        return Integer.parseInt(rownumber.getText());
    }//gets number of row

    private int getNumberOfColumns() {
        return Integer.parseInt(columnnumber.getText());
    }//gets number of columns

    private GridPane prepareBattleGrid(int rows, int columns) {
        GridPane battleGrid = new GridPane();
        for (int i = 0; i < rows + 2; i++) {
            for (int j = 0; j < columns + 2; j++) {
                TextField cell = createBattleCell(i, j, rows, columns);
                battleGrid.add(cell, j, i); // Adds the cell to the battleGrid at the specified column and row
            }
        }
        return battleGrid;
    }//used to preapre battle grid and add items to it but does not create it completely

    private TextField createBattleCell(int row, int column, int totalRows, int totalColumns) {
        TextField cell = new TextField();
        cell.setPrefWidth(30);
        cell.setMinSize(20, 20);

        // Determine the content of the cell based on its position
        if (isBorderCell(row, column, totalRows, totalColumns)) {
            cell.setText("#");
        } else {
            Entity entity = battle.getEntity(row - 1, column - 1);
            cell.setText(entity != null ? String.valueOf(entity.getSymbol()) : ".");
        }

        return cell;
    }//creates a textfield cell

    private boolean isBorderCell(int row, int column, int totalRows, int totalColumns) {
        return column == totalColumns + 1 || column == 0 || row == 0 || row == totalRows + 1;
    }//used to check if the cell in question is at the border

    /**
     * i use this to add and dlete objects
     * this method also creates a popup window with alerts
     */
    @FXML
    private void objectModifier() {
        int a=Integer.parseInt(rownumber.getText());
        int b=Integer.parseInt(columnnumber.getText());
        battle=new Battle(a,b);
        // Create the custom dialog for grid location input
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Robot Location Modifier");
        dialog.setHeaderText("Please enter the row and column where you want to modify a robot:");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OTHER);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField rowField = new TextField();
        rowField.setPromptText("Row");
        TextField columnField = new TextField();
        columnField.setPromptText("Column");
        grid.add(new Label("Row:"), 0, 0);
        grid.add(rowField, 1, 0);
        grid.add(new Label("Column:"), 0, 1);
        grid.add(columnField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(rowField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return "Add";
            } else if (dialogButton == deleteButtonType) {
                return "Delete";
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(action -> {
            try {
                int row = Integer.parseInt(rowField.getText());
                int column = Integer.parseInt(columnField.getText());
                if ("Add".equals(action)) {
                    if (Predbutton.isSelected() || Maxibutton.isSelected()) {
                        System.out.println(row + " -------------------- "+ column);
                        addOrDeleteRobot(row, column, Predbutton.isSelected(), true); // true for add
                    }
                } else if ("Delete".equals(action)) {
                    addOrDeleteRobot(row, column, false, false); // false, false for delete
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numeric values for row and column.");
            }
        });
    }

    /**
     * actually used to add or delete objects the object is deleted visually as it
     * it is not possible to delete it from Battle object as there is no predefined functionn
     * @param row
     * @param column
     * @param isPredaCon
     * @param isAdd
     */
    private void addOrDeleteRobot(int row, int column, boolean isPredaCon, boolean isAdd) {

        if (isAdd) {
            Entity newEntity = null;
            // Validate input fields before entity creation
            if (isPredaCon) {

                char symbol = Predsymbol.getText().charAt(0);
                String name = Predname.getText();
                int health = Integer.parseInt(Predhealth.getText());
                String weaponTypeStr = weaponbox.getSelectionModel().getSelectedItem().toString();
                WeaponType weaponType = WeaponType.valueOf(weaponTypeStr.toUpperCase()); // Adjusted for direct use

                newEntity = new PredaCon(symbol, name, health, weaponType);


            } else { // For Maximal
                char symbol = Maxisymbol.getText().charAt(0);
                String name = Maxiname.getText();
                int health = Integer.parseInt(MaxiHealth.getText());
                int weaponStrength = Integer.parseInt(Maxiweapon.getText());
                int armourStrength = Integer.parseInt(MaxiArmour.getText());

                newEntity = new Maximal(symbol, name, health, weaponStrength, armourStrength);
            }


            if (newEntity != null && battle != null) {
                battle.addEntity(row, column, newEntity); // Adjusted for correct indexes if necessary
                updateUIWithNewEntity(row, column, newEntity); // Implemented this method to update UI
            }
            System.out.println(battle);

        } else {
            visuallyClearCell(row - 1, column - 1);//visually deletes the object
        }

        prepareAndDisplayGrid();
    }

    /**
     * shows alert if there are any
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void visuallyClearCell(int row, int column) {
        // Assuming row and column are 0-based indexes and there's a border around the grid,
        // you might need to adjust these indexes when accessing the GridPane directly.
        int gridRow = row + 1; // Adjusting because of a potential border or header in your grid layout
        int gridColumn = column + 1; // Similar adjustment as above

        // Find the TextField in the specified cell of the GridPane and clear its text
        for (Node node : gridScrollPane.getContent().lookupAll(".text-field")) { // Assuming CSS class selector
            if (GridPane.getRowIndex(node) == gridRow && GridPane.getColumnIndex(node) == gridColumn && node instanceof TextField) {
                ((TextField)node).setText(".");
                break; // Exit the loop once the correct cell is found and updated
            }
        }
    }
    private void updateUIWithNewEntity(int row, int column, Entity entity) {
        // This will run the UI update on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Directly use the Set<Node> returned by lookupAll
            Set<Node> children = gridScrollPane.getContent().lookupAll(".text-field"); // Ensure the selector matches your TextField CSS class if you have one.

            for (Node node : children) {
                // GridPane.getRowIndex and getColumnIndex might return null, so we need to handle that case
                Integer nodeRow = GridPane.getRowIndex(node);
                Integer nodeColumn = GridPane.getColumnIndex(node);

                // Adjust row and column indices as necessary, depending on how they're set up in your GridPane
                if (nodeRow != null && nodeColumn != null && nodeRow == row+1 && nodeColumn == column+1 && node instanceof TextField) {
                    char symbol = entity.getSymbol(); // Assuming a getSymbol method exists for all entities
                    String s=String.valueOf(symbol);
                    ((TextField) node).setText(s);
                    break; // Exit once the correct cell is found and updated
                }
            }
        });
    }

    // Method to append lines to the TextArea
    private void appendLinesTotextarea(String line) {

        infoViewerTextArea.appendText(line + "\n"); // Appends each line with a newline character


    }

    /**
     * this is used to display details about an object
     */
    @FXML
    private void ObjectViewer() {
        // Assuming the declaration of battle somewhere in your class



        // Create the custom dialog for grid location input
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Object information viewer");
        dialog.setHeaderText("Please enter the row and column:");

        // Set the button type.
        ButtonType modifyButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(modifyButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField rowField = new TextField();
        rowField.setPromptText("Row");
        TextField columnField = new TextField();
        columnField.setPromptText("Column");

        grid.add(new Label("Row:"), 0, 0);
        grid.add(rowField, 1, 0);
        grid.add(new Label("Column:"), 0, 1);
        grid.add(columnField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(rowField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == modifyButtonType) {

                int row = Integer.parseInt(rowField.getText());
                int column = Integer.parseInt(columnField.getText());

                Entity entity = battle.getEntity(row, column);

                System.out.println(battle);


                if (entity != null) {
                    // Set common properties for all entities.

                    if (entity instanceof PredaCon) {
                        PredaCon pred = (PredaCon) entity;
                        infoViewerTextArea.setText("");
                        appendLinesTotextarea(pred.getName());
                        appendLinesTotextarea(String.valueOf(pred.getHealth()));
                        appendLinesTotextarea(String.valueOf(entity.getSymbol()));
                        appendLinesTotextarea(String.valueOf(weaponbox.getValue()));


                        //appendLinesTotextarea();

                        weaponbox.getSelectionModel().select(pred.getWeaponType().toString());

                    } else if (entity instanceof Maximal) {
                        Maximal maximal = (Maximal) entity;

                        appendLinesTotextarea(maximal.getName());
                        infoViewerTextArea.setText("");
                        appendLinesTotextarea(String.valueOf(maximal.getHealth()));
                        appendLinesTotextarea(String.valueOf(maximal.getSymbol()));
                        appendLinesTotextarea(String.valueOf(maximal.armorStrength()));
                        appendLinesTotextarea(String.valueOf(maximal.weaponStrength()));


                    }
                }

                return "Submit";
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

    }

    @FXML
    private void AboutBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Robot Wars World Editor V1.1"); // Title of the application
        alert.setHeaderText("About Robot Wars World Editor"); // More descriptive header
        alert.setContentText(
                "Author: Jaspinder Singh Maan\n" +
                        "Email: JaspinderSingh.Maan@ucalgary.ca\n" +
                        "Version: 1.1\n\n" +
                        "This is a battle editor for a game. Designed to provide a user-friendly " +
                        "interface for creating and editing battle scenarios."
        );
        alert.showAndWait();
    }

    @FXML
    private void updateLeftStatusLabel(String message) {
        leftStatusLabel.setText(message);
    }
    @FXML
    private void quitApplication() {
        Platform.exit();
    }

    private File currentFile = null;

    /**
     * Handles both "Save" and "Save As" actions.
     * @param askPath Whether to ask the user for a file path or not.
     */
    private void save(boolean askPath) {
        if (askPath || currentFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );

            // Here we present the Save File dialog to the user.
            File selectedFile = fileChooser.showSaveDialog(stage);

            // If the user selects a file, we update the currentFile.
            // If they cancel, we simply return without doing anything.
            if (selectedFile != null) {
                currentFile = selectedFile;
            } else {
                return;
            }
        }

        if (currentFile != null) {

            // Assuming your Writer.saveBattle method throws IOException.
            // The actual method call to save the battle configuration.
            Writer.saveBattle(battle, currentFile);

        }
    }

    @FXML
    private void handleSave() {
        save(false); // Pass false to "save" indicating that we do not wish to ask the user for a new file path.
    }

    @FXML
    private void handleSaveAs() {
        save(true); // Pass true to "save as" indicating we wish to ask the user for a new file path.
    }}

// Utility method to show an alert dialog with a message




