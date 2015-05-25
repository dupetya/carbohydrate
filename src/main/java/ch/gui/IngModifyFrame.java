package ch.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ch.dao.FoodDAO;
import ch.dao.FoodXmlDAO;
import ch.model.Ingredient;

@SuppressWarnings("serial")
public class IngModifyFrame extends MyFrame {

	private JTextField nameTF;
	private JTextField caloriesTF;
	private JTextField carbonHydTF;
	private JTextField proteinTF;
	private JTextField fatTF;
	
	private Ingredient transfer;

	public IngModifyFrame(JFrame parent, Ingredient trans) {
		super(parent);
		transfer = trans;
		setAlwaysOnTop(true);
		setTitle("Hozzávaló módosítás");
		setBounds(100, 100, 450, 181);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblNv = new JLabel("Név");
		lblNv.setBounds(10, 11, 107, 20);
		getContentPane().add(lblNv);

		JLabel lblKalria = new JLabel("Kalória");
		lblKalria.setBounds(10, 37, 107, 20);
		getContentPane().add(lblKalria);

		JLabel lblSznhidrt = new JLabel("Szénhidrát");
		lblSznhidrt.setBounds(10, 63, 107, 20);
		getContentPane().add(lblSznhidrt);

		JLabel lblFehrje = new JLabel("Fehérje");
		lblFehrje.setBounds(10, 89, 107, 20);
		getContentPane().add(lblFehrje);

		JLabel lblZsr = new JLabel("Zsír");
		lblZsr.setBounds(10, 115, 107, 20);
		getContentPane().add(lblZsr);

		nameTF = new JTextField(transfer.getName());
		nameTF.setBounds(138, 11, 141, 20);
		getContentPane().add(nameTF);

		caloriesTF = new NumberOnlyTextField(transfer.getCalories());
		caloriesTF.setBounds(138, 37, 141, 20);
		getContentPane().add(caloriesTF);

		carbonHydTF = new NumberOnlyTextField(transfer.getCarbons());
		carbonHydTF.setBounds(138, 63, 141, 20);
		getContentPane().add(carbonHydTF);

		proteinTF = new NumberOnlyTextField(transfer.getProteins());
		proteinTF.setBounds(138, 89, 141, 20);
		getContentPane().add(proteinTF);

		fatTF = new NumberOnlyTextField(transfer.getFat());
		fatTF.setBounds(138, 115, 141, 20);
		getContentPane().add(fatTF);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(319, 10, 89, 47);
		btnOk.addActionListener(e -> {
			Ingredient ig;
			if (nameTF.getText().trim().isEmpty())
				return;
			try {
				double cal = Double.valueOf(caloriesTF.getText().trim());
				double fat = Double.valueOf(fatTF.getText().trim());
				double prot = Double.valueOf(proteinTF.getText().trim());
				double carb = Double.valueOf(carbonHydTF.getText().trim());

				ig = new Ingredient(transfer.getId(), nameTF.getText().trim(),
						prot, fat, cal, carb);
			} catch (NumberFormatException ex) {
				ig = null;
			}

			if (ig != null) {
				FoodDAO dao;
				try {
					dao = new FoodXmlDAO();
					dao.updateIngredient(ig);
					((IngViewFrame) parentFrame).fillPanels(dao.getIngredients());
					this.parentFrame.setVisible(true);
					this.dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		getContentPane().add(btnOk);

		JButton btnMgsem = new JButton("Mégsem");
		btnMgsem.addActionListener(e -> {
			this.parentFrame.setVisible(true);
			this.dispose();
		});
		btnMgsem.setBounds(319, 76, 89, 47);
		getContentPane().add(btnMgsem);

	}

}
