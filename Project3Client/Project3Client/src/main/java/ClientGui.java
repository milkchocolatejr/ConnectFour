
import javafx.application.Application;

import javafx.application.Platform;
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
	ClientThread clientThread, clientConnection;
	Stage stage;
	TextField c1;
	Button b1;
	HashMap<String, Scene> sceneMap;
	VBox clientBox;

	HBox fields;

	ComboBox<Integer> listUsers;
	ListView<String> listItems;
	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*clientConnection = new ClientThread(data->{
			Platform.runLater(()->{
				switch (data.messageType){
					case JOIN:
						listUsers.getItems().add(data.recipient);
						listItems.getItems().add(data.recipient + " has joined!");
						break;
					case ERROR:
						listUsers.getItems().remove(data.recipient);
						listItems.getItems().add(data.recipient + " has disconnected!");
						break;
					case TEXT:
						listItems.getItems().add(data.recipient+": "+data.messageText);
				}
			});
		});

		clientConnection.start();*/
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
