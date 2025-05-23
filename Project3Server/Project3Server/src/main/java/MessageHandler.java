import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MessageHandler {
    public static Game handle(Message message, Stage stage, Server server) {
        Message response = new Message();
        switch(message.messageType){
            case JOIN:
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
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        if(!game.started){
                            game.fillGame(message.username, false);

                            Message startMessage1 = new Message();
                            Message startMessage2 = new Message();

                            startMessage1.messageType = MessageType.START;
                            startMessage1.username = game.playerTwoUser;
                            startMessage1.messageText = game.gameID + "";
                            startMessage1.recipient = game.playerOneUser;
                            startMessage1.moveCol = 1;

                            startMessage2.messageType = MessageType.START;
                            startMessage2.username = game.playerOneUser;
                            startMessage2.messageText = game.gameID + "";
                            startMessage2.recipient = game.playerTwoUser;
                            startMessage2.moveCol = 2;

                            send(startMessage1, server);
                            send(startMessage2, server);

                            return game;
                        }
                        else {
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
                for(Game game : server.getGames()){
                    if(game.gameID == Integer.parseInt(message.messageText)){
                        game.Play(message.username, message.moveCol);
                        Message reflectiveMessage = new Message();
                        reflectiveMessage.messageType = MessageType.PLAY;
                        reflectiveMessage.username = message.username;
                        reflectiveMessage.recipient = game.playerOneTurn ? game.playerOneUser: game.playerTwoUser;
                        reflectiveMessage.moveCol = message.moveCol;
                        send(reflectiveMessage, server);
                    }
                }
                break;
            case CHAT:
                send(message, server);
                break;
            case QUIT:
                //Send Reflective quit message to other client
                Message quitMessage = new Message();
                quitMessage.messageType = MessageType.QUIT;
                quitMessage.recipient = message.recipient;
                send(quitMessage, server);
                //Close both connections
                for(Server.ClientThread client : server.clients){
                    if(Objects.equals(client.username, message.username) || Objects.equals(client.username, message.recipient)){
                        try {
                            client.connection.close();
                        } catch (IOException e) {
                            System.out.println("CLOSE CONNECTION FAILURE!");
                            e.printStackTrace();
                        }
                        server.clients.remove(client);
                        for(Game game : server.getGames()){
                            if(game.gameID == Integer.parseInt(message.messageText)){
                                server.getGames().remove(game);
                            }
                        }
                        updateStage(server, stage);
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

    public static void updateStage(Server server, Stage stage) {
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
    }
}
