package gui.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clientSocketHandler.Cliente;

public class RemoverCadastro extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSenha;
	private JTextField textFieldEmail;
	private JLabel lblEmail;
	private JLabel lblSenha;
	private JButton btnAtualizar;
	private JButton btnLimpar;
	private JTextField textFieldToken;
	private JTextField textFieldID;
	
	private Cliente cliente;	

	/**
	 * Create the frame.
	 */
	public RemoverCadastro(Cliente cliente) {
		
		this.cliente = cliente;
		
		this.initComponents();
		
	}
	
	private void initComponents() {
				
		
		setTitle("Remover Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 220);
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
		
		JLabel lblToken = new JLabel("Token");
		lblToken.setBounds(12, 66, 70, 15);
		contentPane.add(lblToken);
		
		textFieldToken = new JTextField();
		textFieldToken.setColumns(10);
		textFieldToken.setBounds(59, 64, 379, 19);
		contentPane.add(textFieldToken);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(12, 95, 70, 15);
		contentPane.add(lblId);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(321, 128, 117, 25);
		contentPane.add(btnAtualizar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(203, 128, 117, 25);
		contentPane.add(btnLimpar);
		
		textFieldID = new JTextField();
		textFieldID.setColumns(10);
		textFieldID.setBounds(59, 93, 379, 19);
		contentPane.add(textFieldID);
	}

}
