import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientMessageHandler{
    public static void handle(Message message, Stage currentStage){
        if(message == null || currentStage == null){
            Platform.runLater(() -> {
                switch(message.messageType){
                    case TEXT:
                        System.out.println("TEXT MESSAGE");
                        break;
                    case JOIN:
                        System.out.println("JOINED SERVER");
                        break;
                    case PLAY:
                        System.out.println("PLAYING SERVER");
                        break;
                    case DISCONNECT:
                        System.out.println("DISCONNECTED SERVER");
                        break;
                    case ERROR:
                        System.out.println("ERROR MESSAGE");
                        break;
                    case JOIN_ACCEPT:
                        System.out.println("JOIN ACCEPTED SERVER");
                        break;
                    case JOIN_DENY:
                        System.out.println("JOIN DENIED SERVER");
                        break;
                    default:
                        System.out.println("UNKNOWN MESSAGE");
                }

            });
        }

    }

    public static void send(Message request, Stage stage){
        if(request == null){
            return;
        }
        ClientThread t1 = new ClientThread(request, stage);
        t1.start();
    }

    public synchronized void tryJoinGame(ClientThread clientThread){

    }
}