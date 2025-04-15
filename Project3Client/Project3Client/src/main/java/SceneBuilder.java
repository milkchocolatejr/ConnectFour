import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.concurrent.ThreadLocalRandom;

public class SceneBuilder {
    private static int WIDTH = 800;
    private static int HEIGHT = 800;

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

    public static Scene buildGameScreen(Shape board){
        return new Scene(new BorderPane(board), WIDTH, HEIGHT);
    }
}
