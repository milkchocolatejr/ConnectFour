import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;

	Game currentGame;
	String username;
	int gameID;

	public void run() {
		try {
			socketClient= new Socket("127.0.0.1",5555);
	    	out = new ObjectOutputStream(socketClient.getOutputStream());
	    	in = new ObjectInputStream(socketClient.getInputStream());
	   	 	socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}


		while(true) {
			try {
				Message message = (Message) in.readObject();
				ClientMessageHandler.handle(message);
			}
			catch(Exception e) {
				//e.printStackTrace();
			}
		}
	
    }


	public void send(Message data) {
		try {
			out.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
