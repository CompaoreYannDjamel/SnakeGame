package com.example.snake1.modele;
import javafx.scene.paint.Color;

public class Food {
    //public static final Color COLOR = Color.ROSYBROWN;

    // Couleur de la nourriture
    public static final Color COLOR = Color.RED;

    // Position de la nourriture dans la grille
    private Point point;

    // Constructeur prenant un objet Point comme paramètre
    Food(Point point) {
        this.point = point;
    }

    // Getter pour récupérer la position de la nourriture
    public Point getPoint() {
        return point;
    }

    // Setter pour définir une nouvelle position de la nourriture
    public void setPoint(Point point) {
        this.point = point;
    }
}
