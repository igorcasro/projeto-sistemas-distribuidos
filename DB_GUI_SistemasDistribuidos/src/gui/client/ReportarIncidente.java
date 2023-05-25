package gui.client;

import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import enums.TipoIncidente;

public class ReportarIncidente extends JFrame {

	private JPanel contentPane;
	private JLabel lblData;
	private JLabel lblRodovia;
	private JLabel lblTipoDeIncidente;
	private JFormattedTextField textFieldData;
	private JFormattedTextField textFieldRodovia;
	private JFormattedTextField textFieldKm;
	private JCheckBox chckbxKM;
	private JComboBox comboBox;
	private JButton btnReportar;
	private JButton btnLimpar;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraRodovia;
	private MaskFormatter mascaraKm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportarIncidente frame = new ReportarIncidente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReportarIncidente() {
		
		this.criarMascaraData();
		this.criarMascaraRodovia();
		this.criarMascaraKm();
		
		this.initComponents();
	}
	
	private void criarMascaraData() {

		try {

			this.mascaraData = new MaskFormatter("####-##-## ##:##:##");

		} catch (ParseException e) {

			System.out.println("ERRO: " + e.getMessage());
		}
	}
	
	private void criarMascaraRodovia() {
		
		try {

			this.mascaraRodovia = new MaskFormatter("UU-###");

		} catch (ParseException e) {

			System.out.println("ERRO: " + e.getMessage());
		}
	}
	
	private void criarMascaraKm() {
		
		try {

			this.mascaraKm = new MaskFormatter("###");

		} catch (ParseException e) {

			System.out.println("ERRO: " + e.getMessage());
		}
	}
	
	public void initComponents() {
		
		setTitle("Reportar Incidente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblData = new JLabel("Data:");
		lblData.setBounds(12, 14, 38, 16);
		contentPane.add(lblData);
		
		lblRodovia = new JLabel("Rodovia:");
		lblRodovia.setBounds(12, 52, 102, 16);
		contentPane.add(lblRodovia);
		
		lblTipoDeIncidente = new JLabel("Tipo de Incidente:");
		lblTipoDeIncidente.setBounds(12, 134, 102, 16);
		contentPane.add(lblTipoDeIncidente);
		
		textFieldData = new JFormattedTextField(mascaraData);
		textFieldData.setBounds(132, 10, 78, 25);
		contentPane.add(textFieldData);
		
		textFieldRodovia = new JFormattedTextField(mascaraRodovia);
		textFieldRodovia.setBounds(132, 48, 78, 25);
		contentPane.add(textFieldRodovia);
		
		textFieldKm = new JFormattedTextField(mascaraKm);
		textFieldKm.setBounds(132, 86, 78, 25);
		contentPane.add(textFieldKm);
		
		chckbxKM = new JCheckBox("KM");
		chckbxKM.setSelected(true);
		chckbxKM.setBounds(8, 86, 54, 24);
		contentPane.add(chckbxKM);
		
		comboBox = new JComboBox();
		comboBox.setBounds(132, 130, 222, 25);
		contentPane.add(comboBox);
		
		for(String tipoIncidenteNome : enums.TipoIncidente.getNomes()) {
			comboBox.addItem(tipoIncidenteNome);
		}
		
		btnReportar = new JButton("Reportar");
		btnReportar.setBounds(255, 168, 99, 26);
		contentPane.add(btnReportar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(132, 168, 99, 26);
		contentPane.add(btnLimpar);
	}
}
