import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class SceneBuilder {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int TILE_SIZE = 50;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;

    public static Scene buildTitleScreen(Stage primaryStage) {
        Text title = makeTitleText("Welcome to our \n Connect Four Project! \n SPR 2025 CS 342 ", 70, TextAlignment.CENTER, true);

        Text userPrompt = makeTitleText("Please enter your username:", 40, null, false);
        Text codePrompt = makeTitleText("Please enter your game code:", 40, null, false);

        TextField usernameField = makeTitleTextField("Enter username here", 30);
        TextField codeField = makeTitleTextField("Enter game code here", 30);

        Button loginButton = makeTitleButton("Login", 30);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            int gameCode = Integer.parseInt(codeField.getText());

            if(gameCode < 10000 || gameCode > 99999){
                codeField.setText("INVALID GAME CODE: PLEASE ENTER A 5 DIGIT INTEGER!");
                return;
            }

            Message message = new Message();
            message.username = username;
            message.messageText = Integer.toString(gameCode);
            message.messageType = MessageType.JOIN;

            ClientMessageHandler.send(message, primaryStage);

        });

        Button randomCodeButton = makeTitleButton("Generate Random Code", 30);
        randomCodeButton.setOnAction(e -> {
            codeField.setText(
                    Integer.toString(ThreadLocalRandom.current().nextInt(10000, 99999))
            );
        });

        VBox userBox = new VBox(10, userPrompt, usernameField);
        VBox codeBox = new VBox(10, codePrompt, codeField);

        HBox buttonBox = new HBox(10, randomCodeButton, loginButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(60, title, userBox, codeBox, buttonBox);
        BorderPane.setMargin(root, new Insets(25,25,25,25));

        BorderPane canvas = new BorderPane(root);
        canvas.setPrefSize(WIDTH - 100, HEIGHT - 100);
        canvas.setStyle("-fx-background-color: linear-gradient(to bottom right, black, darkgoldenrod);");
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

    private static Button makeTitleButton(String text, int fontSize){
        Button button = new Button(text);
        button.setAlignment(javafx.geometry.Pos.CENTER);
        button.setStyle(
                "-fx-background-color: white;"+
                "-fx-text-fill: #000000;" +
                "-fx-background-color: #dda428; " +
                "-fx-font-size: " + fontSize + "px;" +
                "-fx-background-radius: 30px; " +
                "-fx-border-radius: 30px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-font-weight: bold; " +
                "-fx-min-width: 200px; " +
                "-fx-min-height: 50px; " +
                "-fx-border-color: #e68a00; " +
                "-fx-border-width: 2px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.5), 10, 0, 4, 4);"
        );
        button.setMaxWidth((int)(WIDTH / 2) - 20);
        return button;
    }

    private static Text makeTitleText(String text, int fontSize, TextAlignment alignment, boolean centered){
        Text t = new Text(text);
        t.setStyle(
                "-fx-font-size: " + fontSize + "px;" +
                "-fx-fill: #FFFFFF;" +
                "-fx-stroke: #000000;" +
                "-fx-stroke-width: 2px;" +
                ((centered) ? "-fx-text-alignment: : center;" : "")
        );
        t.setTextAlignment((alignment == null) ? TextAlignment.LEFT : alignment);
        return t;
    }

    private static TextField makeTitleTextField(String text, int fontSize){
        TextField t = new TextField(text);
        t.setStyle(
                "-fx-font-size: " + fontSize + "px;"
        );
        return t;
    }
}
