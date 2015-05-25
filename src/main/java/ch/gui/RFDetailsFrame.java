package ch.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import ch.model.ReadyFood;

@SuppressWarnings("serial")
public class RFDetailsFrame extends MyFrame {

	ReadyFood trans;
	private JTextField textField;
	private JLabel lFatinCustom;
	private JLabel lProtinCustom;
	private JLabel lCalinCustom;
	private JLabel lCarbinCustom;
	private static final String formatStr = "%.3f";

	public RFDetailsFrame(JFrame parent, ReadyFood rf) {
		super(parent);
		setTitle("Részletek");
		trans = rf;
		setResizable(false);
		this.setBounds(100, 100, 519, 351);
		getContentPane().setLayout(null);

		JLabel label = new JLabel(trans.getName());
		label.setBounds(12, 12, 70, 15);
		getContentPane().add(label);

		JSeparator separator = new JSeparator();
		separator.setBounds(22, 39, 485, 2);
		getContentPane().add(separator);

		JLabel lblTprtk = new JLabel("Tápérték");
		lblTprtk.setBounds(32, 53, 70, 15);
		getContentPane().add(lblTprtk);

		JLabel lblGban = new JLabel("100 g-ban");
		lblGban.setBounds(142, 105, 70, 15);
		getContentPane().add(lblGban);

		textField = new NumberOnlyTextField(100.0);
		textField.setBounds(260, 103, 114, 19);
		textField.addActionListener(e -> {
			if (!textField.getText().trim().isEmpty())
				updateLabels(Double.valueOf(textField.getText().trim()));
		});
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lCarb = new JLabel("Szénhidrát");
		lCarb.setBounds(22, 139, 90, 15);
		getContentPane().add(lCarb);

		JLabel lCal = new JLabel("Kalória");
		lCal.setBounds(22, 166, 90, 15);
		getContentPane().add(lCal);

		JLabel lProt = new JLabel("Fehérje");
		lProt.setBounds(22, 193, 90, 15);
		getContentPane().add(lProt);

		JLabel lFat = new JLabel("Zsír");
		lFat.setBounds(22, 220, 90, 15);
		getContentPane().add(lFat);

		// carb
		JLabel lCarbIn100g = new JLabel(String.format(formatStr,
				trans.getCarbons()));
		lCarbIn100g.setBounds(142, 139, 70, 15);
		getContentPane().add(lCarbIn100g);

		lCarbinCustom = new JLabel(String.format(formatStr, trans.getCarbons()));
		lCarbinCustom.setBounds(260, 139, 70, 15);
		getContentPane().add(lCarbinCustom);

		// cal
		JLabel lCalin100g = new JLabel(String.format(formatStr,
				trans.getCalories()));
		lCalin100g.setBounds(142, 166, 70, 15);
		getContentPane().add(lCalin100g);

		lCalinCustom = new JLabel(String.format(formatStr, trans.getCalories()));
		lCalinCustom.setBounds(260, 166, 70, 15);
		getContentPane().add(lCalinCustom);

		// prot
		JLabel lProtin100g = new JLabel(String.format(formatStr,
				trans.getProteins()));
		lProtin100g.setBounds(142, 193, 70, 15);
		getContentPane().add(lProtin100g);

		lProtinCustom = new JLabel(String.format(formatStr, trans.getProteins()));
		lProtinCustom.setBounds(260, 193, 70, 15);
		getContentPane().add(lProtinCustom);

		// fat
		JLabel lFatin100g = new JLabel(String.format(formatStr, trans.getFat()));
		lFatin100g.setBounds(142, 220, 70, 15);
		getContentPane().add(lFatin100g);

		lFatinCustom = new JLabel(String.format(formatStr, trans.getFat()));
		lFatinCustom.setBounds(260, 220, 70, 15);
		getContentPane().add(lFatinCustom);

		JButton btnVissza = new JButton("Vissza");
		btnVissza.setBounds(158, 264, 200, 50);
		btnVissza.addActionListener(e -> {
			this.dispose();
			parentFrame.setVisible(true);
		});
		getContentPane().add(btnVissza);
	}

	private void updateLabels(Double d) {
		lCalinCustom.setText(String.format("%.5f", trans.getCalories(d)));
		lCarbinCustom.setText(String.format("%.5f", trans.getCarbons(d)));
		lFatinCustom.setText(String.format("%.5f", trans.getFat(d)));
		lProtinCustom.setText(String.format("%.5f", trans.getProteins(d)));
		repaint();
	}
}
