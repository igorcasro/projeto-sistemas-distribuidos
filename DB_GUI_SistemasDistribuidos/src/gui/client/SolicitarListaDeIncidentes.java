package gui.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import com.google.gson.Gson;

import entities.Incidente;
import exceptions.GeneralErrorException;

public class SolicitarListaDeIncidentes extends JFrame {

	private JPanel contentPane;
	private JLabel lblManha;
	private JLabel lblNoite;
	private JLabel lblTarde;
	private JLabel lblMadrugada;
	private JPanel panelPeriodo;
	private JFormattedTextField textFieldKmFim;
	private JFormattedTextField textFieldKmInicio;
	private JFormattedTextField textFieldData;
	private JFormattedTextField textFieldRodovia;
	private JLabel lblFim;
	private JLabel lblInicio;
	private JCheckBox chckbxFaixaKm;
	private JLabel lblData;
	private JLabel lblRodovia;

	private MaskFormatter mascaraData;
	private MaskFormatter mascaraRodovia;
	private MaskFormatter mascaraKm;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnManha;
	private JRadioButton rdbtnTarde;
	private JRadioButton rdbtnNoite;
	private JRadioButton rdbtnMadrugada;

	/**
	 * Create the frame.
	 */
	public SolicitarListaDeIncidentes(PrintWriter out, BufferedReader in) {
		
		this.out = out;
		this.in = in;
		
		this.criarMascaraData();
		this.criarMascaraRodovia();
		this.criarMascaraKm();
		
		this.initComponents();
		
	}
	
	private void criarMascaraData() {

		try {

			this.mascaraData = new MaskFormatter("####-##-##");

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
	
	private Integer verificarSelecaoRadioButtonPeriodo() {
		
		if (this.rdbtnManha.isSelected()) {
			return 1;
		} else if (this.rdbtnTarde.isSelected()) {
			return 2;
		} else if (this.rdbtnNoite.isSelected()) {
			return 3;
		} else {
			return 4;
		}
	}
	
	public void initComponents() {
		
		setTitle("Solicitar Lista de Incidentes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 416, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblRodovia = new JLabel("Rodovia: ");
		lblRodovia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRodovia.setBounds(10, 13, 62, 20);
		contentPane.add(lblRodovia);
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblData.setBounds(10, 58, 45, 13);
		contentPane.add(lblData);
		
		chckbxFaixaKm = new JCheckBox("Faixa de Km");
		chckbxFaixaKm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnFaixaKmActionPerformed();
			}
		});
		chckbxFaixaKm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxFaixaKm.setSelected(true);
		chckbxFaixaKm.setBounds(10, 95, 93, 21);
		contentPane.add(chckbxFaixaKm);
		
		lblInicio = new JLabel("Inicio");
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInicio.setBounds(115, 97, 33, 17);
		contentPane.add(lblInicio);
		
		lblFim = new JLabel("Fim");
		lblFim.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFim.setBounds(286, 97, 24, 17);
		contentPane.add(lblFim);
		
		textFieldRodovia = new JFormattedTextField(mascaraRodovia);
		textFieldRodovia.setToolTipText("XX-000");
		textFieldRodovia.setBounds(158, 12, 232, 25);
		contentPane.add(textFieldRodovia);
		
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
		
		textFieldData = new JFormattedTextField(mascaraData);
		textFieldData.setValue(data);
		textFieldData.setToolTipText("YYYY-MM-DD");
		textFieldData.setBounds(158, 52, 232, 25);
		contentPane.add(textFieldData);
		
		textFieldKmInicio = new JFormattedTextField(mascaraKm);
		textFieldKmInicio.setToolTipText("000");
		textFieldKmInicio.setBounds(158, 93, 70, 25);
		contentPane.add(textFieldKmInicio);
		
		textFieldKmFim = new JFormattedTextField(mascaraKm);
		textFieldKmFim.setToolTipText("000");
		textFieldKmFim.setBounds(320, 93, 70, 25);
		contentPane.add(textFieldKmFim);
		
		panelPeriodo = new JPanel();
		panelPeriodo.setBorder(new TitledBorder(null, "Per\u00EDodo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPeriodo.setBounds(10, 128, 380, 85);
		contentPane.add(panelPeriodo);
		panelPeriodo.setLayout(null);
		
		lblManha = new JLabel("06:00 - 11:59");
		lblManha.setBounds(10, 56, 76, 13);
		panelPeriodo.add(lblManha);
		
		lblTarde = new JLabel("12:00 - 17:59");
		lblTarde.setBounds(98, 56, 76, 13);
		panelPeriodo.add(lblTarde);
		
		lblNoite = new JLabel("18:00 - 23:59");
		lblNoite.setBounds(186, 56, 76, 13);
		panelPeriodo.add(lblNoite);
		
		lblMadrugada = new JLabel("00:00 - 05:59");
		lblMadrugada.setBounds(274, 56, 76, 13);
		panelPeriodo.add(lblMadrugada);
		
		rdbtnManha = new JRadioButton("Manhã");
		buttonGroup.add(rdbtnManha);
		rdbtnManha.setBounds(8, 25, 78, 24);
		panelPeriodo.add(rdbtnManha);
		
		rdbtnTarde = new JRadioButton("Tarde");
		buttonGroup.add(rdbtnTarde);
		rdbtnTarde.setBounds(96, 24, 78, 24);
		panelPeriodo.add(rdbtnTarde);
		
		rdbtnNoite = new JRadioButton("Noite");
		buttonGroup.add(rdbtnNoite);
		rdbtnNoite.setBounds(184, 24, 78, 24);
		panelPeriodo.add(rdbtnNoite);
		
		rdbtnMadrugada = new JRadioButton("Madrugada");
		buttonGroup.add(rdbtnMadrugada);
		rdbtnMadrugada.setBounds(272, 25, 100, 24);
		panelPeriodo.add(rdbtnMadrugada);
		
		JButton btnSolicitar = new JButton("Solicitar");
		btnSolicitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnSolicitarActionPerformed();
				} catch (GeneralErrorException e1) {
					// TODO Auto-generated catch block
					errorCaught(e1);
					
				}
			}
		});
		btnSolicitar.setBounds(305, 223, 85, 21);
		contentPane.add(btnSolicitar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(210, 223, 85, 21);
		contentPane.add(btnLimpar);
	}
	
	private void btnSolicitarActionPerformed() throws GeneralErrorException {
		
		Incidente incidente = new Incidente();
		
		incidente.setId_operacao(5);
		if(textFieldRodovia.getText().equals("  -   ")) {
			throw new GeneralErrorException("Não é possível buscar quando possui campos vazios.");	
		} else {
			incidente.setRodovia(textFieldRodovia.getText());
		}
		
		incidente.setData(textFieldData.getText() + " " + "00:00:00");
		if(chckbxFaixaKm.isSelected()) {
			if(textFieldKmInicio.getText().isBlank() || textFieldKmFim.getText().isBlank()) {
				throw new GeneralErrorException("Não é possível buscar quando possui campos vazios.");		
			} else {
				incidente.setFaixa_km(textFieldKmInicio.getText()+"-"+textFieldKmFim.getText());
			}
		} 
		incidente.setPeriodo(verificarSelecaoRadioButtonPeriodo());
		
		Gson gson = new Gson();
		
		String json = gson.toJson(incidente);
		System.out.println("Client sent: " + json);
		out.println(json);
		
		try {		
			new VerListaDeIncidentes(in).setVisible(true);
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Solicitar Lista de Incidentes", JOptionPane.ERROR_MESSAGE);
		} finally {
			this.dispose();
		}
	}
	
	private void btnFaixaKmActionPerformed() {
		
		if(chckbxFaixaKm.isSelected()) {
			textFieldKmInicio.setEditable(true);
			textFieldKmFim.setEditable(true);
		} else {
			textFieldKmInicio.setEditable(false);
			textFieldKmFim.setEditable(false);
		}
	}
	
	private void errorCaught(GeneralErrorException gee) {

		JOptionPane.showMessageDialog(null, gee.getMessage(), "Solicitar Lista de Incidentes", JOptionPane.ERROR_MESSAGE);
		this.dispose();
	}
}
