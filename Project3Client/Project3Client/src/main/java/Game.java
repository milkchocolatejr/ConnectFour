import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.String;
import java.util.Objects;

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
        this.displayMessage = getStatus();

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
        this.started = false;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = "";
        this.displayMessage = getStatus();

        gameState = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
    }

    public void fillGame(String playerTwo, boolean swap){
        if(!this.started) {
            this.playerTwoUser = playerTwo;
            this.displayMessage = getStatus();
            if(swap){
                this.playerTwoUser = this.playerOneUser;
                this.playerOneUser = playerTwo;
            }
            Start();
        }
    }

    private void Start(){
        if(this.started){
            throw new RuntimeException("Game has already started!");
        }
        this.started = true;
        playerOneTurn = true;
        System.out.println("=============");
        System.out.println("GAME START!");
        System.out.println("Player 1: " + playerOneUser);
        System.out.println("Player 2: " + playerTwoUser);
        System.out.println("=============");
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
        if(!started){
            return false;
        }
        if(playerOneUser.equals(username) && playerOneTurn){
            return gameState[BOARD_SIZE - 2][col] == 0;
        }
        return false;
    }
    public String getStatus(){
        if(!this.started){
            return "GAME NOT STARTED";
        }
        return (this.playerOneTurn ? playerOneUser : playerTwoUser) + "'s turn";
    }

    public int outcome() {

        int[][] boardPairs =
                {
                        //All Horizontal win outcomes
                        {gameState[0][0], gameState[0][1], gameState[0][2], gameState[0][3]},
                        {gameState[0][1], gameState[0][2], gameState[0][3], gameState[0][4]},
                        {gameState[0][2], gameState[0][3], gameState[0][4], gameState[0][5]},
                        {gameState[0][3], gameState[0][4], gameState[0][5], gameState[0][6]},

                        {gameState[1][0], gameState[1][1], gameState[1][2], gameState[1][3]},
                        {gameState[1][1], gameState[1][2], gameState[1][3], gameState[1][4]},
                        {gameState[1][2], gameState[1][3], gameState[1][4], gameState[1][5]},
                        {gameState[1][3], gameState[1][4], gameState[1][5], gameState[1][6]},

                        {gameState[2][0], gameState[2][1], gameState[2][2], gameState[2][3]},
                        {gameState[2][1], gameState[2][2], gameState[2][3], gameState[2][4]},
                        {gameState[2][2], gameState[2][3], gameState[2][4], gameState[2][5]},
                        {gameState[2][3], gameState[2][4], gameState[2][5], gameState[2][6]},

                        {gameState[3][0], gameState[3][1], gameState[3][2], gameState[3][3]},
                        {gameState[3][1], gameState[3][2], gameState[3][3], gameState[3][4]},
                        {gameState[3][2], gameState[3][3], gameState[3][4], gameState[3][5]},
                        {gameState[3][3], gameState[3][4], gameState[3][5], gameState[3][6]},

                        {gameState[4][0], gameState[4][1], gameState[4][2], gameState[4][3]},
                        {gameState[4][1], gameState[4][2], gameState[4][3], gameState[4][4]},
                        {gameState[4][2], gameState[4][3], gameState[4][4], gameState[4][5]},
                        {gameState[4][3], gameState[4][4], gameState[4][5], gameState[4][6]},

                        {gameState[5][0], gameState[5][1], gameState[5][2], gameState[5][3]},
                        {gameState[5][1], gameState[5][2], gameState[5][3], gameState[5][4]},
                        {gameState[5][2], gameState[5][3], gameState[5][4], gameState[5][5]},
                        {gameState[5][3], gameState[5][4], gameState[5][5], gameState[5][6]},

                        {gameState[6][0], gameState[6][1], gameState[6][2], gameState[6][3]},
                        {gameState[6][1], gameState[6][2], gameState[6][3], gameState[6][4]},
                        {gameState[6][2], gameState[6][3], gameState[6][4], gameState[6][5]},
                        {gameState[6][3], gameState[6][4], gameState[6][5], gameState[6][6]},

                        //All vertical win outcomes
                        {gameState[0][0], gameState[1][0], gameState[2][0], gameState[3][0]},
                        {gameState[1][0], gameState[2][0], gameState[3][0], gameState[4][0]},
                        {gameState[2][0], gameState[3][0], gameState[4][0], gameState[5][0]},
                        {gameState[3][0], gameState[4][0], gameState[5][0], gameState[6][0]},

                        {gameState[0][1], gameState[1][1], gameState[2][1], gameState[3][1]},
                        {gameState[1][1], gameState[2][1], gameState[3][1], gameState[4][1]},
                        {gameState[2][1], gameState[3][1], gameState[4][1], gameState[5][1]},
                        {gameState[3][1], gameState[4][1], gameState[5][1], gameState[6][1]},

                        {gameState[0][2], gameState[1][2], gameState[2][2], gameState[3][2]},
                        {gameState[1][2], gameState[2][2], gameState[3][2], gameState[4][2]},
                        {gameState[2][2], gameState[3][2], gameState[4][2], gameState[5][2]},
                        {gameState[3][2], gameState[4][2], gameState[5][2], gameState[6][2]},

                        {gameState[0][3], gameState[1][3], gameState[2][3], gameState[3][3]},
                        {gameState[1][3], gameState[2][3], gameState[3][3], gameState[4][3]},
                        {gameState[2][3], gameState[3][3], gameState[4][3], gameState[5][3]},
                        {gameState[3][3], gameState[4][3], gameState[5][3], gameState[6][3]},

                        {gameState[0][4], gameState[1][4], gameState[4][4], gameState[3][4]},
                        {gameState[1][4], gameState[2][4], gameState[3][4], gameState[4][4]},
                        {gameState[2][4], gameState[3][4], gameState[4][4], gameState[5][4]},
                        {gameState[3][4], gameState[4][4], gameState[5][4], gameState[6][4]},

                        {gameState[0][5], gameState[1][5], gameState[2][5], gameState[3][5]},
                        {gameState[1][5], gameState[2][5], gameState[3][5], gameState[4][5]},
                        {gameState[2][5], gameState[3][5], gameState[4][5], gameState[5][5]},
                        {gameState[3][5], gameState[4][5], gameState[5][5], gameState[6][5]},

                        {gameState[0][6], gameState[1][6], gameState[2][6], gameState[3][6]},
                        {gameState[1][6], gameState[2][6], gameState[3][6], gameState[4][6]},
                        {gameState[2][6], gameState[3][6], gameState[4][6], gameState[5][6]},
                        {gameState[3][6], gameState[4][6], gameState[5][6], gameState[6][6]},

                        //All diagonal-right win outcomes
                        {gameState[0][0], gameState[1][1], gameState[2][2], gameState[3][3]},
                        {gameState[0][1], gameState[1][2], gameState[2][3], gameState[3][4]},
                        {gameState[0][2], gameState[1][3], gameState[2][4], gameState[3][5]},
                        {gameState[0][3], gameState[1][4], gameState[2][5], gameState[3][6]},

                        {gameState[1][0], gameState[2][1], gameState[3][2], gameState[4][3]},
                        {gameState[1][1], gameState[2][2], gameState[3][3], gameState[4][4]},
                        {gameState[1][2], gameState[2][3], gameState[3][4], gameState[4][5]},
                        {gameState[1][3], gameState[2][4], gameState[3][5], gameState[4][6]},

                        {gameState[2][0], gameState[3][1], gameState[4][2], gameState[5][3]},
                        {gameState[2][1], gameState[3][2], gameState[4][3], gameState[5][4]},
                        {gameState[2][2], gameState[3][3], gameState[4][4], gameState[5][5]},
                        {gameState[2][3], gameState[3][4], gameState[4][5], gameState[6][6]},

                        {gameState[3][0], gameState[4][1], gameState[5][2], gameState[6][3]},
                        {gameState[3][1], gameState[4][2], gameState[5][3], gameState[6][4]},
                        {gameState[3][2], gameState[4][3], gameState[5][4], gameState[6][5]},
                        {gameState[3][3], gameState[4][4], gameState[5][5], gameState[6][6]},

                        //All diagonal-left win outcomes
                        {gameState[3][0], gameState[2][1], gameState[1][2], gameState[0][3]},
                        {gameState[3][1], gameState[2][2], gameState[1][3], gameState[0][4]},
                        {gameState[3][2], gameState[2][3], gameState[1][4], gameState[0][5]},
                        {gameState[3][3], gameState[2][4], gameState[1][5], gameState[0][6]},

                        {gameState[4][0], gameState[3][1], gameState[2][2], gameState[1][3]},
                        {gameState[4][1], gameState[3][2], gameState[2][3], gameState[1][4]},
                        {gameState[4][2], gameState[3][3], gameState[2][4], gameState[1][5]},
                        {gameState[4][3], gameState[3][4], gameState[2][5], gameState[1][6]},

                        {gameState[5][0], gameState[4][1], gameState[3][2], gameState[2][3]},
                        {gameState[5][1], gameState[4][2], gameState[3][3], gameState[2][4]},
                        {gameState[5][2], gameState[4][3], gameState[3][4], gameState[2][5]},
                        {gameState[5][3], gameState[4][4], gameState[3][5], gameState[2][6]},

                        {gameState[6][0], gameState[5][1], gameState[4][2], gameState[3][3]},
                        {gameState[6][1], gameState[5][2], gameState[4][3], gameState[3][4]},
                        {gameState[6][2], gameState[5][3], gameState[4][4], gameState[3][5]},
                        {gameState[6][3], gameState[5][4], gameState[4][5], gameState[3][6]},

                };


        //Return 0 for wins, 1 for losses, and 2 for ties

        return 1;
    }

}