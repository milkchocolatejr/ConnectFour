
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class ClientGui extends Application{
	ClientThread clientThread;
	Stage stage;

	/*TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	VBox clientBox;
	Client clientConnection;

	HBox fields;

	ComboBox<Integer> listUsers;
	ListView<String> listItems;*/
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		renderTitle(primaryStage, "Welcome to connect four!");
		ClientThread clientThread = new ClientThread(primaryStage);
		clientThread.start();
	}

	public void renderTitle(Stage primaryStage, String title){
		primaryStage.setScene(SceneBuilder.buildTitleScreen());
		primaryStage.setTitle(title);
		primaryStage.show();
	}

	public void renderPlayer(int player){

	}


}
