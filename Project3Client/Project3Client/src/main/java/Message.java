import java.io.Serializable;

public class Message implements Serializable {
    public boolean isMove;
    public boolean isJoin;
    public int moveCol;
    public String username;
    public String messageText;
    static final long serialVersionUID = 42L;

    public Message(String username, String messageText) {
        this.messageText = messageText;
        this.isMove = false;
        this.isJoin = false;
        this.username = username;
    }
    public Message(boolean isJoin, String username, String messageText) {
        this.isJoin = isJoin;
        this.messageText = messageText;
        this.username = username;
        this.isMove = false;
    }
    public Message(boolean isMove, int moveCol, String username, String messageText) {
        this.isMove = isMove;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = messageText;
        this.isJoin = false;
    }
    public Message(boolean isMove, boolean isJoin, int moveCol, String username, String messageText) {
        this.isMove = isMove;
        this.isJoin = isJoin;
        this.moveCol = moveCol;
        this.username = username;
        this.messageText = messageText;
    }

    @Override
    public String toString(){
        return messageText;
    }
}
