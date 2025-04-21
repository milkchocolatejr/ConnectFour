import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.concurrent.ThreadLocalRandom;

public class SceneBuilder {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int TILE_SIZE = 50;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;



    public static Scene buildTitleScreen(){
        Text title = new Text("CS 342 Connect Four");
        title.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 30px;" +
                "-fx-text-fill: white;");

        Text userPrompt = new Text("Please enter your username: ");
        Text codePrompt = new Text("Please enter your game code: ");

        TextField usernameBox = new TextField();
        TextField codeBox = new TextField();


        Button loginButton = new Button("Login");

        Button randomCodeButton = new Button("Generate Random Code");
        randomCodeButton.setOnAction(e -> {
            codeBox.setText(
                    Integer.toString(ThreadLocalRandom.current().nextInt(0, 10001))
            );
        });

        HBox buttonBox = new HBox(randomCodeButton, loginButton);

        VBox root = new VBox(title, userPrompt, usernameBox, codePrompt, codeBox, buttonBox);
        BorderPane canvas = new BorderPane();
        canvas.setPrefSize(WIDTH - 100, HEIGHT - 100);
        canvas.setCenter(root);

        return new Scene(canvas, WIDTH, HEIGHT);
    }

    public static Scene buildGameScreen(Game game){
        Text gameStateText = new Text("GAME: " + game.gameID +  " | " + (game.playerOneTurn ? game.playerOneUser : game.playerTwoUser) + "'s turn");
        Shape board = makeBoard(game.gameState);

        VBox root = new VBox(board);
        BorderPane canvas = new BorderPane();
        canvas.setPrefSize(WIDTH, HEIGHT);
        canvas.setCenter(root);

        return new Scene(canvas, WIDTH, HEIGHT);
    }

    private static Shape makeBoard(int[][] gameBoard){
        Shape board = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);

        for(int y = 0; y < ROWS; y++){
            for(int x = 0; x < COLUMNS; x++){
                Circle c = new Circle((double) TILE_SIZE / 2);

                //Setting center of the square to be the true center of sub-square
                c.setCenterX((double) TILE_SIZE / 2);
                c.setCenterY((double) TILE_SIZE / 2);

                //Providing margins
                c.setTranslateX(x * (TILE_SIZE + 5) + (double) TILE_SIZE / 2);
                c.setTranslateY(y * (TILE_SIZE + 5) + (double) TILE_SIZE / 2);

                //Color piece based on game state
                board = Shape.subtract(board, c);

                Color color;
                switch(gameBoard[x][y]){
                    case 0:
                        color = Color.WHITE;
                        break;
                    case 1:
                        color = Color.RED;
                        break;
                    case 2:
                        color = Color.BLUE;
                        break;
                    default:
                        color = Color.BLACK;
                        break;
                }

                board.setFill(Color.BEIGE);

                //Apply lighting to the circles
                Light.Distant light = new Light.Distant();
                light.setAzimuth(45.0);
                light.setElevation(30.0);

                Lighting lighting = new Lighting(light);
                lighting.setSurfaceScale(5.0);

                board.setEffect(lighting);

                board = Shape.intersect(board, c);

            }
        }
        return board;
    }
}
