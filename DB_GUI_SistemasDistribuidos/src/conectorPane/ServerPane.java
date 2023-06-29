package conectorPane;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import serverSocketHandler.ListaUsuariosLogados;

public class ServerPane extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPorta;
	private JButton btnAbrirConexao;
	private JLabel lblPorta;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerPane frame = new ServerPane();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerPane() {
		
		this.initComponents();
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 129);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPorta = new JLabel("Insira a Porta:");
		lblPorta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPorta.setBounds(12, 12, 128, 32);
		contentPane.add(lblPorta);
		
		textFieldPorta = new JTextField();
		textFieldPorta.setColumns(10);
		textFieldPorta.setBounds(145, 13, 221, 32);
		contentPane.add(textFieldPorta);
		
		btnAbrirConexao = new JButton("Abrir Conexão");
		btnAbrirConexao.addActionListener(e -> {
	            String valor = textFieldPorta.getText();
	            dispose();
	        });
		btnAbrirConexao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAbrirConexao.setBounds(215, 57, 151, 21);
		contentPane.add(btnAbrirConexao);
	}
	
	public String getValor() {
		if(textFieldPorta.getText().equals("")) {
			return "24001";
		}
		
        return textFieldPorta.getText();
    }
}
