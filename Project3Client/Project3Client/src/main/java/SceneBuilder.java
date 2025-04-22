import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.dnd.peer.DragSourceContextPeer;
import java.util.concurrent.ThreadLocalRandom;

public class SceneBuilder {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int TILE_SIZE = 75;
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
        Text gameStateText = makeGameText(game.displayMessage, 40);
        Pane board = makeBoard(game.gameState, 550, 550, 5);
        ListView<String> list = new ListView<String>();

        list.getItems().add(String.valueOf(game.gameID));

        HBox root = new HBox(new VBox(gameStateText, board), list) ;
        BorderPane canvas = new BorderPane();
        canvas.setPrefSize(WIDTH, HEIGHT);
        canvas.setCenter(root);

        return new Scene(canvas, WIDTH, HEIGHT - 200);
    }

    private static Pane makeBoard(int[][] gameBoard, double PANE_WIDTH, double PANE_HEIGHT, int GAP) {
        double tileWidth = (PANE_WIDTH - (COLUMNS + 1) * GAP) / COLUMNS;
        double tileHeight = (PANE_HEIGHT - (ROWS + 1) * GAP) / ROWS;
        double TILE_SIZE = Math.min(tileWidth, tileHeight);

        Pane pane = new Pane();
        pane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        Color backgroundColor = Color.BEIGE;

        // Add background
        Rectangle background = new Rectangle(PANE_WIDTH, PANE_HEIGHT);
        background.setFill(backgroundColor);
        pane.getChildren().add(background);

        // Draw vertical gridlines
        for (int x = 1; x < COLUMNS; x++) {
            double lineX = x * (TILE_SIZE + GAP) + (GAP/2);
            Rectangle line = new Rectangle(lineX, 0, 1, PANE_HEIGHT);
            line.setFill(Color.DARKGRAY);
            pane.getChildren().add(line);
        }

        // Add game pieces
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle c = new Circle(TILE_SIZE / 2.0);

                double posX = x * (TILE_SIZE + GAP) + GAP + TILE_SIZE / 2.0;
                double posY = y * (TILE_SIZE + GAP) + GAP + TILE_SIZE / 2.0;
                c.setCenterX(posX);
                c.setCenterY(posY);

                // Color based on game state
                Color color;
                switch (gameBoard[x][y]) {
                    case 0: color = Color.WHITE; break;
                    case 1: color = Color.RED; break;
                    case 2: color = Color.BLUE; break;
                    default: color = Color.BLACK; break;
                }
                c.setFill(color);

                // Add lighting
                Light.Distant light = new Light.Distant();
                light.setAzimuth(45.0);
                light.setElevation(30.0);
                Lighting lighting = new Lighting(light);
                lighting.setSurfaceScale(5.0);
                c.setEffect(lighting);

                pane.getChildren().add(c);
            }
        }

        return pane;
    }

    private static Text makeGameText(String text, int size){
        Text gameText = new Text(text);
        gameText.setFont(new Font(size));

        return gameText;
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
                ((centered) ? "-fx-text-alignment: center;" : "")
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
