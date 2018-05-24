package server;

import java.net.*;
import java.io.*;

public class ClientThread implements Runnable {
	private Socket socket;
	private String name;
	private BufferedReader inClient;
	private ChatServer server;
	private PrintWriter out;
	private String message;
	
	public ClientThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			inClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			socket.setSoTimeout(60000);
			
			name = inClient.readLine();
			if(server.isAuthorized(name)) {
				server.addName(name);
				sendMessage("You are connected!\n");
				server.broadcast(name + " joined. :)", this);						
				
				if(server.getQueueMsgsForUser().equals(name)) {
					while(!server.getMsgQueue().isEmpty()) {
						message = server.getMsgQueue().remove();
						sendMessage(message);
					}
				}
				
				do {
					message = inClient.readLine();
					if(message != null) {
						if(server.isOnline(name)) {
							server.broadcast("[" + name + "]: " + message, this);
						}
						else {
							server.setQueueMsgsForUser(name);
							server.getMsgQueue().add("[" + name + "]: " + message);
						}
					}
				} while(!message.equals("tata"));
			}
			sendMessage("You are disconnected.");
			this.endConnection();
		} catch(SocketTimeoutException e) {
			try {
				sendMessage("You are disconnected.");
				this.endConnection();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endConnection() throws IOException {
		server.removeUser(this);
		server.removeName(name);
		socket.close();
		server.broadcast(name + " left. :(", this);
	}
	
	public void sendMessage(String message) throws IOException {
		out.println(message);
	}
}
