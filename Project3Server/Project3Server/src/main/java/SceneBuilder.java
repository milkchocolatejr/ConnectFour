import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class SceneBuilder {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int TILE_SIZE = 50;
    private static final int ROWS = 7;
    private static final int COLUMNS = 7;
    public static Scene buildInitScreen(Stage stage, ListView<String> listGames, ListView<String> listUsers){
        HBox lists = new HBox(listGames,listUsers);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(70));
        pane.setStyle("-fx-background-color: coral");

        pane.setCenter(lists);
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

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLUMNS; col++){
                Circle c = new Circle((double) TILE_SIZE / 2);

                //Setting center of the square to be the true center of sub-square
                c.setCenterX((double) TILE_SIZE / 2);
                c.setCenterY((double) TILE_SIZE / 2);

                //Providing margins
                c.setTranslateX(row * (TILE_SIZE + 5) + (double) TILE_SIZE / 2);
                c.setTranslateY(col * (TILE_SIZE + 5) + (double) TILE_SIZE / 2);

                //Color piece based on game state
                board = Shape.subtract(board, c);

                Color color;
                switch(gameBoard[row][col]){
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
