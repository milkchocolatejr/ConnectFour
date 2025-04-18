import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;


import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Server{

	int count = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;


	Server(){

		server = new TheServer();
		server.start();
	}


	public class TheServer extends Thread{

		public void run() {
			//How the server will first accept a client
			try(ServerSocket mysocket = new ServerSocket(5555);){
				//Prints out to console, and soon for GUI, that the server is waiting for a client
		    System.out.println("Server is waiting for a client!");


		    while(true) {

				ClientThread c = new ClientThread(mysocket.accept(), count);
				//Could include a callback here
				clients.add(c);
				c.start();

				count++;

			    }
			} catch(Exception e) {
				//Thrown exception for if the server doesn't launch for some reason
					System.err.println("Server did not launch");
				}
			}
		}


		class ClientThread extends Thread{


			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			//Client Thread class constructor that's
			//the socket connection and the count # of clients, Ex. Client #1, and Client #2
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;
			}

			public void updateClients(Message message) {
				//Prints out to console toString, or messageText, of the message
				//that all clients are updated with
				System.out.println(message);
					for (ClientThread client : clients) {
						if (this != client) {
							client.send(message);
						}
					}
			}

			public void send(Message message){
                try {
                    out.writeObject(message);
                } catch (IOException e) {
					System.err.println("Message send error");
                    throw new RuntimeException(e);
                }
            }
			public void run(){

				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
				}
				catch(Exception e) {
					System.err.println("Streams not open");
				}

				//updateClients(new Message("new client on server: client #" + count));

				 while(true) {
					    try {
					    	Message data = (Message) in.readObject();
					    	updateClients(data);

					    	}
					    catch(Exception e) {
							e.printStackTrace();
					    	System.err.println("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	//updateClients(new Message("Client #" + count + " has left the server!"));
					    	clients.remove(this);
					    	break;
					    }
				 }
			}//end of run


		}//end of client thread
}






