import javafx.stage.Stage;

public class GuiThread extends Thread {
    ClientGui gui;
    Stage primaryStage;

    public GuiThread(){
        gui = new ClientGui();
        primaryStage = new Stage();
        try{
            gui.start(primaryStage);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }





}
