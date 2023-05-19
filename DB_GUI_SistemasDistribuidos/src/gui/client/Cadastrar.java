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

public class Cadastrar extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldSenha;
	private JTextField textFieldEmail;
	private JLabel lblNome;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnCadastrar;
	private JButton btnLimpar;
	
	private UsuarioService usuarioService;

	/**
	 * Create the frame.
	 */
	public Cadastrar() {
		
		this.initComponents();
		
		this.usuarioService = new UsuarioService();
		
	}
	
	public void initComponents() {		
		setTitle("Cadastrar");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 467, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 15, 70, 15);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(59, 15, 379, 25);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(12, 49, 70, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(59, 44, 379, 25);
		contentPane.add(textFieldEmail);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 81, 70, 15);
		contentPane.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(59, 73, 379, 25);
		contentPane.add(textFieldSenha);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnCadastrarActionPerformed();
				
			}
		});
		btnCadastrar.setBounds(321, 116, 117, 25);
		contentPane.add(btnCadastrar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 116, 117, 25);
		contentPane.add(btnLimpar);
	}
	
	
	private void btnCadastrarActionPerformed(){
		
		try {
			
			Usuario usuario = new Usuario();
			usuario.setNome(textFieldNome.getText());
			usuario.setEmail(textFieldEmail.getText());
			String hashedPswd = Usuario.hashed(textFieldSenha.getText());			
			usuario.setSenha(hashedPswd);
			
			usuarioService.cadastrar(usuario);
			
			JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.", "Cadastro de Usuário", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException | IOException | GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Cadastro de Usuário", JOptionPane.ERROR_MESSAGE);
			gee.printStackTrace();
			
		} finally {
			
			this.dispose();
		}
		
	}
}
