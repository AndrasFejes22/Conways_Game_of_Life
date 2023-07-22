package conway;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    private boolean alive = false;

    public Cell(){
        super(20, 20, Color.WHITE);
    }

    public boolean isAlive(){
        return alive;
    }

    public void flip(){
        alive = !alive;
        if(alive){
            setFill(Color.BLACK);
        } else {
            setFill(Color.WHITE);
        }
    }

    public void reset(){
        alive = false;
        setFill(Color.WHITE);
    }
}
