import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread{
	Socket socketClient;

	ObjectOutputStream out;
	ObjectInputStream in;

	Message requestMessage, responseMessage;
	Stage currentStage;


	public ClientThread(Message request, Stage stage){
		this.responseMessage = null;
		this.requestMessage = request;
		this.currentStage = stage;
	}

	public void run() {
		try {
			socketClient = new Socket("127.0.0.1", 5555);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);

			// Send the request
			if (requestMessage != null) {
				send(this.requestMessage);
				System.out.println("CLIENT SENT " + this.requestMessage.messageType);
			}

			// Only process the first response immediately (e.g., JOIN_ACCEPT)
			Message responseMessage = (Message) in.readObject();
			System.out.println("CLIENT GOT " + responseMessage.messageType);
			ClientMessageHandler.handle(responseMessage, this.currentStage);

			// Start listening for further messages
			new ClientListener(in, this.currentStage).start();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public void send(Message data) {
		try {
			out.writeObject(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	class ClientListener extends Thread {
		private ObjectInputStream in;
		private Stage stage;

		public ClientListener(ObjectInputStream in, Stage stage) {
			this.in = in;
			this.stage = stage;
		}

		public void run() {
			try {
				while (true) {
					Message message = (Message) in.readObject();
					System.out.println("CLIENT LISTENER GOT " + message.messageType);
					ClientMessageHandler.handle(message, stage);
				}
			} catch (Exception e) {
				System.out.println("CLIENT LISTENER CLOSED");
				e.printStackTrace();
			}
		}
	}

}
