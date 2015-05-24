package ch.gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class MyFrame extends JFrame {
	protected JFrame parentFrame;
	protected MyFrame(JFrame parent){
		parentFrame = parent;
	}
}
