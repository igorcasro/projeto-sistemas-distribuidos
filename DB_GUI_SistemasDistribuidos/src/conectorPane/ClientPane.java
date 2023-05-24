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

import clientSocketHandler.Cliente;
import gui.client.ClientUnlogged;

public class ClientPane extends JFrame {

	private JPanel clientPane;
	private JTextField textFieldIP;
	private JTextField textFieldPorta;
	private JLabel lblIP;
	private JLabel lblPorta;
	private JButton btnConectar;
	
	private String ip;
	private int connectionPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientPane frame = new ClientPane();
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
	public ClientPane() {
		
		this.initComponents();
		
	}
	
	private void initComponents() {
		
		setTitle("Conexão ao Socket - Cliente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 169);
		clientPane = new JPanel();
		clientPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(clientPane);
		clientPane.setLayout(null);
		
		lblIP = new JLabel("Insira o IP: ");
		lblIP.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblIP.setBounds(10, 10, 96, 32);
		clientPane.add(lblIP);
		
		lblPorta = new JLabel("Insira a Porta:");
		lblPorta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPorta.setBounds(10, 58, 128, 20);
		clientPane.add(lblPorta);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(148, 11, 221, 32);
		clientPane.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		textFieldPorta = new JTextField();
		textFieldPorta.setBounds(148, 53, 221, 32);
		clientPane.add(textFieldPorta);
		textFieldPorta.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnConectarActionPerformed();
			}
		});
		btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConectar.setBounds(252, 95, 117, 21);
		clientPane.add(btnConectar);
	}
	
	private void btnConectarActionPerformed() {
		
		if(textFieldIP.getText().equals("")) {
			ip = "127.0.0.1";
		} else {
			ip = textFieldIP.getText();
		}
		
		if(textFieldPorta.getText().equals("")) {
			connectionPort = 24001;
		} else {
			connectionPort = Integer.parseInt(textFieldPorta.getText());
		}

		try {
			this.dispose();
			new ClientUnlogged(ip, connectionPort).setVisible(true);			

			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Problema ao conectar ao servidor.", "Conexão - Cliente", JOptionPane.ERROR_MESSAGE);
			System.out.println(ex.getMessage());
			System.exit(1);
		}
		
	}
}
