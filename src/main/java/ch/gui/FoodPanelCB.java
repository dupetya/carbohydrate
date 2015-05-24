package ch.gui;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.model.Food;

@SuppressWarnings("serial")
public class FoodPanelCB extends JPanel {

	private Food food;
	private JCheckBox chkBox;

	public FoodPanelCB(Food ing) {
		food = ing;
		setLayout(null);
		setPreferredSize(new Dimension(400, 35));

		chkBox = new JCheckBox("");
		chkBox.setBounds(6, 7, 21, 21);
		add(chkBox);
		
		JLabel lblNewLabel = new JLabel(food.getName());
		lblNewLabel.setBounds(50, 7, 340, 21);
		add(lblNewLabel);
	}
	
	public JCheckBox getRButton() {
		return chkBox;
	}
	
	public boolean isSelected() {
		return chkBox.isSelected();
	}
	
	public Food getFood() {
		return food;
	}

	
}
