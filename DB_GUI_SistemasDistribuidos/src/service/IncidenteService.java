package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;

import dao.BancoDados;
import dao.IncidenteDAO;
import entities.Incidente;
import entities.Retorno;
import exceptions.GeneralErrorException;

public class IncidenteService {

	Gson gson;
	
	public IncidenteService() {
		
	}
	
	public Retorno cadastrar(Incidente incidente) throws SQLException, IOException, GeneralErrorException {
		
		Connection conn = BancoDados.conectar();
		
		if(incidente.getData() != null && 
				incidente.getRodovia() != null && 
				incidente.getTipoIncidente() != null &&
				incidente.getToken() != null &&
				incidente.getId_usuario() != null) {
			if(incidente.getKm() == null) {
				incidente.setKm(000);
			}
			
			conn = BancoDados.conectar();
			new IncidenteDAO(conn).cadastrar(incidente);
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			
			return retorno;
			
		} else {
			throw new GeneralErrorException("Erro ao reportar incidente.");
		}
	}
	
}
