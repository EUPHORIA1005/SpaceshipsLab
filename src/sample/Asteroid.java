package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;



public class Asteroid extends Character {
    private double rotationalMovement;


    public Asteroid(int x, int y) {
        //super(new Polygon(20, -20, 20, 20, -20, 20, -20, -20), x, y);
        super(new Polygon(), x - 300, y - 100);

        //Setting the properties of the ellipse
        if (Math.random() < 0.5) {
            getCharacter().getPoints().addAll(new Double[]{
                    410.0, 160.0, 430.0, 130.0, 470.0, 130.0,
                    490.0, 160.0, 470.0, 200.0, 430.0, 200.0
            });
        }
        else {
            getCharacter().getPoints().addAll(new Double[]{
                    205.0,80.0, 215.0, 65.0, 235.0, 65.0,
                    245.0, 80.0, 235.0, 100.0, 215.0, 100.0
            });
            //getCharacter().setScaleX(0.5);
            //getCharacter().setScaleY(0.5);
        }

        Random rnd = new Random();

        super.getCharacter().setScaleX(0.75);
        super.getCharacter().setScaleY(0.75);
        super.getCharacter().setFill(Color.DARKGRAY);
        super.getCharacter().setStroke(Color.BLACK);
        super.getCharacter().setStrokeWidth(0.9);

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