import javafx.scene.Scene;

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
    boolean started;
    int[][] gameState;


    public Game(String playerOne, String playerTwo, int gameID) {
        this.playerOneWinning = false;
        this.playerTwoWinning = false;
        this.playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = playerTwo;
        this.displayMessage = "Game has started!";

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
        this.started = false;
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
    }

    public void fillGame(String playerTwo){
        if(!this.started) {
            this.playerTwoUser = playerTwo;
            this.displayMessage = "Game has started!";
            Start();
        }
    }

    private void Start(){
        if(this.started){
            throw new RuntimeException("Game has already started!");
        }
        this.started = true;
        playerOneTurn = true;
    }

    public void Play(String username, int col){
        if(!isValidPlay(username, col)){
            return;
        }

        for(int row = BOARD_SIZE - 1; row >= 0; row--){
            if(gameState[row][col] == 0){
                gameState[row][col] = (playerOneTurn ? 1 : 2);
                break;
            }
        }

        playerOneTurn = !playerOneTurn;
    }

    public boolean isValidPlay(String username, int col){
        if(playerOneUser.equals(username) && playerOneTurn){
            return gameState[BOARD_SIZE - 2][col] == 0;
        }
        return false;
    }
    public String getStatus(){
        String status = "GAME " + this.gameID;
        if(!this.started){
            return status + " // GAME NOT STARTED";
        }
        return status + " // " + (this.playerOneTurn ? playerOneUser : playerTwoUser) + "'s turn";
    }


}