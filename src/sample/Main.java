package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main extends Application{

    public static int WIDTH = 1200;
    public static int HEIGHT = 800;


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("KozlovErmishovSpaceships");



        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        FileInputStream inp = new FileInputStream("assets\\space.png");
        Image image = new Image(inp);
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(bgImage);
        pane.setBackground(background);

        //Creating text
        Text text = new Text (40, 20, "Points: 0");
        pane.getChildren().add(text);
        AtomicInteger points = new AtomicInteger();
        text.setScaleX(2); text.setScaleY(2);
        text.setSmooth(true);
        text.setFill(Color.ORANGE);

        FileInputStream forShip = new FileInputStream("assets\\pngegg.png");
        Image imgOfSpaceship = new Image(forShip);
        ImageView imageOfShip = new ImageView(imgOfSpaceship);
        imageOfShip.setFitHeight(32);
        imageOfShip.setFitWidth(31);
        imageOfShip.setX(590);
        imageOfShip.setY(580);
        pane.getChildren().add(imageOfShip);

        Ship ship = new Ship(600,600);


        //Создаем астероиды
        Random rnd = new Random();
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 2; i++) {

            Asteroid asteroid = new Asteroid(rnd.nextInt((1200)), -100/*rnd.nextInt((100 - 50) + 50)*/);
            asteroids.add(asteroid);
        }

        //Создаем снаряды
        List<Projectile> projectiles = new ArrayList<>();

        //Сохраняем нажимаемые клавиши
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

        Scene scene = new Scene(pane);

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);

            if (event.getCode() == KeyCode.R && !(ship.isAlive())) {
                try {
                    restart(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });


        //Реакция на нажатия
        new AnimationTimer(){

            @Override
            public void handle(long now){
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false) && ship.getTranslateX() > 0 ){
                    ship.moveLeft();
                    imageOfShip.setX(ship.getTranslateX() - 10);
                }
                else if (pressedKeys.getOrDefault(KeyCode.RIGHT, false) && ship.getTranslateX() < WIDTH){
                    ship.moveRight();
                    imageOfShip.setX(ship.getTranslateX() - 10);
                }
                else if (pressedKeys.getOrDefault(KeyCode.UP, false) && ship.getTranslateY() > 0){
                    ship.moveUp();
                    imageOfShip.setY(ship.getTranslateY() - 20);
                }
                else if (pressedKeys.getOrDefault(KeyCode.DOWN, false)&& ship.getTranslateY() < HEIGHT){
                    ship.moveDown();
                    imageOfShip.setY(ship.getTranslateY() - 20);
                }
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)) {
                    // we shoot
                    if (ship.canShoot()) {
                        Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                        projectiles.add(projectile);

                        projectile.accelerate();
                        projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                        pane.getChildren().add(projectile.getCharacter());
                        ship.setLastShot(System.currentTimeMillis());
                    }
                }

                asteroids.forEach(asteroid -> asteroid.move());
                if (true){
                    asteroids.forEach(asteroid -> asteroid.accelerate());
                }

                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)){
                        ship.setAlive(false);
                        stop();

                        Text menuText = new Text(575, 400, "Your score is " + points.get() + System.lineSeparator() + "Press R to restart!");
                        menuText.setScaleY(3);
                        menuText.setScaleX(3);
                        menuText.setFill(Color.ORANGE);
                        pane.getChildren().add(menuText);

                    }
                });


                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                            text.setText("Points: " + points.addAndGet(100));
                        }
                    });
                });

                projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                List<Projectile> projectilesToRemove = projectiles.stream().filter(projectile -> {
                    List<Asteroid> collisions = asteroids.stream()
                            .filter(asteroid -> asteroid.collide(projectile))
                            .collect(Collectors.toList());



                    if(collisions.isEmpty()) {
                        return false;
                    }

                    collisions.stream().forEach(collided -> {
                        asteroids.remove(collided);
                        pane.getChildren().remove(collided.getCharacter());
                    });

                    return true;
                }).collect(Collectors.toList());

                projectilesToRemove.forEach(projectile -> {
                    pane.getChildren().remove(projectile.getCharacter());
                    projectiles.remove(projectile);
                });


                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());



                if(Math.random() < 0.05) {
                    Asteroid asteroid = new Asteroid(rnd.nextInt((1200)), -100);
                    if(!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }


            }

        }.start();


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void restart(Stage primaryStage) throws Exception {
        start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
