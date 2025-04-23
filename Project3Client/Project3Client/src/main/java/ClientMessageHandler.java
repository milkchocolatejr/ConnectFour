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
                    default:
                        System.out.println("UNKNOWN MESSAGE");
                }

            });
    }

    public static void send(Message request, Stage stage){
        if(request == null){
            return;
        }

        if(request.messageType == MessageType.PLAY && (!myGame.isValidPlay(myUsername, request.moveCol) || !myGame.started)){
            return;
        }
        if(request.messageType == MessageType.PLAY){
            myGame.Play(myUsername, request.moveCol);
            stage.setScene(SceneBuilder.buildGameScreen(myGame, stage));
        }

        ClientThread t1 = new ClientThread(request, stage);
        t1.start();
    }

    public synchronized void tryJoinGame(ClientThread clientThread){

    }
}