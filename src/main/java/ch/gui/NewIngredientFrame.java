package ch.gui;

import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class NewIngredientFrame extends MyFrame {
	
	private JTextField nameTF;
	private JTextField caloriesTF;
	private JTextField carbonHydTF;
	private JTextField proteinTF;
	private JTextField fatTF;

	public NewIngredientFrame(JFrame parent) {
		super(parent);
		setAlwaysOnTop(true);
		setTitle("Új hozzávaló");
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
		
		nameTF = new JTextField();
		nameTF.setBounds(138, 11, 141, 20);
		getContentPane().add(nameTF);
		
		caloriesTF = new NumberOnlyTextField(0.0);
		caloriesTF.setBounds(138, 37, 141, 20);
		getContentPane().add(caloriesTF);
		
		carbonHydTF = new NumberOnlyTextField(new Float(0.0));
		carbonHydTF.setBounds(138, 63, 141, 20);
		getContentPane().add(carbonHydTF);
		
		proteinTF = new NumberOnlyTextField(new Float(0.0));
		proteinTF.setBounds(138, 89, 141, 20);
		getContentPane().add(proteinTF);
		
		fatTF = new NumberOnlyTextField(new Float(0.0));
		fatTF.setBounds(138, 115, 141, 20);
		getContentPane().add(fatTF);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(319, 10, 89, 47);
		btnOk.addActionListener(e -> {
			/*Ingredient ig;
			
			FoodDAO dao;
			try {
				dao = new FoodXmlDAO();
				dao.insertIngredient(ig);
				IngredientFrame iframe = new IngredientFrame((JFrame) parent.getParent());
				parent.dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
			}*/

		});
		getContentPane().add(btnOk);
		
		JButton btnMgsem = new JButton("Mégsem");
		btnMgsem.setBounds(319, 76, 89, 47);
		getContentPane().add(btnMgsem);
		
		
		
	}
	
	
	
}
