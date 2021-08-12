package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Ship extends Character  {


    private long lastShot;

    public Ship(int x, int y) {
        super(new Polygon(-10, -10, 20, 0, -10, 10), x, y);
        super.getCharacter().setFill(Color.DARKGRAY);
        super.getCharacter().setStroke(Color.BLACK);
        super.getCharacter().setStrokeWidth(0.7);
        super.getCharacter().setOpacity(0);
    }

    public boolean canShoot(){
        if (System.currentTimeMillis() - lastShot > 200){
            return true;
        }
        else{
            return false;
        }
    }

    public void setLastShot(long lastShot){
        this.lastShot = lastShot;
    }

    public long getLastShot() {
        return lastShot;
    }
}
