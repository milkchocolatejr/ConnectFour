import java.io.Serializable;

public class Message implements Serializable {

    public MessageType messageType;
    public int moveCol;
    public String username;
    public String messageText;
    public static final long serialVersionUID = 42L;
    public String recipient;

    //Connection message - JOIN/DISCONNECT from server
    public Message(String s, boolean connect){
        if(connect) {
            messageType = MessageType.JOIN;
            messageText = "User '" +s+"' has joined!";
            recipient = s;
        } else {
            messageType = MessageType.DISCONNECT;
            messageText = "User '"+s+"' has disconnected!";
            recipient = s;
        }
    }

    //Basic Text message to all clients
    public Message(String mess){
        messageType = MessageType.TEXT;
        messageText = mess;
    }

    //Text Message to Direct Recipient
    public Message(String rec, String mess){
        messageType = MessageType.TEXT;
        messageText = mess;
        recipient = rec;
    }

    //Template message of any type
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

