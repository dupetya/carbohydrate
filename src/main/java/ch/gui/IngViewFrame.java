package ch.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import ch.dao.FoodDAO;
import ch.dao.FoodDaoException;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;


@SuppressWarnings("serial")
public class IngViewFrame extends MyFrame implements ActionListener {
	private JScrollPane scrollPane;
	private JPanel ipanelContainer;
	private List<FoodPanelRB> ipanels;

	private FoodPanelRB selected;

	private JButton btnNew;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnVissza;

	public IngViewFrame(JFrame parent) {
		super(parent);
		selected = null;
		this.setBounds(100, 100, 553, 300);
		ipanels = new ArrayList<FoodPanelRB>();
		setTitle("Hozzávalók");
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(14, 11, 424, 246);
		getContentPane().add(scrollPane);

		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BorderLayout(0, 0));
		scrollPane.setViewportView(tempPanel);

		ipanelContainer = new JPanel();
		tempPanel.add(ipanelContainer, BorderLayout.NORTH);
		ipanelContainer.setLayout(new GridLayout(0, 1, 0, 1));

		btnNew = new JButton("Új");
		btnNew.setBounds(448, 11, 89, 40);
		btnNew.addActionListener(e -> {
			IngNewIngredientFrame nif = new IngNewIngredientFrame(this);
			this.setVisible(false);
			nif.setVisible(true);
		});
		getContentPane().add(btnNew);

		btnModify = new JButton("Módosítás");
		btnModify.addActionListener(e -> {
			if (selected != null) {
				IngModifyFrame miFrame = new IngModifyFrame(this,
						(Ingredient) selected.getFood());
				miFrame.setVisible(true);
				this.setVisible(false);
			}
		});
		btnModify.setBounds(448, 62, 89, 40);
		getContentPane().add(btnModify);

		btnDelete = new JButton("Törlés");
		btnDelete.setBounds(448, 113, 89, 40);
		btnDelete.addActionListener(e -> {
			if (selected != null) {
				try {
					FoodDAO dao = new FoodXmlDAO();
					dao.deleteIngredient((Ingredient) selected.getFood());
					fillPanels(dao.getIngredients());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		getContentPane().add(btnDelete);

		btnVissza = new JButton("Vissza");
		btnVissza.setBounds(448, 196, 89, 61);
		btnVissza.addActionListener(e -> {
			this.parentFrame.setVisible(true);
			this.dispose();
		});
		getContentPane().add(btnVissza);
		
		JButton btnImport = new JButton("Import");
		btnImport.setBounds(448, 164, 89, 23);
		btnImport.addActionListener(e -> {
			try {
				FoodDAO dao = new FoodXmlDAO();
				List<Ingredient> present = new ArrayList<Ingredient>();
				for (FoodPanelRB foodPanelRB : ipanels) {
					present.add((Ingredient)foodPanelRB.getFood());
				}
				
				List<Ingredient> all = dao.getIngredients();
				
				for (Ingredient ing : present) {
					all.remove(ing);
				}
				

				for (Ingredient ing : all) {
					FoodPanelRB ipan = new FoodPanelRB(this, ing);
					ipanels.add(ipan);
					ipanelContainer.add(ipan);
				}
				this.repaint();
				this.revalidate();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		getContentPane().add(btnImport);

		loadIngredients();

	}

	private void loadIngredients() {
		try {
			FoodDAO dao = new FoodXmlDAO();
			fillPanels(dao.getIngredients());
		} catch (FoodDaoException e) {
			e.printStackTrace();
		}
	}

	void fillPanels(List<Ingredient> ingredients) {
		ipanelContainer.removeAll();
		ipanels.clear();

		Collections.sort(ingredients, (o1, o2) -> {
			return o1.getName().compareTo(o2.getName());
		});

		for (Ingredient ingredient : ingredients) {
			FoodPanelRB ipan = new FoodPanelRB(this, ingredient);
			ipanels.add(ipan);
			ipanelContainer.add(ipan);
		}
		this.repaint();
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (selected != null) {
			selected.getRButton().setSelected(false);
			selected = null;
		}
		for (FoodPanelRB ingredientPanel : ipanels) {
			if (e.getSource() == ingredientPanel.getRButton()) {
				selected = ingredientPanel;
				selected.getRButton().setSelected(true);
				return;
			}
		}

	}
}
