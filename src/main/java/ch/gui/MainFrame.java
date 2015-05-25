package ch.gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends MyFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		super(null);
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setTitle("CarboHydrate");
		this.setBounds(100, 100, 283, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JButton buttonIngredients = new JButton("Hozzávalók");
		buttonIngredients.setBounds(48, 31, 169, 49);
		buttonIngredients.addActionListener(e-> {
				IngViewFrame iFrame = new IngViewFrame(this);
				this.setVisible(false);
				iFrame.setVisible(true);
			});
		this.getContentPane().add(buttonIngredients);
		
		JButton buttonReadyFood = new JButton("Készételek");
		buttonReadyFood.setBounds(48, 107, 169, 49);
		buttonReadyFood.addActionListener(e -> {
			RFViewFrame rfvf = new RFViewFrame(this);
			rfvf.setVisible(true);
			this.setVisible(false);
		});
		this.getContentPane().add(buttonReadyFood);
		
		JButton btnExit = new JButton("Kilépés");
		btnExit.setBounds(48, 185, 169, 49);
		btnExit.addActionListener(e -> {
			this.dispose();
		});
		this.getContentPane().add(btnExit);
	}
}
