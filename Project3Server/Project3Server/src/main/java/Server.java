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
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			} catch(Exception e) {
					System.err.println("Server did not launch");
				}
			}
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public void updateClients(Message message) {
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


	
	

	
