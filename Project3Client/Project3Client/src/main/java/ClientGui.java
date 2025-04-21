

import javafx.application.Application;

import javafx.stage.Stage;

public class ClientGui extends Application{
	ClientThread clientThread;
	Stage stage;
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
