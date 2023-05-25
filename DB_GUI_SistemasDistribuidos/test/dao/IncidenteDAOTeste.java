package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.Incidente;

public class IncidenteDAOTeste {
	
	public static void cadastrarIncidenteTeste() throws SQLException, IOException {
		
		Incidente incidente = new Incidente();
		incidente.setId_operacao(4);
		incidente.setData("2023-05-25 11:13:00");
		incidente.setRodovia("BR-153");
		incidente.setKm(123);
		incidente.setTipoIncidente(1);
		incidente.setToken("AAAAAAAAAAAAAAAAAAAAA");
		incidente.setId_usuario(23);
		
		System.out.println(incidente);
		
		Connection conn = BancoDados.conectar();
		new IncidenteDAO(conn).cadastrar(incidente);
		
		System.out.println("Incidente cadastrado com sucesso.");
	}
	
	public static void buscarTodosTeste() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		List<Incidente> listaIncidente = new IncidenteDAO(conn).buscarTodosSemDadosUsuario();
		
		for (Incidente incidente : listaIncidente) {
			
			System.out.println(incidente.getId_incidente() + " - " + incidente.getId_usuario() + " - " + incidente.getToken() +
					" - " + incidente.getTipoIncidente() + " - " + incidente.getKm() +
					" - " + incidente.getRodovia() + " - " + incidente.getData());
		}
		
	}
	
	public static void main(String[] args) {

		try {

//			IncidenteDAOTeste.cadastrarIncidenteTeste();
			IncidenteDAOTeste.buscarTodosTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}
}
