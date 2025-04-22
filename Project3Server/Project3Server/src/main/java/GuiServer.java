
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{
	Server serverConnection;

	ListView<String> listGameCodes, listUsers;
	ArrayList<Game> gameList;



	public static void main(String[] args) { launch(args); }
	

	@Override
	public void start(Stage primaryStage) throws Exception {

		listGameCodes = new ListView<String>();
		listUsers = new ListView<String>();
		gameList = new ArrayList<>();

		serverConnection = new Server(data->{
			Platform.runLater(()->{
				MessageHandlerResponse response = MessageHandler.handle(data, gameList, primaryStage);
			});
		});

		primaryStage.setScene(SceneBuilder.buildInitScreen(primaryStage, listGameCodes, listUsers));
		primaryStage.setTitle("ConnectFour - Trenton/AJ Working Title");
		primaryStage.show();
		
	}
}
