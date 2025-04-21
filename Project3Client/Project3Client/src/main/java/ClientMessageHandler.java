import javafx.application.Platform;

public class ClientMessageHandler{
    public static void handle(Message message, Thread guiThread){
        if(message == null || guiThread == null){
            return;
        }
        switch(message.messageType) {
            case JOIN:
                Platform.runLater(guiThread);
                break;
            case PLAY:
                break;
            case TEXT:
                break;
            case ERROR:
                /*listUsers.getItems().remove(message.recipient);
                listItems.getItems().add(message.recipient + " has disconnected!");*/
                break;
            default:
                throw new IllegalArgumentException("Message type not specified!");
        }
    }

    public synchronized void tryJoinGame(ClientThread clientThread){

    }
}