package gui.client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clientSocketHandler.Cliente;

public class ClientUnlogged extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnSobre;
	private JMenuItem mntmInformacoes;
	private JPanel panel;
	private JLabel lblIdUsuario;
	private JLabel lblIdRecebido;
	private JLabel lblToken;
	private JLabel lblTokenrecebido;
	private JButton btnCadastrar;
	private JButton btnLogar;
	private JButton btnVerListaDeIncidentes;
	private JButton btnRemoverCadastro;

	private String ip;
	private int connectionPort;
	
	private Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;

	/**
	 * Create the frame.
	 */

	public ClientUnlogged(String ip, int connectionPort) {
		
		this.ip = ip;
		this.connectionPort = connectionPort;
		
		this.initComponents();
	}
	
	private void initComponents() {
		
		//Initiate socket connection
		String serverHostname = new String(ip);
        System.out.println ("Attemping to connect to host " +
                serverHostname + " on port " + connectionPort + ".");

        echoSocket = null;
        out = null;
        in = null;

        try {
            echoSocket = new Socket(serverHostname, connectionPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
            JOptionPane.showMessageDialog(null, "Conexão realizada com sucesso.", "Conexão ao servidor.", JOptionPane.INFORMATION_MESSAGE);
        
        
         // Aqui inicia o Frame ClientUnlogged
    		setTitle("Client");
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		setBounds(100, 100, 398, 253);
    		
    		menuBar = new JMenuBar();
    		setJMenuBar(menuBar);
    		
    		mnSobre = new JMenu("Sobre");
    		menuBar.add(mnSobre);
    		
    		mntmInformacoes = new JMenuItem("Informações");
    		mntmInformacoes.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    			
    				new Informacoes().setVisible(true);
    			}
    		});
    		mnSobre.add(mntmInformacoes);
    		contentPane = new JPanel();
    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    		setContentPane(contentPane);
    		
    		btnCadastrar = new JButton("Cadastrar");
    		btnCadastrar.setBounds(88, 48, 219, 25);
    		btnCadastrar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				new Cadastrar(out, in).setVisible(true);
    			}
    		});
    		contentPane.setLayout(null);
    		contentPane.add(btnCadastrar);
    		
    		btnLogar = new JButton("Logar");
    		btnLogar.setBounds(88, 85, 219, 25);
    		btnLogar.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				
    				btnLogarActionPerformed();
    			}
    		});
    		contentPane.add(btnLogar);
    		
    		btnVerListaDeIncidentes = new JButton("Ver Lista de Incidentes");
    		btnVerListaDeIncidentes.setBounds(88, 122, 219, 25);
    		btnVerListaDeIncidentes.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    			
    				new SolicitarListaDeIncidentes(out, in).setVisible(true);
    			}
    		});
    		contentPane.add(btnVerListaDeIncidentes);
    		
    		btnRemoverCadastro = new JButton("Remover Cadastro");
    		btnRemoverCadastro.setEnabled(false);
    		btnRemoverCadastro.setBounds(88, 159, 219, 25);
//    		btnRemoverCadastro.addActionListener(new ActionListener() {
//    			public void actionPerformed(ActionEvent e) {
//    			
//    				new RemoverCadastro(cliente).setVisible(true);
//    			}
//    		});
    		contentPane.add(btnRemoverCadastro);
    		
    		JLabel lblPorta = new JLabel("Porta:");
    		lblPorta.setFont(new Font("Dialog", Font.BOLD, 16));
    		lblPorta.setBounds(12, 12, 54, 30);
    		contentPane.add(lblPorta);
    		
    		JLabel lblPortaSelecionada = new JLabel("00000");
    		lblPortaSelecionada.setText(String.valueOf(connectionPort));
    		lblPortaSelecionada.setFont(new Font("Dialog", Font.BOLD, 16));
    		lblPortaSelecionada.setBounds(78, 21, 77, 16);
    		contentPane.add(lblPortaSelecionada);
    		
    		JLabel lblIP = new JLabel("IP:");
    		lblIP.setFont(new Font("Dialog", Font.BOLD, 16));
    		lblIP.setBounds(239, 20, 30, 16);
    		contentPane.add(lblIP);
    		
    		JLabel lblIPSelecionado = new JLabel("000.0.0.0");
    		lblIPSelecionado.setText(ip);
    		lblIPSelecionado.setFont(new Font("Dialog", Font.BOLD, 16));
    		lblIPSelecionado.setBounds(278, 20, 94, 16);
    		contentPane.add(lblIPSelecionado);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão ao servidor.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + serverHostname);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Conexão ao servidor.", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        
		
	}
	
	private void btnLogarActionPerformed() {
		new Logar(out, in, this).setVisible(true);
	}
}
