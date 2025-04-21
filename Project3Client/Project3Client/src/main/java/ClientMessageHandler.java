import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientMessageHandler{
    public static void handle(Message message, Stage currentStage){
        if(message == null || currentStage == null){
            return;
        }

    }

    public static void send(Message request, Stage stage){
        if(request == null){
            return;
        }
        Message response = null;
        try {
            ClientThread t1 = new ClientThread(request, response, stage);
            t1.start();
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void tryJoinGame(ClientThread clientThread){

    }
}