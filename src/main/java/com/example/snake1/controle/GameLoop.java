package com.example.snake1.controle;

import com.example.snake1.vue.Painter;
import com.example.snake1.modele.Grid;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop implements Runnable {
    private Runnable onGameOver;
    private final Grid grid;
    private final GraphicsContext context;
    private final int frameRate;
    private final float interval;
    private boolean running;
    private boolean paused;
    private boolean keyIsPressed;

    // Constructeur de la boucle de jeu
    public GameLoop(final Grid grid, final GraphicsContext context) {
        this.grid = grid;
        this.context = context;
        frameRate = 20;
        interval = 1000.0f / frameRate;
        running = true;
        paused = false;
        keyIsPressed = false;
    }

    @Override
    // Méthode run() exécutée lors du démarrage du thread de la boucle de jeu
    public void run() {
        // Boucle de jeu principale
        while (running && !paused) {

            float time = System.currentTimeMillis();

            keyIsPressed = false;
            grid.update();
            Painter.paint(grid, context);

            if (!grid.getSnake().isSafe()) {
                pause();
                Painter.paintResetMessage(context);
                break;
            }

            time = System.currentTimeMillis() - time;


            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            }
        }
    }

    // Méthode pour arrêter la boucle de jeu
    public void stop() {
        running = false;
    }

    // Méthode pour vérifier si une touche est enfoncée
    public boolean isKeyPressed() {
        return keyIsPressed;
    }

    // Méthode pour mettre en pause le jeu et exécuter la fonction onGameOver
    public void pause() {
        paused = true;
        if (onGameOver != null) {
            onGameOver.run();
        }
    }

    // Méthode pour vérifier si le jeu est en pause
    public boolean isPaused() {
        return paused;
    }

    // Méthode pour définir la fonction à exécuter lors de la fin du jeu
    public void setOnGameOver(Runnable onGameOver) {
        this.onGameOver = onGameOver;
    }



}
