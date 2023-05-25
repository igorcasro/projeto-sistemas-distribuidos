package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.google.gson.Gson;

import entities.Incidente;
import entities.Retorno;
import exceptions.GeneralErrorException;

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
	
	private PrintWriter out;
	private BufferedReader in;
	private String tokenReceived;
	private int id_usuario;

	/**
	 * Create the frame.
	 */
	public ReportarIncidente(PrintWriter out, BufferedReader in, String tokenReceived, int id_usuario) {
		
		this.out = out;
		this.in = in;
		
		this.tokenReceived = tokenReceived;
		this.id_usuario = id_usuario;
		
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		textFieldData.setBounds(174, 10, 180, 25);
		contentPane.add(textFieldData);
		
		textFieldRodovia = new JFormattedTextField(mascaraRodovia);
		textFieldRodovia.setBounds(240, 48, 114, 25);
		contentPane.add(textFieldRodovia);
		
		textFieldKm = new JFormattedTextField(mascaraKm);
		textFieldKm.setBounds(276, 86, 78, 25);
		contentPane.add(textFieldKm);
		
		chckbxKM = new JCheckBox("KM");
		chckbxKM.setSelected(true);
		chckbxKM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnKmActionPerformed();
			}
		});
		chckbxKM.setBounds(8, 86, 54, 24);
		contentPane.add(chckbxKM);

		
		comboBox = new JComboBox();
		comboBox.setBounds(132, 130, 222, 25);
		contentPane.add(comboBox);
		
		for(String tipoIncidenteNome : enums.TipoIncidente.getNomes()) {
			comboBox.addItem(tipoIncidenteNome);
		}
		
		btnReportar = new JButton("Reportar");
		btnReportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnReportarActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReportar.setBounds(255, 168, 99, 26);
		contentPane.add(btnReportar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(132, 168, 99, 26);
		contentPane.add(btnLimpar);
	}
	
	private void btnKmActionPerformed() {
		if(chckbxKM.isSelected()) {
			textFieldKm.setEditable(true);
		} else {
			textFieldKm.setEditable(false);
		}
	}
	
	private void btnReportarActionPerformed() throws IOException {
		
		try {
			
			Incidente incidente = new Incidente();
			incidente.setId_operacao(4);
			incidente.setData(textFieldData.getText());
			incidente.setRodovia(textFieldRodovia.getText());
			incidente.setKm(Integer.parseInt(textFieldKm.getText()));
			int tipoIncidente = transformaNomeEmIntComboBox(comboBox);
			incidente.setTipoIncidente(tipoIncidente);
			incidente.setToken(tokenReceived);
			incidente.setId_usuario(id_usuario);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(incidente);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
		
			if(retorno.getCodigo().equals(200)) {
				JOptionPane.showMessageDialog(null, "Incidente reportado com sucesso.", "Reportar incidente", JOptionPane.INFORMATION_MESSAGE);
		
			} else {
				throw new GeneralErrorException("Erro ao reportar incidente.");
			}
		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Reportar incidente", JOptionPane.ERROR_MESSAGE);
		} finally {
			
			this.dispose();	
		}
	}
	
	private Integer transformaNomeEmIntComboBox(JComboBox comboBox) {
		
		String selected = String.valueOf(comboBox.getSelectedItem());
		
		if(selected.equals("Vento")) {
			return 1;
		}
		if(selected.equals("Chuva")) {
			return 2;
		}
		if(selected.equals("Nevoeiro")) {
			return 3;
		}
		if(selected.equals("Neve")) {
			return 4;
		}
		if(selected.equals("Gelo na Pista")) {
			return 5;
		}
		if(selected.equals("Granizo")) {
			return 6;
		}
		if(selected.equals("Trânsito parado")) {
			return 7;
		}
		if(selected.equals("Filas de trânsito")) {
			return 8;
		}
		if(selected.equals("Trânsito lento")) {
			return 9;
		}
		if(selected.equals("Acidente desconhecido")) {
			return 10;
		}
		if(selected.equals("Incidente desconhecido")) {
			return 11;
		}
		if(selected.equals("Trabalhos na estrada")) {
			return 12;
		}
		if(selected.equals("Bloqueio de pista")) {
			return 13;
		}
		if(selected.equals("Bloqueio de Estrada")) {
			return 14;
		}
		
		return -1;
	}
}
