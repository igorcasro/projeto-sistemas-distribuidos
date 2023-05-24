package conectorPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import serverSocketHandler.Server;

public class ServerPane extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPorta;

	private int connectionPort;
	private JButton btnAbrirConexao;
	private JLabel lblPorta;
	
	static int count = 0;
	
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
		btnAbrirConexao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnAbrirConexaoActionPerformed();
				e.getActionCommand();
			}
		});
		btnAbrirConexao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAbrirConexao.setBounds(215, 57, 151, 21);
		contentPane.add(btnAbrirConexao);
	}
	
	private void btnAbrirConexaoActionPerformed() {
		
		if(textFieldPorta.getText().equals("")) {
			connectionPort = 24001;
		} else {
			connectionPort = Integer.parseInt(textFieldPorta.getText());
		}
		
		try {
			new Server(connectionPort);
			count++;
			if(count == 1)
				Server.main(null);
//			this.dispose();
			JOptionPane.showMessageDialog(null, "Conexão aberta.", "Conexão - Servidor", JOptionPane.INFORMATION_MESSAGE);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Problema ao conectar à porta especificada.", "Conexão - Servidor", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
	}
}
