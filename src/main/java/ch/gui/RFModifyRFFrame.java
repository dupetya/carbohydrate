package ch.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import ch.dao.FoodDAO;
import ch.dao.FoodDaoException;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;
import ch.model.ReadyFood;

@SuppressWarnings("serial")
public class RFModifyRFFrame extends MyFrame {

	private ReadyFood trans;
	private JTextField nameTextField;
	private JTable addedTable;
	private JTable removedTable;

	private List<Ingredient> initRemovedList() {
		List<Ingredient> res;
		try {
			FoodDAO dao = new FoodXmlDAO();
			res = dao.getIngredients();

			for (Ingredient ing : trans.getIngredients()) {
				res.remove(ing);
			}
		} catch (FoodDaoException e) {
			res = new ArrayList<Ingredient>();
		}
		return res;
	}

	public RFModifyRFFrame(JFrame parent, ReadyFood rfood) {
		super(parent);
		trans = rfood;
		getContentPane().setLayout(null);
		this.setBounds(100, 100, 519, 365);

		removedTable = new JTable(new TableModelIngredient(initRemovedList()));
		addedTable = new JTable(new TableModelIngredientWithWeight(
				trans.getIngredientsTable()));

		JScrollPane scrollPaneAdded = new JScrollPane(addedTable);
		scrollPaneAdded.setBounds(10, 11, 205, 239);
		getContentPane().add(scrollPaneAdded);

		JScrollPane scrollPaneRemoved = new JScrollPane(removedTable);
		scrollPaneRemoved.setBounds(288, 11, 205, 239);
		getContentPane().add(scrollPaneRemoved);

		JButton buttonAdd = new JButton("<");
		buttonAdd.setBounds(225, 42, 53, 58);
		buttonAdd.addActionListener(e -> {
			addIngredient();
			revalidate();
		});
		getContentPane().add(buttonAdd);

		JButton buttonRemove = new JButton(">");
		buttonRemove.setBounds(225, 164, 53, 58);
		buttonRemove.addActionListener(e -> {
			removeIngredient();
		});
		getContentPane().add(buttonRemove);

		nameTextField = new JTextField(trans.getName());
		nameTextField.setBounds(46, 261, 169, 20);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblNv = new JLabel("Név: ");
		lblNv.setBounds(10, 261, 62, 20);
		getContentPane().add(lblNv);

		JButton btnOK = new JButton("OK");
		btnOK.setBounds(245, 261, 104, 54);
		btnOK.addActionListener(e -> {
			if (!nameTextField.getText().trim().isEmpty()) {
				String name = nameTextField.getText().trim();
				Map<Ingredient, Double> map = ((TableModelIngredientWithWeight) addedTable
						.getModel()).getIngredients();
				if (!map.isEmpty()) {
					ReadyFood rf = new ReadyFood(trans.getId());
					rf.setName(name);
					for (Ingredient ing : map.keySet()) {
						rf.addIngredient(ing, map.get(ing));
					}

					try {
						FoodDAO dao = new FoodXmlDAO();
						dao.updateReadyFood(rf);
						((RFViewFrame) parentFrame).fillPanels(dao
								.getReadyFoods());
						parentFrame.setVisible(true);
						this.dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		getContentPane().add(btnOK);

		JButton btnMgsem = new JButton("Mégsem");
		btnMgsem.setBounds(371, 261, 104, 54);
		btnMgsem.addActionListener(e -> {
			parentFrame.setVisible(true);
			this.dispose();
		});
		getContentPane().add(btnMgsem);
	}

	private void addIngredient() {
		int toRemove = removedTable.getSelectedRow();
		if (toRemove > -1) {
			TableModelIngredient t = (TableModelIngredient) removedTable
					.getModel();
			TableModelIngredientWithWeight tm = (TableModelIngredientWithWeight) addedTable
					.getModel();

			Ingredient ingToAdd = t.remove(toRemove);
			tm.addIng(ingToAdd, 1.0);
		}
		this.repaint();
		this.revalidate();
	}

	private void removeIngredient() {
		int toRemove = addedTable.getSelectedRow();
		if (toRemove > -1) {
			TableModelIngredient t = (TableModelIngredient) removedTable
					.getModel();
			TableModelIngredientWithWeight tm = (TableModelIngredientWithWeight) addedTable
					.getModel();

			Ingredient ingToAdd = tm.remove(toRemove);
			t.add(ingToAdd);
		}
		this.repaint();
		this.revalidate();
	}

}
