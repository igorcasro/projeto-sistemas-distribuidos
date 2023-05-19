package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import entities.Usuario;
import exceptions.GeneralErrorException;
import service.UsuarioService;

public class ClientLogged extends JFrame {

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
	private JButton btnVerListaDeIncidentes;
	private JButton btnRemoverCadastro;
	private JButton btnEditarIncidente;
	
	private ClientUnlogged clientUnloggedWindow;
	private UsuarioService usuarioService;
	private Usuario usuarioLogado;

	/**
	 * Create the frame.
	 */
	public ClientLogged(ClientUnlogged clientUnloggedWindow, Usuario usuarioLogado) {
		
		this.clientUnloggedWindow = clientUnloggedWindow;
		this.usuarioLogado = usuarioLogado;
		this.initComponents();
		
		this.usuarioService = new UsuarioService();
	}
	
	private void initComponents() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 489);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);
		
		mntmInformacoes = new JMenuItem("Informações");
		mnSobre.add(mntmInformacoes);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados do Usuário", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 0, 426, 70);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblIdUsuario = new JLabel("ID Usuário:");
		lblIdUsuario.setBounds(12, 16, 78, 15);
		panel.add(lblIdUsuario);
		
		lblIdRecebido = this.addIDReceived();
		lblIdRecebido.setBounds(102, 16, 312, 15);
		panel.add(lblIdRecebido);
		
		lblToken = new JLabel("Token:");
		lblToken.setBounds(12, 43, 70, 15);
		panel.add(lblToken);
		
		lblTokenrecebido = this.addTokenReceived();
		lblTokenrecebido.setBounds(69, 43, 345, 15);
		panel.add(lblTokenrecebido);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Cadastrar().setVisible(true);
			}
		});
		btnCadastrar.setBounds(96, 82, 256, 25);
		contentPane.add(btnCadastrar);
		
		btnAtualizarCadastro = new JButton("Atualizar Cadastro");
		btnAtualizarCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new AtualizarCadastro().setVisible(true);
			}
		});
		btnAtualizarCadastro.setBounds(96, 119, 256, 25);
		contentPane.add(btnAtualizarCadastro);
		
		btnVerListaDeIncidentes = new JButton("Ver Lista de Incidentes");
		btnVerListaDeIncidentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new VerListaDeIncidentes().setVisible(true);
			}
		});
		
		JButton btnReportarIncidente = new JButton("Reportar Incidente");
		btnReportarIncidente.setBounds(96, 156, 256, 25);
		contentPane.add(btnReportarIncidente);
		btnVerListaDeIncidentes.setBounds(96, 193, 256, 25);
		contentPane.add(btnVerListaDeIncidentes);
		
		btnRemoverCadastro = new JButton("Remover Cadastro");
		btnRemoverCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new RemoverCadastro().setVisible(true);
			}
		});
		
		JButton btnVerIncidentesReportados = new JButton("Ver Incidentes Reportados");
		btnVerIncidentesReportados.setBounds(96, 230, 256, 25);
		contentPane.add(btnVerIncidentesReportados);
		
		btnEditarIncidente = new JButton("Editar Incidente");
		btnEditarIncidente.setBounds(96, 267, 256, 25);
		contentPane.add(btnEditarIncidente);
		
		JButton btnRemoverIncidente = new JButton("Remover Incidente");
		btnRemoverIncidente.setBounds(96, 304, 256, 25);
		contentPane.add(btnRemoverIncidente);
		btnRemoverCadastro.setBounds(96, 341, 256, 25);
		contentPane.add(btnRemoverCadastro);
		
		JButton btnSairDoSistema = new JButton("Logout");
		btnSairDoSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnDeslogarActionPerformed();
			}
		});
		btnSairDoSistema.setBounds(96, 378, 256, 25);
		contentPane.add(btnSairDoSistema);
	}
	
	private JLabel addTokenReceived() {
		
		String Token = usuarioLogado.getToken();
		JLabel labelToken = new JLabel(Token);
		
		return labelToken;
	}

	private JLabel addIDReceived() {
		
		int ID = usuarioLogado.getId_usuario();
		JLabel labelID = new JLabel(Integer.toString(ID));
		
		return labelID;
	}
	
	public void btnDeslogarActionPerformed() {
		
		try {
			System.out.println(usuarioLogado);
			usuarioService.deslogar(usuarioLogado);
			
			JOptionPane.showMessageDialog(null, "Usuário deslogado com sucesso.", "Logout de Usuário", JOptionPane.INFORMATION_MESSAGE);
			
			clientUnloggedWindow.setVisible(true);			
			
		} catch (SQLException | IOException | GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Logout de Usuário", JOptionPane.ERROR_MESSAGE);
			gee.printStackTrace();
		} finally {
			
			this.dispose();	
		}
	}
}
