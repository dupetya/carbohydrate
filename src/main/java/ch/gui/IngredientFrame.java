package ch.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.dao.FoodDAO;
import ch.dao.FoodDaoException;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;

@SuppressWarnings("serial")
public class IngredientFrame extends MyFrame implements ActionListener {
	private JScrollPane scrollPane;
	private JPanel ipanelContainer;
	private List<IngredientPanel> ipanels;

	private JButton btnNew;
	private JButton btnModify;
	private JButton btnDelete;

	public IngredientFrame(JFrame parent) {
		super(parent);
		this.setBounds(100, 100, 553, 300);
		ipanels = new ArrayList<IngredientPanel>();
		setTitle("Hozzávalók");
		setResizable(false);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 259, 424, -246);
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
			NewIngredientFrame nif = new NewIngredientFrame(this);
			this.setVisible(false);
			nif.setVisible(true);
		});
		getContentPane().add(btnNew);

		btnModify = new JButton("Módosítás");
		btnModify.setBounds(448, 62, 89, 40);
		getContentPane().add(btnModify);

		btnDelete = new JButton("Törlés");
		btnDelete.setBounds(448, 113, 89, 40);
		getContentPane().add(btnDelete);

		loadIngredients();
	}

	private void loadIngredients() {
		try {
			FoodDAO dao = new FoodXmlDAO();
			List<Ingredient> list = dao.getIngredients();
			for (Ingredient ingredient : list) {
				IngredientPanel ipan = new IngredientPanel(ingredient);
				ipanels.add(ipan);
				ipanelContainer.add(ipan);
				repaint();
			}
			revalidate();
		} catch (FoodDaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (IngredientPanel ingredientPanel : ipanels) {

		}

	}
}
