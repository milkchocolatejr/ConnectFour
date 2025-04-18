import java.io.Serializable;

public class Message implements Serializable {
    public MessageType messageType;
    public int moveCol;
    public String username;
    public String messageText;
    public static final long serialVersionUID = 42L;

    public Message(String m, MessageType messageType) {
        this.messageText = m;
        this.messageType = messageType;
    }

    public Message(int moveCol, String username) {
        this.messageType = MessageType.PLAY;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = username + "has played in column " + moveCol + ".";
    }

    @Override
    public String toString(){
        return messageText;
    }
}

