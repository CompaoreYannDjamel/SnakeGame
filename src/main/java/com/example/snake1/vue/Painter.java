package com.example.snake1.vue;

import com.example.snake1.modele.Food;
import com.example.snake1.modele.Grid;
import com.example.snake1.modele.Point;
import com.example.snake1.modele.Snake;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import static com.example.snake1.modele.Grid.SIZE;

public class Painter {
    public static Color SNAKE_COLOR = Color.BEIGE;

    // Méthode pour peindre la grille, la nourriture et le serpent sur le canevas
    public static void paint(Grid grid, GraphicsContext gc) {

        // Peindre la grille
        gc.setFill(Grid.COLOR);
        gc.fillRect(0, 0, grid.getWidth(), grid.getHeight());

        // Peindre la nourriture
        gc.setFill(Food.COLOR);
        paintPoint(grid.getFood().getPoint(), gc);

        // Peindre le serpent
        Snake snake = grid.getSnake();
        gc.setFill(SNAKE_COLOR);
        snake.getPoints().forEach(point -> paintPoint(point, gc));

        // Peindre la tête du serpent en couleur DEAD si le serpent n'est pas en sécurité
        if (!snake.isSafe()) {
            gc.setFill(Snake.DEAD);
            paintPoint(snake.getHead(), gc);
        }

        // Afficher le score
        gc.setFill(Color.BEIGE);
        gc.fillText("Score : " + 100 *(-1+ snake.getPoints().size()), 10, 490);
    }

    // Méthode pour peindre un point sur le canvas
    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
    }

    // Méthode pour afficher un message de réinitialisation du jeu
    public static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Appuyez sur Entrer pour recommencer.", 10, 10);
    }
}
