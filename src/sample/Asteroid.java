package sample;

import javafx.scene.shape.Polygon;

import java.util.Random;



public class Asteroid extends Character {
    private double rotationalMovement;


    public Asteroid(int x, int y) {
        super(new Polygon(20, -20, 20, 20, -20, 20, -20, -20), x, y);

        Random rnd = new Random();

        int accelerationAmount = 1 + rnd.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            accelerate();
        }

        this.rotationalMovement = 0.5 - rnd.nextDouble();

    }

    @Override
    public boolean isAlive(){
        if (super.getTranslateY() > Main.HEIGHT){
            return false;
        }
        else
            return true;
    }

}