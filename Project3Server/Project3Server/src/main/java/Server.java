import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;


import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class Server{

	int count = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private final Consumer<Message> callback;
	Stage stage;


	Server(Consumer<Message> call, Stage stage){
		callback = call;
		server = new TheServer(this);
		server.start();
		this.stage = stage;
	}


	public void addGame(Game game){
		for(ClientThread c : clients){
			if(Objects.equals(game.playerOneUser, c.username)){
				c.setGame(game);
			}
		}
	}
	public ArrayList<Game> getGames(){
		ArrayList<Game> games = new ArrayList<>();
		ArrayList<Integer> seenIDs = new ArrayList<>();
		for(ClientThread c : clients){
			if(c.game == null){
				continue;
			}
			if(!seenIDs.contains(c.game.gameID)){
				games.add(c.game);
				seenIDs.add(c.game.gameID);
			}
		}
		return games;
	}

	public ArrayList<String> getUsers(){
		ArrayList<String> users = new ArrayList<>();
		for(ClientThread c : clients){
			if(!users.contains(c.username)){
				users.add(c.username);
			}
		}
		return users;
	}

	public class TheServer extends Thread{
		Server server;

		public TheServer(Server server){
			this.server = server;
		}
		public void run() {
			//How the server will first accept a client
			try(ServerSocket mysocket = new ServerSocket(5555)){
				//Prints out to console, and soon for GUI, that the server is waiting for a client
				while(true) {
					ClientThread c = new ClientThread(mysocket.accept(), count, "", null, stage, server);
					//Count increases when new clients enter, and callback accepts a message
					clients.add(c);
					c.start();
					count++;
				}
			}
			catch(Exception e) {
				//Thrown exception for if the server doesn't launch for some reason
			}
		}
	}


		class ClientThread extends Thread{

			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			String username;
			Game game;
			Stage stage;
			Server server;

			//Client Thread class constructor that's
			//the socket connection and the count # of clients, Ex. Client #1, and Client #2
			ClientThread(Socket s, int count, String username, Game game, Stage stage, Server server){
				this.connection = s;
				this.count = count;
				this.username = username;
				this.game = game;
				this.stage = stage;
				this.server = server;
			}

			public void setGame(Game game){
				this.game = game;
			}

			public void send(Message data) {
				try {
					out.writeObject(data);
					System.out.println("SERVER SENT " + data.messageType + " TO " + (data.recipient == null ? "null" : data.recipient));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void run(){
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
					Message data = (Message) in.readObject();
					System.out.println("SERVER GOT :" + data.messageType);

					Game g = MessageHandler.handle(data, stage, server);

					switch(data.messageType){
						case JOIN:
							if(g != null){
								Message m = new Message();
								m.messageType = MessageType.JOIN_ACCEPT;
								callback.accept(m);
								setGame(g);
							}
							break;
						case START:
							/*if(g != null){
								Message m = new Message();
								m.messageType = MessageType.START;
								callback.accept(m);
								setGame(g);
							}*/
							break;
						case PLAY:
							setGame(g);
							break;
					}


				}
				catch(Exception e) {
					System.err.println("Streams not open");
				}
			}//end of run


		}//end of client thread
}



