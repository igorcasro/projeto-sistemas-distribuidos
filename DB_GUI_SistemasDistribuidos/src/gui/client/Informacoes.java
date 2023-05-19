package gui.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Informacoes extends JFrame {

	private JPanel contentPane;
	private JButton btnFechar;
	private JLabel lblSistemasDistribudos;
	private JLabel lblUTFPR;
	private JLabel lblIgorPereiraFernandes;

	/**
	 * Create the frame.
	 */
	public Informacoes() {
		setTitle("Informações");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblIgorPereiraFernandes = new JLabel("Igor Pereira Fernandes Castro");
		lblIgorPereiraFernandes.setFont(new Font("Dialog", Font.BOLD, 20));
		lblIgorPereiraFernandes.setHorizontalAlignment(SwingConstants.CENTER);
		lblIgorPereiraFernandes.setBounds(12, -2, 416, 30);
		contentPane.add(lblIgorPereiraFernandes);
		
		lblUTFPR = new JLabel("Universidade Tecnológica Federal do Paraná");
		lblUTFPR.setHorizontalAlignment(SwingConstants.CENTER);
		lblUTFPR.setBounds(12, 40, 416, 15);
		contentPane.add(lblUTFPR);
		
		lblSistemasDistribudos = new JLabel("Sistemas Distribuídos - 2023.1");
		lblSistemasDistribudos.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemasDistribudos.setBounds(12, 67, 416, 15);
		contentPane.add(lblSistemasDistribudos);
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnFecharActionListener();
			}
		});
		btnFechar.setBounds(165, 94, 117, 25);
		contentPane.add(btnFechar);
	}
	
	private void btnFecharActionListener() {
		
		this.dispose();
	}
}
