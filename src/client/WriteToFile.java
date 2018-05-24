package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteToFile extends Thread{
	private String text;
	private final String fileName = "conv.ser";
	private ObjectOutputStream out;
	
	public WriteToFile(String text) {
		this.text = text;
	}
	
	@Override
	public void run() {
		Text obj = new Text(text);
		try {
			File file = new File(fileName); 
			if(!file.exists()) {
				file.createNewFile();
				out = new ObjectOutputStream(new FileOutputStream(fileName));
			} else {
				out = new ObjectOutputStream(new FileOutputStream(fileName, true)) {
					@Override
					protected void writeStreamHeader() throws IOException {
						reset();
					}
				};
			}
			
			out.writeObject(obj);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
