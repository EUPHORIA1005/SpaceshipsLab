package sample;

import javafx.scene.shape.Polygon;

public class Projectile extends Character{



    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
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
