

import java.util.Scanner;

import javafx.application.Application;

import javafx.stage.Stage;

public class ClientGui extends Application{

	public static void main(String[] args) {
		Client clientThread = new Client();
		clientThread.start();
		Scanner s = new Scanner(System.in);
		while (s.hasNext()){
			String x = s.nextLine();
			//clientThread.send(MESSAGE);
		}

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		renderTitle(primaryStage, "Welcome to connect four!");
	}

	public void renderTitle(Stage primaryStage, String title){
		primaryStage.setScene(SceneBuilder.buildTitleScreen());
		primaryStage.setTitle(title);
		primaryStage.show();
	}

	public void renderPlayer(int player){

	}


}
