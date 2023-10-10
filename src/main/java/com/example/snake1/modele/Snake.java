package com.example.snake1.modele;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static final Color DEAD = Color.ORANGE;
    private final Grid grid;
    private int length;
    private boolean safe;
    private final List<Point> points;
    private Point head;
    private int xVelocity;
    private int yVelocity;

    // Constructeur pour la classe Snake, prend une grille et un point initial
    public Snake(Grid grid, Point initialPoint) {
        length = 1;
        points = new LinkedList<>();
        points.add(initialPoint);
        head = initialPoint;
        safe = true;
        this.grid = grid;
        xVelocity = 0;
        yVelocity = 0;
    }

    // Méthode pour faire grandir le serpent à un nouveau point
    private void growTo(Point point) {
        length++;
        checkAndAdd(point);
    }

    // Méthode pour déplacer le serpent à un nouveau point
    private void shiftTo(Point point) {
        // The head goes to the new location
        checkAndAdd(point);
        // The last/oldest position is dropped
        points.remove(0);
    }

    // Méthode pour vérifier et ajouter un point au serpent
    private void checkAndAdd(Point point) {
        point = grid.wrap(point);
        safe &= !points.contains(point);
        points.add(point);
        head = point;
    }

    // Getter pour les points du serpent
    public List<Point> getPoints() {
        return points;
    }

    // Méthode pour vérifier si le serpent est en sécurité
    public boolean isSafe() {
        return safe || length == 1;
    }

    // Getter pour la tête du serpent
    public Point getHead() {
        return head;
    }

    // Méthode pour vérifier si le serpent est immobile
    private boolean isStill() {
        return xVelocity == 0 & yVelocity == 0;
    }

    // Méthode pour déplacer le serpent
    public void move() {
        if (!isStill()) {
            shiftTo(head.translate(xVelocity, yVelocity));
        }
    }

    // Méthode pour étendre le serpent
    public void extend() {
        if (!isStill()) {
            growTo(head.translate(xVelocity, yVelocity));
        }
    }

    // Méthodes pour changer la direction du serpent
    public void setUp() {
        if (yVelocity == 1 && length > 1) return;
        xVelocity = 0;
        yVelocity = -1;
    }

    public void setDown() {
        if (yVelocity == -1 && length > 1) return;
        xVelocity = 0;
        yVelocity = 1;
    }

    public void setLeft() {
        if (xVelocity == 1 && length > 1) return;
        xVelocity = -1;
        yVelocity = 0;
    }

    public void setRight() {
        if (xVelocity == -1 && length > 1) return;
        xVelocity = 1;
        yVelocity = 0;
    }
}
