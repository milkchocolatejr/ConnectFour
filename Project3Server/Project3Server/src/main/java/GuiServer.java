
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
		serverConnection = new Server(data->{
			Platform.runLater(()->{
				MessageHandler.updateStage(serverConnection, primaryStage);
			});
		}, primaryStage);


		listGameCodes = new ListView<>();
		listUsers = new ListView<>();

		for(Game g: serverConnection.getGames()){
			listGameCodes.getItems().add(String.valueOf(g.gameID));
			listUsers.getItems().add(String.valueOf(g.playerOneUser));
			if(g.playerTwoUser != ""){
				listGameCodes.getItems().add(String.valueOf(g.gameID));
				listUsers.getItems().add(String.valueOf(g.playerTwoUser));
			}
		}
		primaryStage.setScene(SceneBuilder.buildInitScreen(primaryStage, listGameCodes, listUsers));
		primaryStage.setTitle("ConnectFour - Trenton/AJ");
		primaryStage.show();
	}
}
