package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entities.Usuario;
import exceptions.GeneralErrorException;
import service.UsuarioService;

public class Logar extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JTextField textFieldSenha;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnLogar;
	private JButton btnLimpar;
	private ClientUnlogged clientUnloggedWindow;
	private Usuario usuarioLogado;
	
	private UsuarioService usuarioService;
	
	/**
	 * Create the frame.
	 */
	public Logar(ClientUnlogged clientUnloggedWindow) {

		this.clientUnloggedWindow = clientUnloggedWindow;
		this.initComponents();
		
		this.usuarioService = new UsuarioService();
	}
	
	public void initComponents(){
		
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 459, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(12, 15, 70, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(59, 10, 379, 25);
		contentPane.add(textFieldEmail);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 45, 70, 15);
		contentPane.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(59, 40, 379, 25);
		contentPane.add(textFieldSenha);
		
		btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnLogarActionPerformed();
			}
		});
		btnLogar.setBounds(320, 87, 117, 25);
		contentPane.add(btnLogar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 87, 117, 25);
		contentPane.add(btnLimpar);
	}

	private void btnLogarActionPerformed() {
		Usuario usuario = new Usuario();
		usuarioLogado = new Usuario();
		try {
			
			
			usuario.setEmail(textFieldEmail.getText());
			String hashedPswd = Usuario.hashed(textFieldSenha.getText());			
			usuario.setSenha(hashedPswd);
			usuarioLogado = usuarioService.logar(usuario);
			
			JOptionPane.showMessageDialog(null, "Usuário logado com sucesso.", "Login de Usuário", JOptionPane.INFORMATION_MESSAGE);
			
			clientUnloggedWindow.setVisible(false);			
			new ClientLogged(clientUnloggedWindow, usuarioLogado).setVisible(true);
			
		} catch (SQLException | IOException | GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Login de Usuário", JOptionPane.ERROR_MESSAGE);
			gee.printStackTrace();
		} finally {
			
			this.dispose();	
		}
	}
}
