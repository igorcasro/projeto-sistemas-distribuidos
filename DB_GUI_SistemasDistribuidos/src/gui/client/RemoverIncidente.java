package gui.client;

import java.awt.Color;
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

public class RemoverIncidente extends JFrame {

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
	public RemoverIncidente(PrintWriter out, BufferedReader in, Usuario usuario) {
		
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
		
		btnRemoverIncidente = new JButton("Remover Incidente");
		btnRemoverIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					Incidente incidenteComboBox = (Incidente)cbIncidente.getSelectedItem();
					
					Object[] options = { "Sim", "Não" };
					int opcao = JOptionPane.showOptionDialog(null, "Deseja confirmar a remoção?", "Confirma remoção", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					
					if(opcao == 0) {
						btnRemoverIncidenteActionPerformed(incidenteComboBox.getId_incidente());
					} 
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Remover incidente", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRemoverIncidente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoverIncidente.setBounds(620, 72, 208, 23);
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
	
	private void btnRemoverIncidenteActionPerformed(int id_incidente) throws IOException {
		
		try {
			
			Incidente incidente = new Incidente();
			incidente.setId_operacao(7);
			incidente.setToken(usuario.getToken());
			incidente.setId_incidente(id_incidente);
			incidente.setId_usuario(usuario.getId_usuario());
			
			Gson gson = new Gson();
			
			String json = gson.toJson(incidente);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
			
			if (retorno == null) {
				throw new GeneralErrorException("Retorno nulo!");
			}
		 
			try {
				if(retorno.getCodigo().equals(200)) {
					JOptionPane.showMessageDialog(null, "Incidente removido com sucesso.", "Remover incidente", JOptionPane.INFORMATION_MESSAGE);
			
				} else {
					throw new GeneralErrorException("Erro ao remover incidente.");
				}	
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Código enviado não é número.", "Cadastro de Usuário", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Reportar incidente", JOptionPane.ERROR_MESSAGE);
		} finally {
			
			this.dispose();	
		}
		
	}
}
