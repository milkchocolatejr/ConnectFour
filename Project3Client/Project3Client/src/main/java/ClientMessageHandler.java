import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientMessageHandler{
    public static Game myGame;
    public static String myUsername;

    public static void handle(Message message, Stage currentStage){
            Platform.runLater(() -> {
                switch(message.messageType){
                    case TEXT:
                        System.out.println("TEXT MESSAGE");
                        break;
                    case JOIN:
                        System.out.println("JOINED SERVER");
                        break;
                    case PLAY:
                        System.out.println("CLIENT GOT PLAY");
                        if(message.recipient.equals(myUsername)){
                            myGame.Play(message.messageText, message.moveCol);
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                            System.out.println(message.messageText + " PLAYED " + message.moveCol);
                        }
                        break;
                    case DISCONNECT:
                        System.out.println("DISCONNECTED SERVER");
                        break;
                    case ERROR:
                        System.out.println("ERROR MESSAGE");
                        break;
                    case JOIN_ACCEPT:
                        myGame = new Game(message.recipient, Integer.parseInt(message.messageText));
                        currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                        myUsername = message.recipient;
                        break;
                    case JOIN_DENY:
                        currentStage.setScene(SceneBuilder.buildTitleScreen(currentStage));
                        break;
                    case START:
                        if(Objects.equals(message.recipient, myUsername)){
                            if(Objects.equals(message.messageText, "1")) {
                                myGame.fillGame(message.username);
                            }
                            else if(Objects.equals(message.messageText, "2")) {
                                myGame.fillGame(myUsername);
                                myGame.playerOneUser = message.username;
                            }
                            myGame.displayMessage = myGame.getStatus();
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                        }
                        break;
                    case PLAY_ACCEPT:
                        if(myGame.gameID == Integer.parseInt(message.recipient)){
                            myGame.Play(message.messageText, message.moveCol);
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                            System.out.println(message.messageText + " PLAYED " + message.moveCol);
                        }
                        break;
                    default:
                        System.out.println("UNKNOWN MESSAGE");
                        break;
                }

            });
    }

    public static void send(Message request, Stage stage){
        if(request == null){
            return;
        }
        switch(request.messageType){
            case PLAY:
                if(!myGame.isValidPlay(myUsername, request.moveCol)){
                    return;
                }
                break;
            case JOIN:
                if(myGame != null){
                    return;
                }
        }
        ClientThread t1 = new ClientThread(request, stage);
        t1.start();

    }

    public synchronized void tryJoinGame(ClientThread clientThread){

    }
}