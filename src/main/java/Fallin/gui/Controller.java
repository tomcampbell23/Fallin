package Fallin.gui;

import Fallin.engine.Cell;
import Fallin.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * The Controller class manages the GUI components and user interactions for the game.
 */
public class Controller {
    @FXML
    private GridPane gridPane;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private TextField difficultyField;
    @FXML
    private Button startButton;
    @FXML
    private TextArea outputArea;
    @FXML
    private Button helpButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    @FXML
    private TextField stepsField;
    @FXML
    private TextField stepLimit;
    @FXML
    private TextField treasureField;
    @FXML
    private TextField healthField;
    @FXML
    private TextArea topScores;
    @FXML
    private TextField scoreField;
    private GameEngine engine;
    private Boolean playing = false;
    private String output = "";
    private final int gridSize = 60;

    /**
     * Initialises the controller and sets up event handlers for the buttons.
     */
    @FXML
    public void initialize() {
        outputArea.setWrapText(true);
        if (engine == null) {
            updateOutput("Please start the game");
        } else {
            setMovementAction(upButton, "u");
            setMovementAction(downButton, "d");
            setMovementAction(leftButton, "l");
            setMovementAction(rightButton, "r");
            helpButton.setOnAction((e) -> {
                output = engine.help() + "\n";
                updateGui();
            });
            saveButton.setOnAction((e) -> {
                output = engine.save() + "\n";
                updateGui();
            });
            loadButton.setOnAction((e) -> {
                output = engine.load() + "\n";
                difficultyField.setText(String.valueOf(engine.getDifficulty()));
                updateGui();
            });
        }

        startButton.setOnAction((e) -> {
            try {
                int difficulty = Integer.parseInt(difficultyField.getText());
                if (difficulty >= 0 && difficulty <= 10) {
                    output = "The difficulty is " + difficulty + ".";
                    playing = true;
                    engine = new GameEngine(difficulty, 10, 100);
                    initialize();
                    updateGui();
                } else {
                    updateOutput("Please enter a number between 0-10 in the difficulty field above");
                }
            } catch (NumberFormatException var3) {
                if (difficultyField.getText().isEmpty()) {
                    output = "The difficulty is 5.";
                    playing = true;
                    engine = new GameEngine(5, 10, 100);
                    difficultyField.setText("5");
                    initialize();
                    updateGui();
                } else {
                    updateOutput("Please enter a valid number between 0-10 in the difficulty field above");
                }
            }
        });
    }

    /**
     * Sets the action event handler for a given movement button.
     *
     * @param button    The Button to which the action event handler will be assigned.
     * @param direction The direction of the player's movement. This should be a string representing
     *                  the direction (e.g., "u" for up, "d" for down, "l" for left, "r" for right).
     */
    private void setMovementAction(Button button, String direction) {
        button.setOnAction((e) -> {
            output = "Your move " + engine.playerInteraction(direction) + "\n";
            if (!output.contains("unsuccessful")) {
                output += engine.moveEnemies();
            }
            updateGui();
        });
    }

    /**
     * Updates the GUI components to reflect the current game state.
     */
    private void updateGui() {
        if (playing) {
            String checkState = engine.checkState();
            if (checkState != null) {
                output = output + checkState + "\nPress the start game button to start a new game.";
                playing = false;
            }

            // Clear old GUI grid pane
            gridPane.getChildren().clear();

            if (checkState != null && checkState.contains("Game over!")) {
                scoreField.setText("-1");
            } else {
                scoreField.setText(Integer.toString(engine.getScore()));
            }
            topScores.setText(engine.displayTopScores());
            outputArea.setText(output);
            stepsField.setText(Integer.toString(engine.getSteps()));
            stepLimit.setText(Integer.toString(engine.getStepLimit()));
            treasureField.setText(Integer.toString(engine.getCollectedTreasures()));
            healthField.setText(Integer.toString(engine.getPlayer().getHealth()));
            output = "";
            gridPane.setGridLinesVisible(false);

            // Loop through game map and add each cell into grid pane
            for (int i = 0; i < engine.getSize(); i++) {
                for (int j = 0; j < engine.getSize(); j++) {
                    Cell cell = engine.getMap()[i][engine.getSize() - 1 - j];
                    Image image;
                    if (cell.getPlayer()) {
                        image = new Image("file:src/main/resources/Player.png");
                    } else {
                        image = new Image(cell.getType().getImage());
                    }
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(gridSize);
                    imageView.setFitWidth(gridSize);
                    gridPane.add(imageView, i, j);
                }
            }

            gridPane.setGridLinesVisible(true);
            gridPane.setStyle("-fx-background-image: url('file:src/main/resources/Wasteland_background.png')");
        }
    }

    /**
     * Updates the output area with the given text.
     *
     * @param text the text to display in the output area.
     */
    private void updateOutput(String text) {
        outputArea.setText(text);
    }
}