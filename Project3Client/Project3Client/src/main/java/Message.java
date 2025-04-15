import java.io.Serializable;

public class Message implements Serializable {
    public boolean isMove;
    public boolean isJoin;
    public int moveCol;
    public String username;
    public String messageText;
    static final long serialVersionUID = 42L;


    public Message(boolean isMove, boolean isJoin, int moveCol, String username, String messageText) {
        this.isMove = isMove;
        this.isJoin = isJoin;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = messageText;
    }

    public toString(){return messageText;}
}
