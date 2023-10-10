package com.example.snake1.modele;

public class Point {
    private final int x;    // coordonnees x
    private final int y;    // coordonees y

    // Constructeur pour la classe Point, prend les coordonnées x et y
    Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    // Getter pour la coordonnée x
    public int getX() {
        return x;
    }

    // Getter pour la coordonnée y
    public int getY() {
        return y;
    }

    // Méthode pour translater un point par une distance dx et dy
    public Point translate(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }

    // Méthode pour vérifier si deux points sont égaux
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) return false;
        Point point = (Point) other;
        return x == point.x & y == point.y;
    }

    // Méthode pour convertir les coordonnées d'un point en chaîne de caractères
    public String toString() {
        return x + ", " + y;
    }
}
