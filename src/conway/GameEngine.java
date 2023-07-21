package conway;

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
            // UI update
            // sleep : 0.5 sec
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private int countLivingNeighbours(int row, int column) {
        return 0;
    }
}
