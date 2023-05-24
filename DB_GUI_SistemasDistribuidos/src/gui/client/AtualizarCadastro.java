package gui.client;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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
	
	/**
	 * Create the frame.
	 */
	public AtualizarCadastro(PrintWriter out, BufferedReader in) {
		
		this.out = out;
		this.in = in;
		
		this.initComponents();
		
	}
	
	private void initComponents() {
		setTitle("Atualizar Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(12, 12, 70, 15);
		contentPane.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(59, 10, 379, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(12, 39, 70, 15);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(59, 37, 379, 19);
		contentPane.add(textFieldEmail);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(12, 66, 70, 15);
		contentPane.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(59, 64, 379, 19);
		contentPane.add(textFieldSenha);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(321, 95, 117, 25);
		contentPane.add(btnAtualizar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(201, 95, 117, 25);
		contentPane.add(btnLimpar);
	}
}
