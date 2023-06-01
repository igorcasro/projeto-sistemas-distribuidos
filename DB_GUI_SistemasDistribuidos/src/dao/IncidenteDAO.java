package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Incidente;

public class IncidenteDAO {

	Connection conn;
	
	public IncidenteDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void cadastrar(Incidente incidente) throws SQLException, ParseException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"insert into incidente (data, rodovia, km, tipo_incidente, token, id_usuario) values (?, ?, ?, ?, ?, ?)");
			
			// Converter a string da data em java.sql.Timestamp
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date data = sdf.parse(incidente.getData());
	        Timestamp dataTimestamp = new Timestamp(data.getTime());
			
	        st.setTimestamp(1, dataTimestamp);
			st.setString(2, incidente.getRodovia());
			st.setInt(3, incidente.getKm());
			st.setInt(4, incidente.getTipo_incidente());
			st.setString(5, incidente.getToken());
			st.setInt(6, incidente.getId_usuario());
			
			st.executeUpdate();
			
		}	finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public List<Incidente> buscarTodosSemFaixaKm(Incidente incidente) throws SQLException, ParseException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {

			st = conn.prepareStatement("select * from incidente where rodovia = ? and data between ? and ?");

			Date dataInicial = null;
			Date dataFinal = null;
			
			// Converter a string da data inicial em java.sql.Timestamp
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    if(incidente.getPeriodo() == 1) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "06:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "11:59:59");
		    }
		    if(incidente.getPeriodo() == 2) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "12:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "17:59:59");
		    }
		    if(incidente.getPeriodo() == 3) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "18:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "23:59:59");
		    }
		    if(incidente.getPeriodo() == 4) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "00:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "05:59:59");
		    }
		    
		    Timestamp dataInicialTimestamp = new Timestamp(dataInicial.getTime());
		    Timestamp dataFinalTimestamp = new Timestamp(dataFinal.getTime());
			
		    st.setString(1, incidente.getRodovia());
			st.setTimestamp(2, dataInicialTimestamp);
			st.setTimestamp(3, dataFinalTimestamp);
		    
			rs = st.executeQuery();

			List<Incidente> listaIncidentes = new ArrayList<>();

			while (rs.next()) {
				
				Incidente incidenteEncontrado = new Incidente();

				incidenteEncontrado.setData(rs.getString("data"));
				incidenteEncontrado.setRodovia(rs.getString("rodovia"));
				incidenteEncontrado.setKm(rs.getInt("km"));
				incidenteEncontrado.setTipo_incidente(rs.getInt("tipo_incidente"));
				incidenteEncontrado.setId_incidente(rs.getInt("id_incidente"));
				
				listaIncidentes.add(incidenteEncontrado);
			}

			return listaIncidentes;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
public List<Incidente> buscarTodosComFaixaKm(Incidente incidente) throws SQLException, ParseException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {

			st = conn.prepareStatement("select * from incidente where rodovia = ? and data between ? and ? and km between ? and ?");

			Date dataInicial = null;
			Date dataFinal = null;
			
			// Converter a string da data inicial em java.sql.Timestamp
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    if(incidente.getPeriodo() == 1) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "06:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "11:59:59");
		    }
		    if(incidente.getPeriodo() == 2) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "12:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "17:59:59");
		    }
		    if(incidente.getPeriodo() == 3) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "18:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "23:59:59");
		    }
		    if(incidente.getPeriodo() == 4) {
		    	dataInicial = sdf.parse(incidente.getData() + " " + "00:00:00");
		    	dataFinal = sdf.parse(incidente.getData() + " " + "05:59:59");
		    }
		    
		    Timestamp dataInicialTimestamp = new Timestamp(dataInicial.getTime());
		    Timestamp dataFinalTimestamp = new Timestamp(dataFinal.getTime());
			
		    st.setString(1, incidente.getRodovia());
			st.setTimestamp(2, dataInicialTimestamp);
			st.setTimestamp(3, dataFinalTimestamp);
		    
			String[] inicioFim = incidente.getFaixa_km().split("-");
			String kmInicio = inicioFim[0];
			String kmFim = inicioFim[1];
			
			st.setString(4, kmInicio);
			st.setString(5, kmFim);
			
			rs = st.executeQuery();

			List<Incidente> listaIncidentes = new ArrayList<>();

			while (rs.next()) {
				
				Incidente incidenteEncontrado = new Incidente();

				incidenteEncontrado.setData(rs.getString("data"));
				incidenteEncontrado.setRodovia(rs.getString("rodovia"));
				incidenteEncontrado.setKm(rs.getInt("km"));
				incidenteEncontrado.setTipo_incidente(rs.getInt("tipo_incidente"));
				incidenteEncontrado.setId_incidente(rs.getInt("id_incidente"));
				
				listaIncidentes.add(incidenteEncontrado);
			}

			return listaIncidentes;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
