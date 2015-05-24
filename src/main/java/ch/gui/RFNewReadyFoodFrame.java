package ch.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import ch.dao.FoodDAO;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;
import ch.model.ReadyFood;

@SuppressWarnings("serial")
public class RFNewReadyFoodFrame extends MyFrame {
	private class IngredientTable extends AbstractTableModel {

		private List<Ingredient> ingredients;

		public void add(Ingredient ing) {
			ingredients.add(ing);
		}

		public Ingredient remove(int rowIndex) {
			return ingredients.remove(rowIndex);
		}

		public IngredientTable(List<Ingredient> l) {
			ingredients = l;
		}

		@Override
		public Class<Ingredient> getColumnClass(int columnIndex) {
			return Ingredient.class;
		}

		@Override
		public int getColumnCount() {
			return 1;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return "Név";
		}

		@Override
		public int getRowCount() {
			return ingredients.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return ingredients.get(rowIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		}

	}

	private class IngredientTableWithWeight extends AbstractTableModel {

		private class Pair {
			Ingredient first;
			Double second;

			public Pair(Ingredient ing, Double d) {
				first = ing;
				second = d;
			}

			public void set(Double d) {
				second = d;
			}
		}

		List<Pair> pairs;
		private String[] colnames;

		public IngredientTableWithWeight() {
			pairs = new ArrayList<RFNewReadyFoodFrame.IngredientTableWithWeight.Pair>();
			colnames = new String[] { "Név", "Súly" };
		}

		public Map<Ingredient, Double> getIngredients() {
			Map<Ingredient, Double> m = new HashMap<Ingredient, Double>();
			for (Pair p : pairs) {
				m.put(p.first, p.second);
			}
			return m;
		}

		public void addIng(Ingredient ing, Double val) {
			pairs.add(new Pair(ing, val));
		}

		public Ingredient remove(int rowIndex) {
			Ingredient ing = pairs.get(rowIndex).first;
			pairs.remove(rowIndex);
			return ing;
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Ingredient.class;
			case 1:
				return Double.class;
			}
			return null;
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return colnames[columnIndex];
		}

		@Override
		public int getRowCount() {
			return pairs.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				return pairs.get(rowIndex).first;
			if (columnIndex == 1)
				return pairs.get(rowIndex).second;
			return null;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return (columnIndex > 0) ? true : false;
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == 1 && rowIndex < pairs.size()) {
				pairs.get(rowIndex).set((Double) aValue);
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}

	}

	private JTextField nameTextField;
	private JTable addedTable;
	private JTable removedTable;

	protected RFNewReadyFoodFrame(JFrame parent, List<Ingredient> list) {
		super(parent);
		getContentPane().setLayout(null);
		this.setBounds(100, 100, 519, 365);

		removedTable = new JTable(new IngredientTable(list));
		addedTable = new JTable(new IngredientTableWithWeight());

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
		});
		getContentPane().add(buttonAdd);

		JButton buttonRemove = new JButton(">");
		buttonRemove.setBounds(225, 164, 53, 58);
		buttonRemove.addActionListener(e -> {
			removeIngredient();
		});
		getContentPane().add(buttonRemove);

		nameTextField = new JTextField();
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
				Map<Ingredient, Double> map = ((IngredientTableWithWeight) addedTable
						.getModel()).getIngredients();
				StringBuilder sb = new StringBuilder("rf");
				sb.append(name.charAt(0));
				for (Ingredient ing : map.keySet()) {
					sb.append((int) (map.get(ing) * 51));
				}
				String id = sb.toString();
				ReadyFood rf = new ReadyFood(id);
				rf.setName(name);
				for (Ingredient ing : map.keySet()) {
					rf.addIngredient(ing, map.get(ing));
				}
				
				
				try {
					FoodDAO dao = new FoodXmlDAO();
					dao.insertReadyFood(rf);
					((RFViewFrame)parentFrame).fillPanels(dao.getReadyFoods());
					parentFrame.setVisible(true);
					this.dispose();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(btnOK);

		JButton btnMgsem = new JButton("Mégsem");
		btnMgsem.setBounds(371, 261, 104, 54);
		getContentPane().add(btnMgsem);
	}

	private void addIngredient() {
		int toRemove = removedTable.getSelectedRow();
		if (toRemove > -1) {
			IngredientTable t = (IngredientTable) removedTable.getModel();
			IngredientTableWithWeight tm = (IngredientTableWithWeight) addedTable
					.getModel();

			Ingredient ingToAdd = t.remove(toRemove);
			tm.addIng(ingToAdd, 1.0);

			t.fireTableDataChanged();
			tm.fireTableDataChanged();

		}
		this.repaint();
		this.revalidate();
	}

	private void removeIngredient() {
		int toRemove = addedTable.getSelectedRow();
		if (toRemove > -1) {
			IngredientTable t = (IngredientTable) removedTable.getModel();
			IngredientTableWithWeight tm = (IngredientTableWithWeight) addedTable
					.getModel();

			Ingredient ingToAdd = tm.remove(toRemove);
			t.add(ingToAdd);

			t.fireTableDataChanged();
			tm.fireTableDataChanged();

		}
		this.repaint();
		this.revalidate();
	}
}
