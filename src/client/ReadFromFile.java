package client;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadFromFile extends Thread {
	private int count;
	private Text obj;
	private ObjectInputStream in;
	private final String fileName = "conv.ser";
	
	public ReadFromFile(int count) {
		this.count = count;
	}
	
	@Override
	public void run() {
		File file = new File(fileName);
		if(file.exists()) {
			System.out.println("------------------------------------------");
			try {
				if(count == 0) {
					in = new ObjectInputStream(new FileInputStream(fileName));
					while(true) {
						try {
							obj = (Text) in.readObject();
							System.out.println(obj.getText());
						} catch(EOFException e) {
							break;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} else {
					int msgCount = getTotalMessagesCount();
					in = new ObjectInputStream(new FileInputStream(fileName));
					
					for(int i = 0; i < msgCount-count; i++) {
						try {
							obj = (Text) in.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					
					while(true) {
						try {
							obj = (Text) in.readObject();
							System.out.println(obj.getText());
						} catch(EOFException e) {
							break;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("------------------------------------------");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getTotalMessagesCount() {
		int cnt = 0;
		try {
			in = new ObjectInputStream(new FileInputStream(fileName));
			while(true) {
				try {
					obj = (Text) in.readObject();
					cnt++;
				} catch(EOFException e) {
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}
}
