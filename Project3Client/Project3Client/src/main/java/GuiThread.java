import javafx.stage.Stage;

public class GuiThread extends Thread {
    ClientGui gui;
    Stage primaryStage;

    public GuiThread(Stage primaryStage){
        gui = new ClientGui();
    }





}
