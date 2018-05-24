package client;

import java.io.*;
import java.net.*;

public class Client {
	private static Socket socket;

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Specify the ip and port as arguments.");
			System.exit(1);
		}

		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		System.out.print("Enter username: ");
		Console console = System.console();
		String name = console.readLine();
		
		try {
			socket = new Socket(ip, port);
			Thread.sleep(1000);
			new ReadThread(socket).start();
			new WriteThread(socket, name).start();
		} catch(IOException e) {
			System.err.println("Cannot connect!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("Cannot Connect!");
			e.printStackTrace();
		}
	}

}
