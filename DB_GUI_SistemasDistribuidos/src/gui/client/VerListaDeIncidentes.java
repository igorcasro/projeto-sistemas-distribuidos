package gui.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import entities.Incidente;
import entities.Retorno;
import exceptions.GeneralErrorException;

public class VerListaDeIncidentes extends JFrame {

	private JPanel contentPane;
	private JTable tableIncidentes;
	private JPanel painelIncidentes;
	private JScrollPane scrollPane;
	

	private BufferedReader in;
	private static Retorno retorno;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VerListaDeIncidentes(BufferedReader in) throws IOException {
		this.in = in;
		
		this.initComponents();
		
	}
	
	private void buscarIncidentes(Retorno retorno) {
		
		DefaultTableModel modelo = (DefaultTableModel) tableIncidentes.getModel();
		modelo.fireTableDataChanged();
		modelo.setRowCount(0);
		
//		Continuar com o retorno do que o server enviar
		List<Incidente> incidentesLista = retorno.getLista_incidentes();
		
		for (Incidente incidente : incidentesLista) {
			
			modelo.addRow(new Object[] {incidente.getId_incidente(), incidente.getData(), incidente.getRodovia(), 
					incidente.getKm(), converteTipoIncidente(incidente.getTipo_incidente())});
		}
		
		
	}
	
	private void initComponents() throws IOException {

		try {
			Gson gson = new Gson();
			
			String jsonRetorno = in.readLine();
			
			System.out.println("Server sent: " + jsonRetorno);
			retorno = gson.fromJson(jsonRetorno, Retorno.class);
			
			if(retorno.getCodigo().equals(200)) {
				
				JOptionPane.showMessageDialog(null, "Lista de incidentes recuperada com sucesso.", "Solicitar Lista de Incidentes", JOptionPane.INFORMATION_MESSAGE);
			} else {
				
				throw new GeneralErrorException("Erro ao buscar lista de incidentes.");
			}
		} catch (GeneralErrorException gee) {
			
			JOptionPane.showMessageDialog(null, gee.getMessage(), "Solicitar Lista de Incidentes", JOptionPane.ERROR_MESSAGE);

			this.dispose();
		}
		
		
		setTitle("Lista de Incidentes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 651, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		painelIncidentes = new JPanel();
		painelIncidentes.setBorder(new TitledBorder(null, "Incidentes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelIncidentes.setBounds(10, 10, 615, 243);
		contentPane.add(painelIncidentes);
		painelIncidentes.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 593, 209);
		painelIncidentes.add(scrollPane);
		
//		"id_incidente": id_incidente, "data": date, "rodovia": "rodovia", "km": km, "tipo_incidente": tipo_incidente
		
		tableIncidentes = new JTable();
		tableIncidentes.setRowSelectionAllowed(false);
		tableIncidentes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Incidente", "Data", "Rodovia", "Km", "Tipo Incidente"
			}
		));
		
		tableIncidentes.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableIncidentes.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableIncidentes.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableIncidentes.getColumnModel().getColumn(3).setPreferredWidth(50);
		tableIncidentes.getColumnModel().getColumn(4).setPreferredWidth(80);
		scrollPane.setViewportView(tableIncidentes);
	
		this.buscarIncidentes(retorno);
	}
	
	private String converteTipoIncidente(int tipo_incidente) {
		
		if(tipo_incidente == 1)
			return "Vento";
		if(tipo_incidente == 2)
			return "Chuva";
		if(tipo_incidente == 3)
			return "Nevoeiro";
		if(tipo_incidente == 4)
			return "Neve";
		if(tipo_incidente == 5)
			return "Gelo na Pista";
		if(tipo_incidente == 6)
			return "Granizo";
		if(tipo_incidente == 7)
			return "Trânsito parado";
		if(tipo_incidente == 8)
			return "Filas de trânsito";
		if(tipo_incidente == 9)
			return "Trânsito lento";
		if(tipo_incidente == 10)
			return "Acidente desconhecido";
		if(tipo_incidente == 11)
			return "Incidente desconhecido";
		if(tipo_incidente == 12)
			return "Trabalhos na estrada";
		if(tipo_incidente == 13)
			return "Bloqueio de pista";
		if(tipo_incidente == 14)
			return "Bloqueio de Estrada";
		
		return "NÃO RECONHECIDO";
	}
}
