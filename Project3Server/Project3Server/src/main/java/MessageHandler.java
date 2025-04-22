import javafx.stage.Stage;
import java.util.ArrayList;

public class MessageHandler {
    public static MessageHandlerResponse handle(Message message, ArrayList<Game> gameList, Stage stage) {
        Message response = new Message();
        switch(message.messageType){
            case JOIN:
                boolean duplicate = false;
                for(Game game : gameList){
                    if(game.playerOneUser == message.username || game.playerTwoUser == message.username){
                        duplicate = true;
                    }
                }
                response.messageType = duplicate ? MessageType.JOIN_DENY : MessageType.JOIN_ACCEPT;
                if(duplicate){
                    send(response);
                    return new MessageHandlerResponse(gameList, response);
                }


                boolean gameCreated = false;
                for(Game game : gameList){
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        gameCreated = true;
                        game.fillGame(message.username);
                    }
                }

                if(!gameCreated){
                    System.out.println("*************");
                    System.out.println(message.messageText);
                    System.out.println("*************");
                    gameList.add(new Game(message.username, Integer.parseInt(message.messageText)));
                }

                response.recipient = message.username;
                response.username = "SERVER";
                break;
            case PLAY:
                System.out.println("PLAY");
                break;
        }
        return new MessageHandlerResponse(gameList, response);
    }
    public static void send(Message message) {

    }
}
