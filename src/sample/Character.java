package sample;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {

    private boolean isAlive = true;
    private Polygon character;
    private Point2D movement;

    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.character.setRotate(-90);

        this.movement = new Point2D(0, 0);
    }

    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public Polygon getCharacter() {
        return character;
    }

    public void moveLeft() {
        this.character.setTranslateX((this.character.getTranslateX()) - 10);

    }

    public void moveRight() {
        this.character.setTranslateX((this.character.getTranslateX()) + 10);
    }

    public void moveDown() {
        this.character.setTranslateY((this.character.getTranslateY()) + 10);

    }

    public void moveUp() {
        this.character.setTranslateY((this.character.getTranslateY()) - 10);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        /*if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + Main.LEVEYS);
        }

        if (this.character.getTranslateX() > Main.LEVEYS) {
            this.character.setTranslateX(this.character.getTranslateX() % Main.LEVEYS);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + Main.KORKEUS);
        }

        if (this.character.getTranslateY() > Main.KORKEUS) {
            this.character.setTranslateY(this.character.getTranslateY() % Main.KORKEUS);
        }*/
    }


    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.15;
        changeY *= -0.011;

        this.movement = this.movement.add(changeX, changeY);
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public double getTranslateX(){
        return this.character.getTranslateX();
    }

    public double getTranslateY(){
        return this.character.getTranslateY();
    }


    public void setAlive (boolean alive){
        this.isAlive = alive;
    }
    public boolean isAlive(){

        return isAlive;
    }
}