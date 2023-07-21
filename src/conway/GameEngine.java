package conway;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Runnable{

    private final Cell[][] cells;

    public GameEngine(Cell[][] cells) {
        this.cells = cells;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("Calculating");
            // counting
            List<Cell> cellsToUpdate = calculateCellUpdates();
            // UI update
            Platform.runLater(() -> cellsToUpdate.forEach(cell -> cell.flip()));
            // sleep : 0.5 sec
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private List<Cell> calculateCellUpdates() {
        List<Cell> cellsToUpdate = new ArrayList<>();
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[row].length; column++) {
                Cell cell = cells[row][column];
                int livingNeighbours = countLivingNeighbours(row, column);
                if(cell.isAlive()){
                    if(livingNeighbours < 2 || livingNeighbours > 3){ //Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                        // || Any live cell with more than three live neighbours dies, as if by overpopulation.
                        cellsToUpdate.add(cell);
                    }
                } else { //dead cell
                    if(livingNeighbours == 3){ // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                        cellsToUpdate.add(cell);
                    }
                }

            }
        }
        return cellsToUpdate;
    }


    private int countLivingNeighbours(int row, int column) {
        int liveCount = 0;
        //this circles a cell in row, column:
        for (int rowOffset = -1; rowOffset <= 1 ; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                int rowToCheck = row + rowOffset;
                int columnToCheck = column + columnOffset;
                if(isSelf(rowOffset, columnOffset) || isOutOfBounds(rowToCheck, columnToCheck)){
                    continue;
                }
                if(cells[rowToCheck][columnToCheck].isAlive()){
                    liveCount++;
                }
            }
        }
        return liveCount;
    }

    //to be checked: 1: cell in the corner of the grid: (do not address out of the cell), 2: the cell itself should be ignored

    private static boolean isSelf(int rowOffset, int columnOffset){
        return rowOffset == 0 && columnOffset == 0;
    }

    private boolean isOutOfBounds(int rowToCheck, int columnToCheck){
        return rowToCheck < 0 || rowToCheck >= cells.length || columnToCheck < 0 || columnToCheck >= cells[0].length;
    }
}
