public class Game {
    const int BOARD_SIZE = 7;
    boolean playerOneWinning;
    boolean playerTwoWinning;
    boolean gameOver;
    boolean playerOneTurn;
    String playerOneUser;
    String playerTwoUser;
    String displayMessage;
    int gameID;
    Application GUI;
    int[BOARD_SIZE][BOARD_SIZE] gameState;


    public Game(String playerOne, String playerTwo, int gameID) {
        playerOneWinning = false;
        playerTwoWinning = false;
        gameOver = false;
        playerOneTurn = true;
        this.gameID = gameID;
        this.playerOneUser = playerOne;
        this.playerTwoUser = playerTwo;
        this.displayMessage = "Game has started!";
        this.GUI = SceneBuilder.buildGameScreen(NULL, displayMessage);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
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
        this.GUI = SceneBuilder.buildGameScreen(NULL, displayMessage);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                gameState[i][j] = 0;
            }
        }
    }

    public void fillGame(String playerTwo){
        if(playerTwo == "") {
            this.playerTwoUser = playerTwo;
            this.displaMessage = "Game has started!";
            this.GUI = SceneBuider.buildGameScreen(NULL, displayMessage);
        }
    }


    public boolean Play(int col){
        throw new NotImplementedException();
    }
}