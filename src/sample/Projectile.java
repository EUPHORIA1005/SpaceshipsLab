package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Projectile extends Character{



    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x + 5   ,  y);
        super.getCharacter().setFill(Color.SKYBLUE);
        super.getCharacter().setStroke(Color.BLUE);
        super.getCharacter().setStrokeWidth(0.15);
    }

    @Override
    public void accelerate(){
        super.setMovement(super.getMovement().add(0, -2));
    }

    @Override
    public boolean isAlive(){
        if (super.getTranslateY() < 0){
            return false;
        }
        else
            return true;
    }


}
