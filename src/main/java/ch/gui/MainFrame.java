package ch.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ch.dao.FoodDAO;
import ch.dao.FoodDaoException;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;
import ch.model.ReadyFood;

public class MainFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		Ingredient ig = new Ingredient("asd", "Körte", 1.0, 2.0, 14, 3.0);
		ReadyFood rf = new ReadyFood("asd", "Almalé");
		
		rf.addIngredient(ig, 47.0);
		
		try {
			FoodDAO dao = new FoodXmlDAO();
			dao.insertReadyFood(rf);
			
		} catch (FoodDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
