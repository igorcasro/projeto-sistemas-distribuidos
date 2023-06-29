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
import entities.Usuario;

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
	
	public void atualizarIncidente(Incidente incidente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {

			st = conn.prepareStatement("update incidente set data = ?, rodovia = ?, km = ?, tipo_incidente = ? where id_incidente = ?");

			st.setString(1, incidente.getData());
			st.setString(2, incidente.getRodovia());
			st.setInt(3, incidente.getKm());
			st.setInt(4, incidente.getTipo_incidente());
			st.setInt(5, incidente.getId_incidente());
//			st.setInt(6, incidente.getId_usuario());
			
			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public int remover(Incidente incidente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from incidente where id_incidente = ?");
			
			st.setInt(1, incidente.getId_incidente());
			
			int linhasManipuladas = st.executeUpdate();
			
			return linhasManipuladas;
		} finally {

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
			String[] dataSplit = incidente.getData().split(" ");
			String data = dataSplit[0];
			
			// Converter a string da data inicial em java.sql.Timestamp
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    if(incidente.getPeriodo() == 1) {
		    	dataInicial = sdf.parse(data + " " + "06:00:00");
		    	dataFinal = sdf.parse(data + " " + "11:59:59");
		    } else if(incidente.getPeriodo() == 2) {
		    	dataInicial = sdf.parse(data + " " + "12:00:00");
		    	dataFinal = sdf.parse(data + " " + "17:59:59");
		    } else if(incidente.getPeriodo() == 3) {
		    	dataInicial = sdf.parse(data + " " + "18:00:00");
		    	dataFinal = sdf.parse(data + " " + "23:59:59");
		    }else if(incidente.getPeriodo() == 4) {
		    	dataInicial = sdf.parse(data + " " + "00:00:00");
		    	dataFinal = sdf.parse(data + " " + "05:59:59");
		    } else {
		    	dataInicial = sdf.parse(data + " " + "00:00:00");
		    	dataFinal = sdf.parse(data + " " + "23:59:59");
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
			String[] dataSplit = incidente.getData().split(" ");
			String data = dataSplit[0];
			
			// Converter a string da data inicial em java.sql.Timestamp
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    if(incidente.getPeriodo() == 1) {
		    	dataInicial = sdf.parse(data + " " + "06:00:00");
		    	dataFinal = sdf.parse(data + " " + "11:59:59");
		    }
		    if(incidente.getPeriodo() == 2) {
		    	dataInicial = sdf.parse(data + " " + "12:00:00");
		    	dataFinal = sdf.parse(data + " " + "17:59:59");
		    }
		    if(incidente.getPeriodo() == 3) {
		    	dataInicial = sdf.parse(data + " " + "18:00:00");
		    	dataFinal = sdf.parse(data + " " + "23:59:59");
		    }
		    if(incidente.getPeriodo() == 4) {
		    	dataInicial = sdf.parse(data + " " + "00:00:00");
		    	dataFinal = sdf.parse(data + " " + "05:59:59");
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

	public List<Incidente> buscarPorIdUsuario(Usuario usuario) throws SQLException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
	
			st = conn.prepareStatement("select * from incidente where id_usuario = ?");
			
		    st.setInt(1, usuario.getId_usuario());
			
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
