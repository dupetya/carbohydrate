package ch.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NumberOnlyTextField extends JTextField {
	private int dots() {
		for (int i = 0; i < getText().length(); i++) {
			if(getText().charAt(i) == '.')
				return 1;
		}
		return 0;
	}
	public NumberOnlyTextField(double d) {
		super(String.valueOf(d));
		this.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e){
				char c = e.getKeyChar();
				if((c < '0'|| c>'9') && c!='\b'){
					if(c != '.' || dots() > 0){
						e.consume();
					}	
				}
			}
		});
	}
}
