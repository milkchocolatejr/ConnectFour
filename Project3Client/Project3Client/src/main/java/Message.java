import java.io.Serializable;

public class Message implements Serializable {
    public MessageType messageType;
    public int moveCol;
    public String username;
    public String messageText;
    static final long serialVersionUID = 42L;

    //For review: A message containing Game information
    //public Message(Game currentGame) {}

    //A text message, TEXT
    public Message(String m, MessageType messageType) {
        this.messageText = m;
        this.messageType = messageType;
    }

    //A message for a move made in the game, PLAY
    public Message(int moveCol, String username) {
        this.messageType = MessageType.PLAY;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = username + "has played in column " + moveCol + ".";
    }

    //To string for message that just send out the name of the message
    //Prevents the need to access Message.messageText directly for name
    //"Game 1 has just started between Player 1 and Player 2"
    //"Player 1 made a move at ..."
    @Override
    public String toString(){
        return messageText;
    }
}

