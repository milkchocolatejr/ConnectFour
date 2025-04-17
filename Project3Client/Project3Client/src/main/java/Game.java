import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        playerOneWinning = false;
        playerTwoWinning = false;
        playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = playerTwo;
        this.displayMessage = "Game has started!";

        this.GUI = SceneBuilder.buildGameScreen(makeBoard(gameState));
        gameState = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
        Start();
    }

    public Game(String playerOne, int gameID){
        playerOneWinning = false;
        playerTwoWinning = false;
        gameOver = false;
        playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = "";
        this.displayMessage = "Waiting... | Code: " + gameID;

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }

        this.GUI = SceneBuilder.buildGameScreen(makeBoard(this.gameState));
    }

    public void fillGame(String playerTwo){
        if(this.playerTwoUser == "") {
            this.playerTwoUser = playerTwo;
            this.displayMessage = "Game has started!";
            this.GUI = SceneBuilder.buildGameScreen(makeBoard(this.gameState));
        }
        Start();
    }

    private void Start(){

    }

    public boolean Play(int col){
        throw new NotImplementedException();
    }

    private Shape makeBoard(int[][] gameBoard){
        throw new NotImplementedException();
    }
}