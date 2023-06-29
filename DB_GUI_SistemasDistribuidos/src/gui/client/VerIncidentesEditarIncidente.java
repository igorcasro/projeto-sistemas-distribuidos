package gui.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import entities.Incidente;
import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;
import service.IncidenteService;

public class VerIncidentesEditarIncidente extends JFrame {

	private JPanel contentPane;
	private JComboBox cbIncidente;
	private JLabel lblIncidente;
	private JButton btnRemoverIncidente;
	
	private IncidenteService incidenteService;
	private Usuario usuario;
	private PrintWriter out;
	private BufferedReader in;

	/**
	 * Create the frame.
	 */
	public VerIncidentesEditarIncidente(PrintWriter out, BufferedReader in, Usuario usuario) {
		
		this.out = out;
		this.in = in;
		this.usuario = usuario;
		
		this.initComponents();
		
		this.incidenteService = new IncidenteService();
		
		this.buscarIncidentes();
	
	}
	
	public void initComponents() {
		
		setTitle("Remover Incidente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 852, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbIncidente = new JComboBox();
		cbIncidente.setBackground(new Color(255, 255, 255));
		cbIncidente.setForeground(new Color(0, 0, 0));
		cbIncidente.setBounds(94, 10, 734, 34);
		contentPane.add(cbIncidente);
		
		lblIncidente = new JLabel("Incidente");
		lblIncidente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIncidente.setBounds(10, 14, 74, 23);
		contentPane.add(lblIncidente);	
		
		btnRemoverIncidente = new JButton("Editar Incidente");
		btnRemoverIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Incidente incidenteComboBox = (Incidente)cbIncidente.getSelectedItem();			
				btnEditarIncidenteActionPerformed(incidenteComboBox);
					
			}
		});
		btnRemoverIncidente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoverIncidente.setBounds(670, 72, 158, 23);
		contentPane.add(btnRemoverIncidente);
	}
	
	private void buscarIncidentes(){
		List<Incidente> incidentes;
		
		try {
			Retorno retorno = this.incidenteService.buscarPorIdUsuario(usuario);
			incidentes = retorno.getLista_incidentes();
			
			for (Incidente incidente : incidentes) {
				
				this.cbIncidente.addItem(incidente);
			}
			
			JOptionPane.showMessageDialog(null, "Sucesso ao buscar incidentes", "Busca de Incidentes", JOptionPane.INFORMATION_MESSAGE);
		} catch(SQLException | GeneralErrorException | IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar incidentes", "Busca de Incidentes", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	private void btnEditarIncidenteActionPerformed(Incidente incidente){
		
		try {
			
			incidente.setId_operacao(10);
			incidente.setToken(usuario.getToken());
			incidente.setId_usuario(usuario.getId_usuario());
					
			
			new EditarIncidente(out, in, incidente).setVisible(true);
		} finally {
			
			this.dispose();	
		}
		
	}
}