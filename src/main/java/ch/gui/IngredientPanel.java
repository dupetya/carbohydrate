package ch.gui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ch.model.Ingredient;

class IngredientPanel extends JPanel {

	private Ingredient ingredient;
	private JRadioButton rbutton;
	private ActionListener parent;

	public IngredientPanel(ActionListener parent, Ingredient ing) {
		ingredient = ing;
		setLayout(null);
		setPreferredSize(new Dimension(400, 35));

		rbutton = new JRadioButton("");
		rbutton.setBounds(6, 7, 21, 21);
		rbutton.addActionListener(e-> {
			parent.actionPerformed(e);
		});
		add(rbutton);
		
		JLabel lblNewLabel = new JLabel(ingredient.getName());
		lblNewLabel.setBounds(50, 7, 340, 21);
		add(lblNewLabel);
	}
	
	public JRadioButton getRButton() {
		return rbutton;
	}
	
	public boolean isSelected() {
		return rbutton.isSelected();
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}

}