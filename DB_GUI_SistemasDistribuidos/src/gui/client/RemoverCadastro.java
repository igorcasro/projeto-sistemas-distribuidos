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

public class RemoverCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSenha;
	private JTextField textFieldEmail;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnRemover;
	private JButton btnLimpar;
	
	private Usuario usuario;	
	private PrintWriter out;
	private BufferedReader in;
	private ClientLogged clientLoggedWindow;
	private ClientUnlogged clientUnloggedWindow;

	/**
	 * Create the frame.
	 */
	public RemoverCadastro(ClientLogged clientLoggedWindow, ClientUnlogged clientUnloggedWindow, PrintWriter out, BufferedReader in, Usuario usuario) {
		
		this.clientLoggedWindow = clientLoggedWindow;
		this.clientUnloggedWindow = clientUnloggedWindow;
		this.out = out;
		this.in = in;
		this.usuario = usuario;
		
		this.initComponents();
		
	}
	
	private void initComponents() {
				
		
		setTitle("Remover Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 139);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(12, 10, 70, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(59, 8, 379, 19);
		contentPane.add(textFieldEmail);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 37, 70, 15);
		contentPane.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(59, 37, 379, 19);
		contentPane.add(textFieldSenha);
		
		btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					Object[] options = { "Sim", "Não" };
					int opcao = JOptionPane.showOptionDialog(null, "Deseja confirmar a remoção?", "Confirma remoção", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					
					if(opcao == 0) {
						btnRemoverActionPerformed();
					}
				} catch (GeneralErrorException | IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Remoção de Usuário", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnRemover.setBounds(321, 68, 117, 25);
		contentPane.add(btnRemover);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(192, 68, 117, 25);
		contentPane.add(btnLimpar);
	}

	public void btnRemoverActionPerformed() throws GeneralErrorException, IOException {
		
		usuario.setId_operacao(8);
		usuario.setEmail(textFieldEmail.getText());
		String hashedPswd = Usuario.hashed(textFieldSenha.getText());			
		usuario.setSenha(hashedPswd);
//		usuario.setSenha(textFieldSenha.getText());
		
		Gson gson = new Gson();
		
		String json = gson.toJson(usuario);
		System.out.println("Client sent: " + json);
		out.println(json);
		
		String jsonRetorno = in.readLine();
		
		System.out.println("Server sent: " + jsonRetorno);
		Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
		
		if(retorno.getCodigo().equals(200)) {
			
			JOptionPane.showMessageDialog(null, "Usuário removido com sucesso.", "Remoção de Usuário", JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			clientLoggedWindow.dispose();
			clientUnloggedWindow.setVisible(true);	
		} else {
			throw new GeneralErrorException("Erro ao remover usuário");
		
		} 
		
	}
	
}
