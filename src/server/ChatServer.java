package server;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ChatServer {
	private static Socket socket;
	private static ServerSocket server;
	private static ClientThread client;
	private static Set<ClientThread> clients;
	private static Set<String> clientNames;
	
	private static Queue<String> msgQueue;
	private static String queueMsgsForUser;
	
	private static final String user1 = "torvalds";
	private static final String user2 = "tanenbaum";
	
	public ChatServer() {
		clients = new HashSet<ClientThread>();
		clientNames = new HashSet<String>();
		msgQueue = new LinkedList<String>();
		queueMsgsForUser = user1;
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Specify only the port number as an argument.");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		
		try {
			server = new ServerSocket(port);
			new ChatServer().acceptClients();
		} catch(IOException e) {
			System.err.println("Cannot listen to port " + port);
			System.exit(1);
		}	
	}
	
	private void acceptClients() {
		System.out.println("\nWaiting for clients to connect...");
		while(true) {
			try {
				socket = server.accept();
				System.out.println("\nClient Accepted with IP: " + socket.getInetAddress());
					
				client = new ClientThread(socket, this);
				Thread thread = new Thread(client);
				clients.add(client);
				thread.start();
			} catch(IOException e) {
				System.out.println("Cannot Accept Client!");
			}
		}
	}
	
	public void broadcast(String message, ClientThread thisClient) throws IOException {
		for(ClientThread client: clients) {
			if(client != thisClient) {
				client.sendMessage(message);
			}
		}
	}
	
	public void addName(String name) {
		clientNames.add(name);
	}
	
	public void removeName(String name) {
		clientNames.remove(name);
	}
	
	public boolean isOnline(String thisUserName) {
		String otherUserName = (thisUserName.equals(user1))? user2: user1;
		return clientNames.contains(otherUserName);
	}
	
	public void removeUser(ClientThread thisClient) {
		clients.remove(thisClient);
	}
	
	public boolean isAuthorized(String name) {
		return name.equals(user1) || name.equals(user2);
	}
	
	public Queue<String> getMsgQueue() {
		return msgQueue;
	}
	
	public String getQueueMsgsForUser() {
		return queueMsgsForUser;
	}
	
	public void setQueueMsgsForUser(String thisUserName) {
		String otherUserName = (thisUserName.equals(user1))? user2: user1;
		queueMsgsForUser = otherUserName;
	}
}
