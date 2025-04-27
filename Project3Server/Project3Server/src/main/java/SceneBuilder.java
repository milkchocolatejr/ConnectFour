import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;


public class SceneBuilder {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int TILE_SIZE = 50;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;
    public static Server server;

    public static Scene buildInitScreen(Stage stage, ListView<String> listGames, ListView<String> listUsers){
        HBox lists = new HBox(listGames,listUsers);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));
        pane.setStyle("-fx-background-color: coral");

        TextField gameCodeField = new TextField();
        Button seeButton = new Button("View Game");

        seeButton.setOnAction(actionEvent -> {
            boolean gameFound = false;
            boolean gameStarted = false;
            int gameCode;

            try{
                gameCode = Integer.parseInt(gameCodeField.getText());
            }
            catch(Exception e){
                gameCodeField.setText("Please enter a valid integer.");
                return;
            }
            for(Game g : server.getGames()){
                if(g.gameID == gameCode){
                    gameFound = true;
                    gameStarted = g.started;
                    break;
                }
            }

            if(!gameFound){
                gameCodeField.setText("Game not found on the server.");
                return;
            }
            if(!gameStarted){
                gameCodeField.setText("Game not started, please wait until the game starts to view.");
                return;
            }

            stage.setScene(SceneBuilder.buildBoardView(gameCode, stage, listGames, listUsers));
        }
        );

        HBox seeGameBox = new HBox(gameCodeField, seeButton);
        seeGameBox.setPrefWidth(listGames.getWidth() * 2);
        pane.setCenter(lists);
        pane.setBottom(seeGameBox);
        pane.setStyle("-fx-font-family: 'serif'");


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });


        return new Scene(pane, 500, 400);
    }
    private static Scene buildBoardView(int gameID, Stage stage, ListView<String> listGames, ListView<String> listUsers){
        int PANE_SIZE = 550;
        Game game = null;
        for(Game g : server.getGames()){
            if(g.gameID == gameID){
                game = g;
                break;
            }
        }
        if(game == null){
            return null;
        }

        Pane board = makeBoard(game.gameState, PANE_SIZE, PANE_SIZE, PANE_SIZE/100);
        Button returnButton = new Button("Click here to go back to the main screen.");
        returnButton.setOnAction(actionEvent -> {
            stage.setScene(SceneBuilder.buildInitScreen(stage, listGames, listUsers));
        });

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));

        Text status = new Text();
        if(game.playerOneTurn){
            status = makeTitleText(game.playerOneUser + "'s turn", 50);
        }
        else if (!game.playerOneTurn){
            status = makeTitleText(game.playerTwoUser + "'s turn", 50);
        }

        if(game.GameOver()){
            status = makeTitleText(game.playerOneWinning ? game.playerOneUser + " has won!" : game.playerTwoUser + " has won!", 50);
        }

        VBox viewBox = new VBox(status, board, returnButton);

        pane.setCenter(viewBox);

        stage.setTitle("GAME " + gameID);

        return new Scene(pane, WIDTH, HEIGHT);

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
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Circle c = new Circle(TILE_SIZE / 2.0);

                double posX = col * (TILE_SIZE + GAP) + GAP + TILE_SIZE / 2.0;
                double posY = row * (TILE_SIZE + GAP) + GAP + TILE_SIZE / 2.0;
                c.setCenterX(posX);
                c.setCenterY(posY);

                // Color based on game state
                Color color;
                switch (gameBoard[row][col]) {
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

    private static Text makeTitleText(String text, int fontSize){
        Text t = new Text(text);
        t.setStyle(
                "-fx-font-size: " + fontSize + "px;" +
                        "-fx-fill: #FFFFFF;" +
                        "-fx-stroke: #000000;" +
                        "-fx-stroke-width: 2px;" +
                        "-fx-text-alignment: center;"
        );
        t.setTextAlignment(TextAlignment.CENTER);
        return t;
    }

}
