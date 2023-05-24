package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;

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
	
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Create the frame.
	 */
	public Cadastrar(PrintWriter out, BufferedReader in) {
		
		this.out = out;
		this.in = in;
		
		this.initComponents();
		
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
				
				try {
					btnCadastrarActionPerformed();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnCadastrar.setBounds(321, 116, 117, 25);
		contentPane.add(btnCadastrar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 116, 117, 25);
		contentPane.add(btnLimpar);
	}
	
	
	private void btnCadastrarActionPerformed() throws IOException{
		
		try {
			
			Usuario usuario = new Usuario();
			usuario.setId_operacao(1);
			usuario.setNome(textFieldNome.getText());
			usuario.setEmail(textFieldEmail.getText());
			String hashedPswd = Usuario.hashed(textFieldSenha.getText());			
			usuario.setSenha(hashedPswd);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(usuario);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
			
			if(retorno.getCodigo().equals(200)) {
				JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.", "Cadastro de Usuário", JOptionPane.INFORMATION_MESSAGE);
			} else {
				throw new GeneralErrorException("Erro ao cadastrar usuário");
			}
		
		} catch(GeneralErrorException gee) {
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Cadastro de Usuário", JOptionPane.ERROR_MESSAGE);
		} finally {
			
			this.dispose();
		}
		
	}
}
