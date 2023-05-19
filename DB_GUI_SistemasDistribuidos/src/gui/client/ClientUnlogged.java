package gui.client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
	private JButton btnAtualizarCadastro;
	private JButton btnLogar;
	private JButton btnVerListaDeIncidentes;
	private JButton btnRemoverCadastro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientUnlogged frame = new ClientUnlogged();
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
	public ClientUnlogged() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 267);
		
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
		contentPane.setLayout(null);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Cadastrar().setVisible(true);
			}
		});
		btnCadastrar.setBounds(112, 12, 219, 25);
		contentPane.add(btnCadastrar);
		
		btnAtualizarCadastro = new JButton("Atualizar Cadastro");
		btnAtualizarCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new AtualizarCadastro().setVisible(true);
			}
		});
		btnAtualizarCadastro.setBounds(112, 49, 219, 25);
		contentPane.add(btnAtualizarCadastro);
		
		btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				logarActionListener();
			}
		});
		btnLogar.setBounds(112, 86, 219, 25);
		contentPane.add(btnLogar);
		
		btnVerListaDeIncidentes = new JButton("Ver Lista de Incidentes");
		btnVerListaDeIncidentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new VerListaDeIncidentes().setVisible(true);
			}
		});
		btnVerListaDeIncidentes.setBounds(112, 123, 219, 25);
		contentPane.add(btnVerListaDeIncidentes);
		
		btnRemoverCadastro = new JButton("Remover Cadastro");
		btnRemoverCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new RemoverCadastro().setVisible(true);
			}
		});
		btnRemoverCadastro.setBounds(112, 160, 219, 25);
		contentPane.add(btnRemoverCadastro);
	}
	
	private void logarActionListener() {
		new Logar(this).setVisible(true);
	}
	
}
