import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

public class MessageHandler {
    public static Game handle(Message message, Stage stage, Server server) {
        Message response = new Message();
        switch(message.messageType){
            case JOIN:
                boolean duplicate = false;
                for(Game game : server.getGames()){
                    if(game.playerOneUser == message.username || game.playerTwoUser == message.username){
                        duplicate = true;
                    }
                }
                response.messageType = duplicate ? MessageType.JOIN_DENY : MessageType.JOIN_ACCEPT;
                if(duplicate){
                    send(response, server);
                    return null;
                }


                for(Game game : server.getGames()){
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        game.fillGame(message.username);
                        return game;
                    }
                }
                Game g = new Game(message.username, Integer.parseInt(message.messageText));

                System.out.println("*************");
                System.out.println(message.messageText);
                System.out.println("*************");
                server.addGame(g);

                response.recipient = message.username;
                response.username = "SERVER";
                response.messageText = message.messageText;
                send(response, server);
                return g;
            case PLAY:
                System.out.println("PLAY");
                break;
        }
        return null;
    }
    public static void send(Message response, Server server) {
        System.out.println("SEND HIT!");
        //Named client
        for(Server.ClientThread c: server.clients){
            if(Objects.equals(c.username, response.recipient)){
                c.send(response);
                return;
            }
        }
        //Unnamed client
        for(Server.ClientThread c : server.clients){
            if(Objects.equals(c.username, "")){
                c.username = response.recipient;
                c.send(response);
                return;
            }
        }
    }
}
