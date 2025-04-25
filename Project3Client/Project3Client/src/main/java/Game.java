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
        System.out.println("CLIENT GAME START!");
        System.out.println("Player 1: " + playerOneUser);
        System.out.println("Player 2: " + playerTwoUser);
        System.out.println("=============");
    }

    public void Play(String username, int col){
        if(isInvalidPlay(username, col)){
            return;
        }

        for(int row = BOARD_SIZE - 1; row >= 0; row--){
            if(gameState[row][col] == 0){
                gameState[row][col] = (playerOneTurn ? 1 : 2);
                break;
            }
        }

        playerOneTurn = !playerOneTurn;
        displayMessage = getStatus();
    }

    public boolean GameOver(){
        if(outcome() == 0 && boardFull())
        {
            System.out.println("GAME TIE, NO ONE WINS");
            gameOver = true;
            return true;
        }
        if(outcome() == 1)
        {
            System.out.println("PLAYER ONE " + playerOneUser + " WINS!");
            System.out.println("PLAYER TWO " + playerTwoUser + " LOSES!");
            playerOneWinning = true;
            playerTwoWinning = false;
            gameOver = true;
            return true;
        }
        if(outcome() == 2)
        {
            System.out.println("PLAYER TWO " + playerTwoUser +  " WINS!");
            System.out.println("PLAYER ONE " + playerOneUser + " LOSES!");
            playerOneWinning = false;
            playerTwoWinning = true;
            gameOver = true;
            return true;
        }
        return false;
    }

    public void resetGame(){
        this.playerOneWinning = false;
        this.playerTwoWinning = false;
        this.playerOneTurn = true;
        this.displayMessage = getStatus();
        this.playerOneWinning = false;
        this.playerTwoWinning = false;
        this.gameOver = false;
        this.playerOneTurn = true;
        this.started = true;

        this.gameState = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
        Start();
    }

    public boolean isInvalidPlay(String username, int col){
        if(!started){
            return true;
        }
        if((playerOneUser.equals(username) && playerOneTurn) || (playerTwoUser.equals(username) && !playerOneTurn)){
            System.out.println("P1 : " + playerOneUser);
            System.out.println("P2 : " + playerTwoUser);
            System.out.println("Player " + (playerOneTurn ? 1 : 2) + "'s turn");
            return gameState[0][col] != 0;
        }
        return true;
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

        //Return 0 for tie, players win nor lose
        //Return 1 for player one win and player two loss
        //Return 2 for player one loss and player two win
        for(int i = 0; i < boardPairs.length; i++)
        {
            if( (boardPairs[i][0] == 1) && (boardPairs[i][1] == 1)
                    && (boardPairs[i][2] == 1) && (boardPairs[i][3] == 1) )
            {
                return 1;
            }
            if( (boardPairs[i][0] == 2) && (boardPairs[i][1] == 2)
                    && (boardPairs[i][2] == 2) && (boardPairs[i][3] == 2) )
            {
                return 2;
            }
        }

        return 0;
    }

    public boolean boardFull() {
        for (int[] boxRows : gameState) {
            for (int box : boxRows) {
                if (box == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}