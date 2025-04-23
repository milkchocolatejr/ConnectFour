import java.io.Serializable;

public class Message implements Serializable {

    public MessageType messageType;
    public int moveCol;
    public String username;
    public String messageText;
    private static final long serialVersionUID = 42L;
    public String recipient;

    public Message(){

    }

    @Override
    public String toString(){
        return "MessageType: " + messageType + "\n" +
                "Move Col: " + moveCol + "\n" +
                "Username: " + username + "\n" +
                "MessageText: " + messageText + "\n" +
                "Recipient: " + recipient + "\n";
    }
}

