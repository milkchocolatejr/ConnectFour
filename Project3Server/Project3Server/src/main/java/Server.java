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
	private Consumer<Message> callback;


	Server(Consumer<Message> call){

		callback = call;
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
				//Count increases when new clients enter, and callback accepts a message
				callback.accept(new Message(count,true));
				clients.add(c);
				c.start();

				count++;

			    }
			} catch(Exception e) {
				//Thrown exception for if the server doesn't launch for some reason
				callback.accept(new Message("Server did not launch"));
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

			/*
			Old update clients
			public void updateClients(Message message) {
				//Prints out to console toString, or messageText, of the message
				//that all clients are updated with
				System.out.println(message);
					for (ClientThread client : clients) {
						if (this != client) {
							client.send(message);
						}
					}
			}*/

			//Current update clients based on Prof. code
			public void updateClients(Message message) {
				switch(message.messageType){
					case TEXT:
						for(ClientThread t: clients){
							if(message.recipient==-1 || message.recipient==t.count ) {
								try {
									t.out.writeObject(message);
								} catch (Exception e) {
									System.err.println("New User Error");
								}
							}
						}
						break;
					case JOIN:
						for(ClientThread t : clients) {
							if(this != t) {
								try {
									t.out.writeObject(message);
								} catch (Exception e) {
									System.err.println("New User Error");
								}
							}
						}
						break;
					case ERROR:
						for(ClientThread t : clients) {
							try {
								t.out.writeObject(message);
							} catch (Exception e) {
								System.err.println("New User Error");
							}
						}
				}

			}

			/*public void send(Message message){
                try {
                    out.writeObject(message);
                } catch (IOException e) {
					System.err.println("Message send error");
                    throw new RuntimeException(e);
                }
            }*/

			public void run(){

				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
				}
				catch(Exception e) {
					System.err.println("Streams not open");
				}

				updateClients(new Message(count,true));

				 while(true) {
					    try {
					    	Message data = (Message) in.readObject();
					    	updateClients(data);

					    	}
					    catch(Exception e) {
							e.printStackTrace();
							Message discon = new Message(count, false);
							System.err.println("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
							callback.accept(discon);
							updateClients(discon);
							clients.remove(this);
							break;
					    }
				 }
			}//end of run


		}//end of client thread
}






