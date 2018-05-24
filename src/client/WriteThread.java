package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
	private Socket socket;
	private String name;
	private BufferedReader inClient;
	private PrintWriter out;
	
	public WriteThread(Socket socket, String name) throws IOException {
		this.socket = socket;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			inClient = new BufferedReader(new InputStreamReader(System.in));
			out.println(name);
			
			while(!socket.isClosed()) {
				if(inClient.ready()) {
					String message = inClient.readLine();
					if(message != null) {
						if(message.equals(":read")) {
							System.out.print("No. of messages to read (Enter 0 for all): ");
							int count = Integer.parseInt(inClient.readLine());
							new ReadFromFile(count).start();
						} else {
							out.println(message);
							new WriteToFile(message).start();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
