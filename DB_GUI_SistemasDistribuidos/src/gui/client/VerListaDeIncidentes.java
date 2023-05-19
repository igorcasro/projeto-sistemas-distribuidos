package gui.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VerListaDeIncidentes extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VerListaDeIncidentes() {
		setTitle("Lista de Incidentes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
	}

}
