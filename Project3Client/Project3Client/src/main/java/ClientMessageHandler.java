import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Objects;

public class ClientMessageHandler{
    public static Game myGame;
    public static String myUsername;

    public static void handle(Message message, Stage currentStage){
            Platform.runLater(() -> {
                System.out.println("=====================");
                System.out.println(myUsername + " received message: " + message.toString());
                System.out.println("=====================");
                switch(message.messageType){
                    case TEXT:
                        System.out.println("TEXT MESSAGE");
                        break;
                    case JOIN:
                        break;
                    case PLAY:
                        if(message.recipient.equals(myUsername)){
                            myGame.Play(message.username, message.moveCol);
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                            System.out.println(message.username + " PLAYED " + message.moveCol);
                        }
                        if(myGame.GameOver()){
                            message.messageText = "GAME OVER";
                            message.messageType = MessageType.GAME_OVER;
                            send(message, currentStage);
                            currentStage.setScene(SceneBuilder.buildGameOverScreen(myGame, currentStage));
                        }
                        break;
                    case DISCONNECT:
                        break;
                    case ERROR:
                        break;
                    case JOIN_ACCEPT:
                        myGame = new Game(message.recipient, Integer.parseInt(message.messageText));
                        myUsername = message.recipient;
                        SceneBuilder.username = myUsername;
                        currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                        break;
                    case JOIN_DENY:
                        currentStage.setScene(SceneBuilder.buildTitleScreen(currentStage));
                        break;
                    case START:
                        if(myGame == null){
                            myGame = new Game(message.recipient, Integer.parseInt(message.messageText));
                            myUsername = message.recipient;
                            SceneBuilder.username = myUsername;
                        }
                        if(Objects.equals(message.recipient, myUsername)){
                            if(message.moveCol == 1) {
                                myGame.fillGame(message.username, false);
                            }
                            else {
                                myGame.fillGame(myUsername, true);
                                myGame.playerOneUser = message.username;
                            }

                            myGame.displayMessage = myGame.getStatus();
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                        }
                        break;
                    case GAME_OVER:
                        currentStage.setScene(SceneBuilder.buildGameOverScreen(myGame, currentStage));
                        break;
                    case CHAT:
                        if(Objects.equals(message.recipient, myUsername)){
                            SceneBuilder.chatLog.getItems().add(message.username + ": " + message.messageText);
                            currentStage.setScene(SceneBuilder.buildGameScreen(myGame, currentStage));
                        }
                        break;
                    case QUIT:
                        myGame = null;
                        myUsername = null;
                        currentStage.setScene(SceneBuilder.buildTitleScreen(currentStage));
                        break;
                    default:
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
                System.out.println(request);
                if(myGame.isInvalidPlay(myUsername, request.moveCol)){
                    System.out.println("INVALID PLAY");
                    return;
                }
                myGame.Play(request.username, request.moveCol);
                if(myGame.GameOver()){
                    stage.setScene(SceneBuilder.buildGameOverScreen(myGame, stage));
                }
                else{
                    stage.setScene(SceneBuilder.buildGameScreen(myGame, stage));
                }
                break;
            case JOIN:
                if(myGame != null){
                    return;
                }
                myUsername = request.username;
                break;
            case QUIT:
                myGame = null;
                myUsername = null;
                stage.setScene(SceneBuilder.buildTitleScreen(stage));
                break;
        }
        ClientThread t1 = new ClientThread(request, stage);
        t1.start();
    }


}