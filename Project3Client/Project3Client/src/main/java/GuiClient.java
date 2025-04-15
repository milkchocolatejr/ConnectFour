

import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class GuiClient extends Application{

	
	public static void main(String[] args) {
		Client clientThread = new Client();
		clientThread.start();
		Scanner s = new Scanner(System.in);
		while (s.hasNext()){
			String x = s.nextLine();
			clientThread.send(new Message(x));
		}

		launch(args);


	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setScene(new Scene(new TextField("I am not yet implemented")));
		primaryStage.setTitle("Client");
		primaryStage.show();
		
	}
	



}
