package ch.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ch.model.Ingredient;

@SuppressWarnings("serial")
class TableModelIngredient extends AbstractTableModel {

	private List<Ingredient> ingredients;

	public void add(Ingredient ing) {
		ingredients.add(ing);
		fireTableRowsInserted(ingredients.size() - 1,
				ingredients.size() - 1);
	}

	public Ingredient remove(int rowIndex) {
		Ingredient removed = ingredients.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
		return removed;
		
	}

	public TableModelIngredient(List<Ingredient> l) {
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