import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

public class MessageHandler {
    public static Game handle(Message message, Stage stage, Server server) {
        Message response = new Message();
        switch(message.messageType){
            case JOIN:
                System.out.println(message.toString());
                boolean duplicateUsername = false;
                for(Game game : server.getGames()){
                    if (Objects.equals(game.playerOneUser, message.username) || Objects.equals(game.playerTwoUser, message.username)) {
                        duplicateUsername = true;
                        break;
                    }
                }
                response.messageType = duplicateUsername ? MessageType.JOIN_DENY : MessageType.JOIN_ACCEPT;
                if(duplicateUsername){
                    send(response, server);
                    return null;
                }


                for(Game game : server.getGames()){
                    System.out.println(game.gameID);
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        if(!game.started){
                            System.out.println("BLOCK HIT!");
                            game.fillGame(message.username);

                            response.recipient = message.username;
                            response.username = "SERVER";
                            response.messageType = MessageType.JOIN_ACCEPT;
                            response.messageText = message.messageText;
                            send(response, server);

                            Message startMessage1 = new Message();
                            Message startMessage2 = new Message();

                            startMessage1.messageType = MessageType.START;
                            startMessage1.username = game.playerTwoUser;
                            startMessage1.messageText = "1";
                            startMessage1.recipient = game.playerOneUser;

                            startMessage2.messageType = MessageType.START;
                            startMessage2.username = game.playerOneUser;
                            startMessage2.messageText = "2";
                            startMessage2.recipient = game.playerTwoUser;

                            send(response, server);
                            send(startMessage1, server);
                            send(startMessage2, server);
                            return game;
                        } else {
                            response.messageType = MessageType.JOIN_DENY;
                            response.recipient = message.username;
                            response.messageText = "GAME ALREADY STARTED!";
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
                Message reflectiveMessage = new Message();
                reflectiveMessage.messageType = MessageType.PLAY;
                reflectiveMessage.messageText = message.username;
                reflectiveMessage.moveCol = message.moveCol;

                for(Game game : server.getGames()){
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        reflectiveMessage.recipient = (game.playerOneTurn ? game.playerTwoUser : game.playerOneUser);
                        send(reflectiveMessage, server);
                        game.Play(message.username, message.moveCol);
                        return game;
                    }
                }
                break;
        }
        return null;
    }
    public static void send(Message response, Server server) {
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
            case PLAY:
                break;
        }
    }
}
