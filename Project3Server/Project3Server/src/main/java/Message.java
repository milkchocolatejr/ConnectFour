import java.io.Serializable;

public class Message implements Serializable {
    static final long serialVersionUID = 42L;

    int recipient;
    String message;

    public Message(String input){
        message = input;
    }
    public Message(int rec, String input){
        recipient = rec;
        message = input;
    }
    public String toString(){
        return message;
    }
}
