package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;

import dao.BancoDados;
import dao.IncidenteDAO;
import entities.Incidente;
import entities.Retorno;
import exceptions.GeneralErrorException;

public class IncidenteService {

	Gson gson;
	private List<Incidente> listaIncidentes;
	
	public IncidenteService() {
		
	}
	
	public Retorno cadastrar(Incidente incidente) throws SQLException, IOException, GeneralErrorException, ParseException {
		
		Connection conn = BancoDados.conectar();
		
		if(incidente.getData().isBlank() || incidente.getRodovia().isBlank() || incidente.getKm() == null) {
			throw new GeneralErrorException("Campos vazios.");
		}
		
		if(incidente.getData() != null && 
				incidente.getRodovia() != null && 
				incidente.getTipo_incidente() != null &&
				incidente.getToken() != null &&
				incidente.getId_usuario() != null &&
				incidente.getKm() != null) {
			
			conn = BancoDados.conectar();
			new IncidenteDAO(conn).cadastrar(incidente);
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			
			return retorno;
			
		} else {
			throw new GeneralErrorException("Erro ao reportar incidente.");
		}
	}
	
	public Retorno buscarTodos(Incidente incidente) throws SQLException, IOException, GeneralErrorException, ParseException {
		
		Connection conn = BancoDados.conectar();
		
		if(incidente.getData() != null &&
				incidente.getRodovia() != null &&
				incidente.getPeriodo() != null) {
			if (incidente.getFaixa_km() == null) {
				this.listaIncidentes = new IncidenteDAO(conn).buscarTodosSemFaixaKm(incidente);
			} else {
				this.listaIncidentes = new IncidenteDAO(conn).buscarTodosComFaixaKm(incidente);
			}
		
			if(listaIncidentes == null) {
				throw new GeneralErrorException("Não há incidentes reportados.");
			}
			
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			retorno.setLista_incidentes(listaIncidentes);
			
			return retorno;
		} else {
			throw new GeneralErrorException("Erro ao buscar lista de incidentes.");
		}
		
		
	}
}
