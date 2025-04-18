

import java.util.Scanner;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
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
			//clientThread.send(MESSAGE);
		}

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(SceneBuilder.buildTitleScreen());
		/*
		Extra code to try and test the scene GUI
		VBox vbox = new VBox(new TextField("Dsads"));
		Scene scene = new Scene(pane, 500, 400);
		primaryStage.setScene(scene);*/
		primaryStage.setTitle("TEST");
		primaryStage.show();
	}

}
