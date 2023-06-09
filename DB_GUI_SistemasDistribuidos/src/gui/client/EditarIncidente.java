package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
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

public class EditarIncidente extends JFrame {

	private JPanel contentPane;
	private JLabel lblData;
	private JLabel lblRodovia;
	private JLabel lblTipoDeIncidente;
	private JFormattedTextField textFieldData;
	private JFormattedTextField textFieldRodovia;
	private JFormattedTextField textFieldKm;
	private JComboBox comboBox;
	private JButton btnEditar;
	private JButton btnLimpar;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraRodovia;
	private MaskFormatter mascaraKm;
	
	private PrintWriter out;
	private BufferedReader in;
	private Incidente incidente;

	/**
	 * Create the frame.
	 */
	public EditarIncidente(PrintWriter out, BufferedReader in, Incidente incidente) {
		
		this.out = out;
		this.in = in;

		this.incidente = incidente;
		
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
		setBounds(100, 100, 490, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblData = new JLabel("Data:");
		lblData.setBounds(12, 14, 61, 16);
		contentPane.add(lblData);
		
		lblRodovia = new JLabel("Rodovia:");
		lblRodovia.setBounds(12, 52, 102, 16);
		contentPane.add(lblRodovia);
		
		lblTipoDeIncidente = new JLabel("Tipo de Incidente:");
		lblTipoDeIncidente.setBounds(12, 134, 226, 16);
		contentPane.add(lblTipoDeIncidente);
		
		Date dataHoraAtual = new Date();
//		String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
//		String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);
		
		textFieldData = new JFormattedTextField(mascaraData);
		textFieldData.setValue(incidente.getData());
		textFieldData.setBounds(216, 10, 180, 25);
		contentPane.add(textFieldData);
		
		textFieldRodovia = new JFormattedTextField(mascaraRodovia);
		textFieldRodovia.setValue(incidente.getRodovia());
		textFieldRodovia.setBounds(282, 48, 114, 25);
		contentPane.add(textFieldRodovia);
		
		textFieldKm = new JFormattedTextField(mascaraKm);
		if(incidente.getKm().equals(0)) {
			textFieldKm.setValue("000");
		} else {
			textFieldKm.setValue(incidente.getKm().toString());
		}
		textFieldKm.setBounds(318, 86, 78, 25);
		contentPane.add(textFieldKm);

		
		comboBox = new JComboBox();
		comboBox.setBounds(174, 130, 222, 25);
		contentPane.add(comboBox);
		
		for(String tipoIncidenteNome : enums.TipoIncidente.getNomes()) {
			comboBox.addItem(tipoIncidenteNome);
		}
		
		comboBox.getModel().setSelectedItem(Incidente.converteTipoIncidente(incidente.getTipo_incidente()));
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnReportarActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEditar.setBounds(297, 162, 99, 26);
		contentPane.add(btnEditar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(196, 162, 99, 26);
		contentPane.add(btnLimpar);
		
		JLabel lblKm = new JLabel("Km:");
		lblKm.setBounds(12, 90, 55, 16);
		contentPane.add(lblKm);
	}
	
	private void btnReportarActionPerformed() throws IOException {
		
		try {
			incidente.setData(textFieldData.getText());
			if(!textFieldRodovia.getText().isBlank()) {
				incidente.setRodovia(textFieldRodovia.getText());
			}
			if(!textFieldKm.getText().isBlank()) {
				incidente.setKm(Integer.parseInt(textFieldKm.getText()));
			} 
			int tipoIncidente = transformaNomeEmIntComboBox(comboBox);
			incidente.setTipo_incidente(tipoIncidente);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(incidente);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
		
			if(retorno == null) {
				throw new GeneralErrorException("Retorno nulo!");
			}
			
			try {
				if(retorno.getCodigo().equals(200)) {
					JOptionPane.showMessageDialog(null, "Incidente editado com sucesso.", "Editar incidente", JOptionPane.INFORMATION_MESSAGE);
			
				} else {
					throw new GeneralErrorException("Erro ao editar incidente.");
				}	
				
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Código enviado não é número.", "Cadastro de Usuário", JOptionPane.ERROR_MESSAGE);
			}
			
			
		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Editar incidente", JOptionPane.ERROR_MESSAGE);
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
