
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

	ListView<String> listGames, listUsers, listTextMsgs;

	HBox lists;

	public static void main(String[] args) {
		Server serv = new Server();
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Place where the Server GUI will be filled with information sourced from
		//various types of messages: GAME, NEWUSER, and TEXT
		/*serverConnection = new Server(data->{
			Platform.runLater(()->{
				switch (data.type){
					case TEXT:
						listItems.getItems().add(data.recipient+": "+data.message);
						break;
					case NEWUSER:
						listUsers.getItems().add(String.valueOf(data.recipient));
						listItems.getItems().add(data.recipient + " has joined!");
						break;
					case DISCONNECT:
						listUsers.getItems().remove(String.valueOf(data.recipient));
						listItems.getItems().add(data.recipient + " has disconnected!");
				}
			});
		});*/


		listGames = new ListView<String>();
		listUsers = new ListView<String>();
		listTextMsgs = new ListView<String>();

		lists = new HBox(listGames,listUsers,listTextMsgs);


		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");

		pane.setCenter(lists);
		pane.setStyle("-fx-font-family: 'serif'");
		;


		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.setScene(new Scene(pane, 500, 400));
		primaryStage.setTitle("ConnectFour - Trenton/AJ Working Title");
		primaryStage.show();
		
	}



}
