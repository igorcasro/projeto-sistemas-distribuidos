package gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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

import com.google.gson.Gson;

import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;

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
	private Usuario usuarioLogado;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private String tokenReceived;
	
	public void setTokenReceived(String tokenReceived) {
		this.tokenReceived = tokenReceived;
	}

	/**
	 * Create the frame.
	 */
	public ClientLogged(PrintWriter out, BufferedReader in, ClientUnlogged clientUnloggedWindow, Usuario usuarioLogado) {
		
		this.out = out;
		this.in = in;
		
		this.clientUnloggedWindow = clientUnloggedWindow;
		this.usuarioLogado = usuarioLogado;
		this.initComponents();
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
		panel.setBounds(12, 0, 414, 70);
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
				
				new Cadastrar(out, in).setVisible(true);
			}
		});
		btnCadastrar.setBounds(96, 82, 256, 25);
		contentPane.add(btnCadastrar);
		
		btnAtualizarCadastro = new JButton("Atualizar Cadastro");
		btnAtualizarCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnAtualizarActionPerformed();
			}
		});
		btnAtualizarCadastro.setBounds(96, 119, 256, 25);
		contentPane.add(btnAtualizarCadastro);
		
		btnVerListaDeIncidentes = new JButton("Ver Lista de Incidentes");
		btnVerListaDeIncidentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new SolicitarListaDeIncidentes(out, in).setVisible(true);
			}
		});
		
		JButton btnReportarIncidente = new JButton("Reportar Incidente");
		btnReportarIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnReportarIncidenteActionPerformed();
			}
		});
		btnReportarIncidente.setBounds(96, 156, 256, 25);
		contentPane.add(btnReportarIncidente);
		btnVerListaDeIncidentes.setBounds(96, 193, 256, 25);
		contentPane.add(btnVerListaDeIncidentes);
		
		btnRemoverCadastro = new JButton("Remover Cadastro");
		btnRemoverCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				btnRemoverCadastroActionPerformed();
			}
		});
		
		JButton btnVerIncidentesReportados = new JButton("Ver Incidentes Reportados");
		btnVerIncidentesReportados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnVerIncidentesReportadosActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerIncidentesReportados.setBounds(96, 230, 256, 25);
		contentPane.add(btnVerIncidentesReportados);
		
		btnEditarIncidente = new JButton("Editar Incidente");
		btnEditarIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnEditarIncidenteActionPerformed();
			}
		});
		btnEditarIncidente.setBounds(96, 267, 256, 25);
		contentPane.add(btnEditarIncidente);
		
		JButton btnRemoverIncidente = new JButton("Remover Incidente");
		btnRemoverIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				btnRemoverIncidenteActionPerformed();
			}
		});
		btnRemoverIncidente.setBounds(96, 304, 256, 25);
		contentPane.add(btnRemoverIncidente);
		btnRemoverCadastro.setBounds(96, 341, 256, 25);
		contentPane.add(btnRemoverCadastro);
		
		JButton btnSairDoSistema = new JButton("Logout");
		btnSairDoSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					btnDeslogarActionPerformed();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSairDoSistema.setBounds(96, 378, 256, 25);
		contentPane.add(btnSairDoSistema);
	}
	
	private JLabel addTokenReceived() {
		
		tokenReceived = usuarioLogado.getToken();
		JLabel labelToken = new JLabel(tokenReceived);
		
		return labelToken;
	}

	private JLabel addIDReceived() {
		
		int idReceived = usuarioLogado.getId_usuario();
		JLabel labelID = new JLabel(Integer.toString(idReceived));
		
		return labelID;
	}
	
	public void btnAtualizarActionPerformed() {
		new AtualizarCadastro(out, in, usuarioLogado.getToken(), usuarioLogado.getId_usuario(), this, clientUnloggedWindow).setVisible(true);
	}
	
	public void btnReportarIncidenteActionPerformed() {
		new ReportarIncidente(out, in, usuarioLogado.getToken(), usuarioLogado.getId_usuario()).setVisible(true);;
	}
	
	public void btnRemoverCadastroActionPerformed() {
		new RemoverCadastro(this, clientUnloggedWindow, out, in ,usuarioLogado).setVisible(true);
	}
	
	public void btnVerIncidentesReportadosActionPerformed() throws IOException {
		
		try {
			usuarioLogado.setId_operacao(6);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(usuarioLogado);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			new VerListaDeIncidentes(in).setVisible(true);
			
		}	catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Solicitar Incidentes Reportados", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void btnEditarIncidenteActionPerformed() {
		
		new VerIncidentesEditarIncidente(out, in, usuarioLogado).setVisible(true);
	}
	
	public void btnRemoverIncidenteActionPerformed() {
		new RemoverIncidente(out, in, usuarioLogado).setVisible(true);
	}
	
	public void btnDeslogarActionPerformed() throws IOException {
		
		try {
			
			usuarioLogado.setId_operacao(9);
			
			Gson gson = new Gson();
			
			String json = gson.toJson(usuarioLogado);
			System.out.println("Client sent: " + json);
			out.println(json);
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			Retorno retorno = gson.fromJson(jsonRetorno, Retorno.class);
			
			if(retorno.getCodigo().equals(200)) {
				
				JOptionPane.showMessageDialog(null, "Usuário deslogado com sucesso.", "Logout de Usuário", JOptionPane.INFORMATION_MESSAGE);
				clientUnloggedWindow.setVisible(true);	
			} else {
				throw new GeneralErrorException("Erro ao deslogar usuário");
			
			}

		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Logout de Usuário", JOptionPane.ERROR_MESSAGE);
			gee.printStackTrace();
		} finally {
			
			this.dispose();	
		}
	}
}
