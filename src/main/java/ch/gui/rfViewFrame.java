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
import ch.model.ReadyFood;

@SuppressWarnings("serial")
public class RFViewFrame extends MyFrame implements ActionListener {

	private List<FoodPanelRB> fPanels;
	private JPanel fPanelContainer;
	private JScrollPane scrollPane;

	private FoodPanelRB selected;

	private JButton btnNew;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnVissza;

	public RFViewFrame(JFrame parent) {
		super(parent);
		selected = null;
		this.setBounds(100, 100, 553, 300);
		fPanels = new ArrayList<FoodPanelRB>();
		setTitle("Készételek");
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

		fPanelContainer = new JPanel();
		tempPanel.add(fPanelContainer, BorderLayout.NORTH);
		fPanelContainer.setLayout(new GridLayout(0, 1, 0, 1));

		btnNew = new JButton("Új");
		btnNew.setBounds(448, 11, 89, 40);
		btnNew.addActionListener(e -> {
			FoodDAO dao;
			try {
				dao = new FoodXmlDAO();
				RFNewReadyFoodFrame newRFF = new RFNewReadyFoodFrame(this, dao
						.getIngredients());
				newRFF.setVisible(true);
				this.setVisible(false);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		getContentPane().add(btnNew);

		btnModify = new JButton("Módosítás");
		btnModify.setBounds(448, 62, 89, 40);
		btnModify.addActionListener(e->{
			if(selected != null) {
			RFModifyRFFrame fram = new RFModifyRFFrame(this, (ReadyFood) selected.getFood());
			this.setVisible(false);
			fram.setVisible(true);
			}
		});
		getContentPane().add(btnModify);

		btnDelete = new JButton("Törlés");
		btnDelete.addActionListener(e -> {
			if(selected != null) {
				try {
					FoodDAO dao = new FoodXmlDAO();
					dao.deleteReadyFood((ReadyFood) selected.getFood());
					fillPanels(dao.getReadyFoods());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(448, 113, 89, 40);
		getContentPane().add(btnDelete);

		btnVissza = new JButton("Vissza");
		btnVissza.setBounds(448, 196, 89, 61);
		btnVissza.addActionListener(e -> {
			this.parentFrame.setVisible(true);
			this.dispose();
		});
		getContentPane().add(btnVissza);

		loadReadyFoods();

	}

	private void loadReadyFoods() {
		try {
			FoodDAO dao = new FoodXmlDAO();
			fillPanels(dao.getReadyFoods());
		} catch (FoodDaoException e) {
			e.printStackTrace();
		}
	}

	void fillPanels(List<ReadyFood> rfoods) {
		fPanelContainer.removeAll();
		fPanels.clear();

		Collections.sort(rfoods, (o1, o2) -> {
			return o1.getName().compareTo(o2.getName());
		});

		for (ReadyFood rf : rfoods) {
			FoodPanelRB ipan = new FoodPanelRB(this, rf);
			fPanels.add(ipan);
			fPanelContainer.add(ipan);
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
		for (FoodPanelRB fPanel : fPanels) {
			if (e.getSource() == fPanel.getRButton()) {
				selected = fPanel;
				selected.getRButton().setSelected(true);
				return;
			}
		}

	}

}
