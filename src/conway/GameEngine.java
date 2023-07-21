package conway;

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
            // UI update
            // sleep : 0.5 sec
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
