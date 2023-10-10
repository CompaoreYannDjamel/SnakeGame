package com.example.snake1.modele;
import javafx.scene.paint.Color;

import java.util.Random;
public class Grid {
    public static final int SIZE = 10;
    public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);
    private final int cols;
    private final int rows;
    private final Snake snake;
    private final Food food;

    // Constructeur de la grille avec la largeur et la hauteur
    public Grid(final double width, final double height) {
        rows = (int) width / SIZE;
        cols = (int) height / SIZE;

        // initialiser le serpent au centre de l'écran
        snake = new Snake(this, new Point(rows / 2, cols / 2));

        // mettre la nourriture à un endroit aléatoire
        food = new Food(getRandomPoint());
    }

    // Méthode pour emballer les bords de la grille
    public Point wrap(Point point) {
        int x = point.getX();
        int y = point.getY();
        if (x >= rows) x = 0;
        if (y >= cols) y = 0;
        if (x < 0) x = rows - 1;
        if (y < 0) y = cols - 1;
        return new Point(x, y);
    }

    // Méthode pour obtenir un point aléatoire dans la grille
    private Point getRandomPoint() {
        Random random = new Random();
        Point point;
        do {
            point = new Point(random.nextInt(rows), random.nextInt(cols));
        } while (point.equals(snake.getHead()));
        return point;
    }

    // Méthode pour mettre à jour la position du serpent et de la nourriture
    public void update() {
        if (food.getPoint().equals(snake.getHead())) {
            snake.extend();
            food.setPoint(getRandomPoint());
        } else {
            snake.move();
        }
    }

    // Getters pour les attributs de la classe
  //  public int getCols() {
  //      return cols;
    //}

    //public int getRows() {
    //    return rows;
  //  }

    public double getWidth() {
        return rows * SIZE;
    }

    public double getHeight() {
        return cols * SIZE;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

}
