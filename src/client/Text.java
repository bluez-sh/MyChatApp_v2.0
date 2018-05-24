package client;

import java.io.Serializable;

public class Text implements Serializable {
	private static final long serialVersionUID = 112123L;
	private String text;
	
	public Text(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
