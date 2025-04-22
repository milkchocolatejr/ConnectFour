import java.util.ArrayList;

public class MessageHandlerResponse{
    ArrayList<Game> gameList;
    Message message;
    public MessageHandlerResponse(ArrayList<Game> gameList, Message message){
        this.gameList = gameList;
        this.message = message;
    }
}
