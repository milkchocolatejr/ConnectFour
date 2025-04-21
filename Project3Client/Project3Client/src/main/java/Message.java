import java.io.Serializable;

public class Message implements Serializable {

    public MessageType messageType;
    public int moveCol;
    public String username;
    public String messageText;
    public static final long serialVersionUID = 42L;
    int recipient;

    public Message(int i, boolean connect){
        if(connect) {
            messageType = MessageType.JOIN;
            messageText = "User "+i+" has joined!";
            recipient = i;
        } else {
            messageType = MessageType.ERROR;
            messageText = "User "+i+" has disconnected!";
            recipient = i;
        }
    }

    public Message(String mess){
        messageType = MessageType.TEXT;
        messageText = mess;
        recipient = -1;
    }

    public Message(int rec, String mess){
        messageType = MessageType.TEXT;
        messageText = mess;
        recipient = rec;
    }

    public Message(String m, MessageType messageType) {
        this.messageText = m;
        this.messageType = messageType;
    }

    public Message(){

    }

    /*
    Edit with AJ
    public Message(int moveCol, String username) {
        this.messageType = MessageType.PLAY;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = username + "has played in column " + moveCol + ".";
    }*/

    @Override
    public String toString(){
        return messageText;
    }
}

