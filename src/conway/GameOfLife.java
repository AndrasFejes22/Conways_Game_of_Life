package conway;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameOfLife extends Application {

    private Cell[][] cells;
    private int numOfRows = 30;
    private int numOfColumns = 30;

    private Thread worker = null;

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

        tilePane.setOnMouseClicked(event -> tilePane.getChildren().stream().filter(cell -> cell.contains(cell.sceneToLocal(event.getSceneX(), event.getSceneY(), true)))
                .findFirst().ifPresent(cell ->((Cell)cell).flip()));

        //tilePane.setOnMouseClicked(this::handleTilePaneClick);
        //return tilePane;
        StackPane stackPane = new StackPane(tilePane);

        Button startButton = new Button("Start");
        startButton.setOnAction(event ->{
            System.out.println("Start button clicked!");
            if(worker == null){
                worker = new Thread((new GameEngine(cells)));
                worker.setDaemon(true);
                worker.start();
                startButton.setText("Stop");
            } else {
                worker.interrupt();
                worker = null;
                startButton.setText("Start");
            }
        });
        HBox buttons = new HBox(startButton);
        return new VBox(stackPane, buttons);
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

