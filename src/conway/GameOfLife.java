package conway;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class GameOfLife extends Application {

    private Cell[][] cells;
    private int numOfRows = 30;
    private int numOfColumns = 30;

    // buttons
    private Button startButton;
    private Button clearButton;
    private Button randomButton;
    private Button stepButton;

    private Thread worker = null;
    private GameEngine engine = null;

    private Parent createContent() {
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(numOfColumns);
        tilePane.setPrefRows(numOfRows);
        tilePane.setTileAlignment( Pos.CENTER );
        tilePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        tilePane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        tilePane.setHgap(1);
        tilePane.setVgap(1);
        //innentől nem jó
        tilePane.setBackground(Background.fill(Color.GRAY));
        tilePane.setBorder(Border.stroke(Color.GRAY));

        cells = new Cell[numOfRows][numOfColumns];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                Cell cell = new Cell();
                cells[i][j] = cell;
                tilePane.getChildren().add(cell);
            }
        }

        // create engine
        engine = new GameEngine(cells);

        tilePane.setOnMouseClicked(event -> tilePane.getChildren().stream().filter(cell -> cell.contains(cell.sceneToLocal(event.getSceneX(), event.getSceneY(), true)))
                .findFirst().ifPresent(cell ->((Cell)cell).flip()));

        //tilePane.setOnMouseClicked(this::handleTilePaneClick);
        //return tilePane;
        StackPane stackPane = new StackPane(tilePane);
        // Button handling
        startButton = new Button("Start");
        clearButton = new Button("Clear");
        randomButton = new Button("Random");
        stepButton = new Button("Step");
        startButton.setOnAction(this::handleStartButton);
        clearButton.setOnAction(this::handleClearButton);
        randomButton.setOnAction(this::handleRandomButton);
        stepButton.setOnAction(this::handleStepButton);
        // HBox
        HBox buttons = new HBox(startButton, clearButton, randomButton, stepButton);
        buttons.setSpacing(5);
        return new VBox(stackPane, buttons);
    }

    private void handleStepButton(ActionEvent event) {
        engine.step();
    }

    private void handleRandomButton(ActionEvent event) {
        Random random = new Random();
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                Cell cell = cells[row][column];
                if(random.nextInt(100) < 20){ // 20 % chance
                    cell.flip();
                }
            }

        }
    }

    private void handleClearButton(ActionEvent event) {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                Cell cell = cells[row][column];
                cell.reset();
            }
            
        }
    }

    private void handleStartButton(ActionEvent event) {
        if(worker == null){
            worker = new Thread((new GameEngine(cells)));
            worker.setDaemon(true);
            worker.start();
            startButton.setText("Stop");
            clearButton.setDisable(true);
            randomButton.setDisable(true);
            stepButton.setDisable(true);
        } else {
            worker.interrupt();
            worker = null;
            startButton.setText("Start");
            clearButton.setDisable(false);
            randomButton.setDisable(false);
            stepButton.setDisable(false);

        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

