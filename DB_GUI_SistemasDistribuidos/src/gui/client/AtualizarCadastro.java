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

public class AtualizarCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldSenha;
	private JTextField textFieldEmail;
	private JLabel lblNome;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnAtualizar;
	private JButton btnLimpar;

	private PrintWriter out;
	private BufferedReader in;
	private String token;
	private int id_usuario;
	
	private ClientLogged clientLogged;
	private ClientUnlogged clientUnloggedWindow;
	
	/**
	 * Create the frame.
	 */
	public AtualizarCadastro(PrintWriter out, BufferedReader in, String token, int id_usuario, ClientLogged clientLogged, ClientUnlogged clientUnloggedWindow) {
		
		this.out = out;
		this.in = in;
		
		this.token = token;
		this.id_usuario = id_usuario;
		
		this.clientLogged = clientLogged;
		this.clientUnloggedWindow = clientUnloggedWindow;
		
		this.initComponents();
		
	}
	
	private void initComponents() {
		setTitle("Atualizar Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 12, 70, 15);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(59, 10, 379, 25);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(12, 51, 70, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(59, 46, 379, 25);
		contentPane.add(textFieldEmail);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 86, 70, 15);
		contentPane.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(59, 81, 379, 25);
		contentPane.add(textFieldSenha);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnAtualizarActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAtualizar.setBounds(321, 116, 117, 25);
		contentPane.add(btnAtualizar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 116, 117, 25);
		contentPane.add(btnLimpar);
	}
	
	private void btnAtualizarActionPerformed() throws IOException {
		
		try {
			
			Usuario usuario = new Usuario();
			usuario.setId_operacao(2);
			usuario.setNome(textFieldNome.getText());
			usuario.setEmail(textFieldEmail.getText());
			String hashedPswd = Usuario.hashed(textFieldSenha.getText());			
			usuario.setSenha(hashedPswd);
			usuario.setToken(token);
			usuario.setId_usuario(id_usuario);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(usuario);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
			
			if(retorno.getCodigo().equals(200)) {
				JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso.", "Atualização de Usuário", JOptionPane.INFORMATION_MESSAGE);
				clientLogged.dispose();
				
				Usuario usuarioAtualizado = new Usuario();
				usuarioAtualizado.setId_usuario(id_usuario);
				usuarioAtualizado.setToken(retorno.getToken());
				
				new ClientLogged(out, in, clientUnloggedWindow, usuarioAtualizado).setVisible(true);
			} else {
				throw new GeneralErrorException("Erro ao atualizar usuário");
			}
			
			
		}	catch(GeneralErrorException gee) {
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Atualização de Usuário", JOptionPane.ERROR_MESSAGE);
		} finally {
			
			this.dispose();
		}
		
		
	}
}
