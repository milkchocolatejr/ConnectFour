import javafx.scene.control.ListView;
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
                    if (Objects.equals(game.playerOneUser, message.username) || Objects.equals(game.playerTwoUser, message.username)) {
                        duplicate = true;
                        break;
                    }
                }
                response.messageType = duplicate ? MessageType.JOIN_DENY : MessageType.JOIN_ACCEPT;
                if(duplicate){
                    send(response, server);
                    return null;
                }


                for(Game game : server.getGames()){
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        if(!game.started){
                            game.fillGame(message.username);

                            response.recipient = message.username;
                            response.username = "SERVER";
                            response.messageType = MessageType.JOIN_ACCEPT;
                            response.messageText = message.messageText;
                            send(response, server);
                            return game;
                        } else {
                            response.messageType = MessageType.JOIN_DENY;
                            response.recipient = message.username;
                            send(response, server);
                            return null;
                        }
                    }
                }
                Game g = new Game(message.username, Integer.parseInt(message.messageText));

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

    public static void updateStage(Message data, Server server, Stage stage) {
        switch(data.messageType){
            case JOIN_ACCEPT:
                ListView<String> listGames = new ListView<>();
                ListView<String> listUsers = new ListView<>();
                for(Game g: server.getGames()){
                    listGames.getItems().add(String.valueOf(g.gameID));
                    listUsers.getItems().add(String.valueOf(g.playerOneUser));
                    if(g.playerTwoUser != ""){
                        listGames.getItems().add(String.valueOf(g.gameID));
                        listUsers.getItems().add(String.valueOf(g.playerTwoUser));
                    }
                }

                stage.setScene(SceneBuilder.buildInitScreen(stage, listGames, listUsers));
                stage.show();
                break;
            case JOIN_DENY:
                break;
        }
    }
}
