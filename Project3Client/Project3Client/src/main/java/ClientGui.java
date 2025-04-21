import javafx.application.Application;

import javafx.stage.Stage;

public class ClientGui extends Application{
	ClientThread clientThread;
	Stage currentStage;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		renderTitle(primaryStage, "Welcome to connect four!");
    }

	public void renderTitle(Stage primaryStage, String title){
		primaryStage.setScene(SceneBuilder.buildTitleScreen(primaryStage));
		primaryStage.setTitle(title);
		primaryStage.show();
		currentStage = primaryStage;
	}

	public void renderPlayer(Stage primaryStage, String title){
		primaryStage.setTitle(title);
		primaryStage.show();
		currentStage = primaryStage;
	}


}
