import javafx.application.Application;

import javafx.stage.Stage;

public class ClientGui extends Application{
	ClientThread clientThread;
	Stage currentStage;
	Game game;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		renderTitle(primaryStage, "Welcome to Connect Four!");
    }

	public void renderTitle(Stage primaryStage, String title){
		primaryStage.setScene(SceneBuilder.buildTitleScreen(primaryStage));
		primaryStage.setTitle(title);
		primaryStage.show();
		currentStage = primaryStage;
	}

}
