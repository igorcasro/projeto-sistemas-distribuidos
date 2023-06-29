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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;

import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;
import listener.UsuarioListener;

public class Logar extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmail;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnLogar;
	private JButton btnLimpar;
	private ClientUnlogged clientUnloggedWindow;
	private Usuario usuarioLogado;
	
	private PrintWriter out;
	private BufferedReader in;
	private JPasswordField textFieldSenha;
	
	/**
	 * Create the frame.
	 */
	public Logar(PrintWriter out, BufferedReader in, ClientUnlogged clientUnloggedWindow) {
		
		this.out = out;
		this.in = in;
		
		this.clientUnloggedWindow = clientUnloggedWindow;
		this.initComponents();
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
		
		btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					btnLogarActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogar.setBounds(320, 87, 117, 25);
		contentPane.add(btnLogar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 87, 117, 25);
		contentPane.add(btnLimpar);
		
		textFieldSenha = new JPasswordField();
		textFieldSenha.setBounds(59, 45, 379, 25);
		contentPane.add(textFieldSenha);
	}

	private void btnLogarActionPerformed() throws IOException {
		
		try {
			
			Usuario usuario = new Usuario();
			usuario.setId_operacao(3);
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
			
			if(retorno == null) {
				throw new GeneralErrorException("Retorno nulo!");
			}
			
			if(retorno.getToken() == null){
				throw new GeneralErrorException("Token nulo!");
			}
			
			if(retorno.getId_usuario() == null){
				throw new GeneralErrorException("Id de usuário nulo!");
			}
			
			try {
				if(retorno.getCodigo().equals(200)) {
					
					UsuarioListener usuarioListener = new UsuarioListener();
					usuarioListener.notifyUsuariosLogadosChanged();
					
					JOptionPane.showMessageDialog(null, "Usuário logado com sucesso.", "Login de Usuário", JOptionPane.INFORMATION_MESSAGE);
	
					usuarioLogado = new Usuario();
					usuarioLogado.setToken(retorno.getToken());
					usuarioLogado.setId_usuario(retorno.getId_usuario());
					
					clientUnloggedWindow.setVisible(false);			
					new ClientLogged(out, in, clientUnloggedWindow, usuarioLogado).setVisible(true);			
				} else {
					throw new GeneralErrorException("Erro ao logar usuário");
				}	
			} catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Código enviado não é número.", "Cadastro de Usuário", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Login de Usuário", JOptionPane.ERROR_MESSAGE);
		} finally {
			
			this.dispose();	
		}
	}
}
