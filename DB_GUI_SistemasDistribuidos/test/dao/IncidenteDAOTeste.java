package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import entities.Incidente;

public class IncidenteDAOTeste {
	
	public static void cadastrarIncidenteTeste() throws SQLException, IOException, ParseException {
		
		Incidente incidente = new Incidente();
		incidente.setId_operacao(4);
		incidente.setData("2023-05-25 11:13:00");
		incidente.setRodovia("BR-153");
		incidente.setKm(123);
		incidente.setTipo_incidente(1);
		incidente.setToken("AAAAAAAAAAAAAAAAAAAAA");
		incidente.setId_usuario(23);
		
		System.out.println(incidente);
		
		Connection conn = BancoDados.conectar();
		new IncidenteDAO(conn).cadastrar(incidente);
		
		System.out.println("Incidente cadastrado com sucesso.");
	}
	
	public static void buscarTodosTeste() throws SQLException, IOException, ParseException {
		
		Incidente incidente = new Incidente();
		incidente.setId_operacao(5);
		incidente.setData("2023-05-31");
		incidente.setRodovia("BR-555");
		incidente.setPeriodo(3);
		incidente.setFaixa_km("111-555");
		
		Connection conn = BancoDados.conectar();
		List<Incidente> listaIncidente = new IncidenteDAO(conn).buscarTodosComFaixaKm(incidente);
		
		for (Incidente incidenteEncontrado : listaIncidente) {
			
			System.out.println(incidenteEncontrado.getId_incidente() + " - " + incidenteEncontrado.getTipo_incidente() +
					" - " + incidenteEncontrado.getKm() + " - " + incidenteEncontrado.getRodovia() + " - " + incidenteEncontrado.getData());
		}
		
	}
	
	public static void main(String[] args) {

		try {

//			IncidenteDAOTeste.cadastrarIncidenteTeste();
			IncidenteDAOTeste.buscarTodosTeste();

		} catch (SQLException | IOException | ParseException e) {

			System.out.println(e.getMessage());
		}
	}
}
