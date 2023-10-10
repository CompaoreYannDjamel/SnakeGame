/*
*          COMPAORE YANN DJAMEL
*           Mame Bara Diop
            Aboubacar Kaba
             Pape Khokhane Séne*/

package com.example.snake1;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.example.snake1.controle.GameLoop;
import com.example.snake1.modele.Grid;
import com.example.snake1.modele.Snake;
import com.example.snake1.vue.Painter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    // Stocker les 10 derniers scores
    private final ArrayList<Integer> lastTenScores = new ArrayList<>();
    private GameLoop loop;
    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private Grid grid;
    private GraphicsContext context;

    @Override
    // Méthode pour lancer l'application
    public void start(Stage primaryStage) {
        VBox root = buildMenu(primaryStage);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour créer le menu principal
    private VBox buildMenu(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #111111;");
        root.setAlignment(Pos.CENTER);
        Text title = new Text("Snake Game");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
        startButton.setOnAction(e -> startGame(primaryStage));
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
        settingsButton.setOnAction(e -> showSettings(primaryStage));
        Button viewScoresButton = new Button("Consulter les scores");
        viewScoresButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
        viewScoresButton.setOnAction(e -> showScores(primaryStage));
        root.getChildren().addAll(title, startButton, settingsButton, viewScoresButton);
        return root;
    }

    // Méthode pour démarrer le jeu
    private void startGame(Stage primaryStage) {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            Snake snake = grid.getSnake();
            if (loop.isKeyPressed()) {
                return;
            }
            switch (e.getCode()) {
                case UP:
                    snake.setUp();
                    break;
                case DOWN:
                    snake.setDown();
                    break;
                case LEFT:
                    snake.setLeft();
                    break;
                case RIGHT:
                    snake.setRight();
                    break;
                case ENTER:
                    if (loop.isPaused()) {
                        reset();
                        (new Thread(loop)).start();
                    }
            }
        });
        reset();
        root.getChildren().add(canvas);
        primaryStage.getScene().setRoot(root);
        (new Thread(loop)).start();
    }

    // Méthode pour réinitialiser le jeu
    private void reset() {
        grid = new Grid(WIDTH, HEIGHT);
        loop = new GameLoop(grid, context);
        Painter.paint(grid, context);
        loop.setOnGameOver(() -> {
            context.setFill(Color.RED);
            context.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            context.fillText("Game Over!", 200, 250);
            int currentScore = (grid.getSnake().getPoints().size() - 1) * 100;
            context.fillText("Score: " + currentScore, 200, 290);
            lastTenScores.add(currentScore);
            if (lastTenScores.size() > 10) {
                lastTenScores.remove(0);
            }
            loop.stop();

            Platform.runLater(() -> {
                Button backButton = new Button("Retour");
                backButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
                backButton.setLayoutX(220);
                backButton.setLayoutY(350);
                backButton.setOnAction(e -> {
                    Stage primaryStage = (Stage) backButton.getScene().getWindow();
                    primaryStage.getScene().setRoot(buildMenu(primaryStage));
                });
                ((Pane) context.getCanvas().getParent()).getChildren().add(backButton);
            });
        });
    }

    // Méthode pour afficher les paramètres
    private void showSettings(Stage primaryStage) {
        VBox settingsRoot = buildSettings(primaryStage);
        primaryStage.getScene().setRoot(settingsRoot);
    }

    // Méthode pour créer les paramètres
    private VBox buildSettings(Stage primaryStage) {
        VBox settingsRoot = new VBox(20);
        settingsRoot.setStyle("-fx-background-color: #111111;");
        settingsRoot.setAlignment(Pos.CENTER);
        Text settingsTitle = new Text("Couleur du serpent");
        settingsTitle.setFill(Color.WHITE);
        settingsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        ColorPicker snakeColorPicker = new ColorPicker(Painter.SNAKE_COLOR);
        snakeColorPicker.setOnAction(e -> {
            Painter.SNAKE_COLOR = snakeColorPicker.getValue();
            if (grid != null && context != null) {
                Painter.paint(grid, context);
            }
        });
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
        backButton.setOnAction(e -> primaryStage.getScene().setRoot(buildMenu(primaryStage)));
        settingsRoot.getChildren().addAll(settingsTitle, snakeColorPicker, backButton);
        return settingsRoot;
    }

    // Méthode pour afficher les scores
    private void showScores(Stage primaryStage) {
        VBox scoresRoot = buildScores(primaryStage);
        primaryStage.getScene().setRoot(scoresRoot);
    }

    // Méthode pour créer l'affichage des scores
    private VBox buildScores(Stage primaryStage) {
        VBox scoresRoot = new VBox(20);
        scoresRoot.setStyle("-fx-background-color: #111111;");
        scoresRoot.setAlignment(Pos.CENTER);
        Text scoresTitle = new Text("Derniers scores");
        scoresTitle.setFill(Color.WHITE);
        scoresTitle.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        Text scoresList = new Text();
        scoresList.setFill(Color.WHITE);
        scoresList.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        scoresList.setText(lastTenScores.stream().map(Object::toString).collect(Collectors.joining("\n")));
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 20;");
        backButton.setOnAction(e -> primaryStage.getScene().setRoot(buildMenu(primaryStage)));
        scoresRoot.getChildren().addAll(scoresTitle, scoresList, backButton);
        return scoresRoot;
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        launch();
    }
}
