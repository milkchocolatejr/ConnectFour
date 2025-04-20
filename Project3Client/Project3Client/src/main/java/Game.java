import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.String;

public class Game {
    private final int BOARD_SIZE = 7;
    boolean playerOneWinning;
    boolean playerTwoWinning;
    boolean gameOver;
    boolean playerOneTurn;
    String playerOneUser;
    String playerTwoUser;
    String displayMessage;
    int gameID;
    Scene GUI;
    int[][] gameState;

    public Game(String playerOne, String playerTwo, int gameID) {
        this.playerOneWinning = false;
        this.playerTwoWinning = false;
        this.playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = playerTwo;
        this.displayMessage = "Game has started!";

        this.GUI = SceneBuilder.buildGameScreen(this);
        gameState = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
        Start();
    }

    public Game(String playerOne, int gameID){
        this.playerOneWinning = false;
        this.playerTwoWinning = false;
        this.gameOver = false;
        this.playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = "";
        this.displayMessage = "Waiting... | Code: " + gameID;

        gameState = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }

        this.GUI = SceneBuilder.buildGameScreen(this);
    }

    public void fillGame(String playerTwo){
        if(this.playerTwoUser == "") {
            this.playerTwoUser = playerTwo;
            this.displayMessage = "Game has started!";
            this.GUI = SceneBuilder.buildGameScreen(this);
        }
        Start();
    }

    private void Start(){

    }

    public boolean Play(int col){
        throw new NotImplementedException();
    }


}