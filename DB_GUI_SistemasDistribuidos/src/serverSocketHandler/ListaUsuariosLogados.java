package serverSocketHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entities.Usuario;
import listener.UsuarioListener;
import listener.UsuarioListenerCallback;
import service.UsuarioService;

public class ListaUsuariosLogados extends JFrame implements UsuarioListenerCallback {

	private JPanel contentPane;
	private JTable tableUsuariosLogados;
	private JScrollPane scrollPane;

	private UsuarioService usuarioService;
	private UsuarioListener usuarioListener;
	
	/**
	 * Create the frame.
	 */
	public ListaUsuariosLogados(UsuarioListener usuarioListener) {
		
		this.usuarioService = new UsuarioService();

        this.initComponents();

        this.usuarioListener = usuarioListener;

        usuarioListener.addListener(this);

        buscarUsuarios();
    }

    // ...

    @Override
    public void onUsuariosLogadosChanged() {
        buscarUsuarios();
    }
	
	private void buscarUsuarios() {

		DefaultTableModel modelo = (DefaultTableModel) tableUsuariosLogados.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);

		List<Usuario> usuarios;
		try {
			usuarios = this.usuarioService.buscarUsuariosLogados();
			for (Usuario usuario : usuarios) {

				modelo.addRow(new Object[] { usuario.getId_usuario(), usuario.getEmail() });
			}
		
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public void initComponents() {
		
		setTitle("Lista Usuários Logados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 416, 243);
		contentPane.add(scrollPane);
		
		tableUsuariosLogados = new JTable();
		scrollPane.setViewportView(tableUsuariosLogados);
		tableUsuariosLogados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Usuário", "E-mail"
			}
		));
	}
}
