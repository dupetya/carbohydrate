package ch.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import ch.model.Ingredient;

@SuppressWarnings("serial")
class TableModelIngredientWithWeight extends AbstractTableModel {
	private class Pair {
		private Ingredient first;
		private Double second;

		public Pair(Ingredient ing, Double d) {
			first = ing;
			second = d;
		}

		public Ingredient first() {
			return first;
		}

		public Double second() {
			return second;
		}

		public void set(Double d) {
			second = d;
		}
	}

	List<Pair> pairs;
	private String[] colnames;

	public TableModelIngredientWithWeight() {
		pairs = new ArrayList<Pair>();
		colnames = new String[] { "Név", "Súly" };
	}

	public TableModelIngredientWithWeight(Map<Ingredient, Double> map) {
		this();
		for (Ingredient ing : map.keySet()) {
			pairs.add(new Pair(ing, map.get(ing)));
		}
		if (!pairs.isEmpty()) {
			fireTableRowsInserted(0, pairs.size() - 1);
		}
	}

	public Map<Ingredient, Double> getIngredients() {
		Map<Ingredient, Double> m = new HashMap<Ingredient, Double>();
		for (Pair p : pairs) {
			m.put(p.first(), p.second());
		}
		return m;
	}

	public void addIng(Ingredient ing, Double val) {
		pairs.add(new Pair(ing, val));
		fireTableRowsInserted(pairs.size() - 1, pairs.size() - 1);
	}

	public Ingredient remove(int rowIndex) {
		Ingredient ing = pairs.get(rowIndex).first;
		pairs.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
		return ing;
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 1 && rowIndex < pairs.size()) {
			pairs.get(rowIndex).set((Double) aValue);
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}