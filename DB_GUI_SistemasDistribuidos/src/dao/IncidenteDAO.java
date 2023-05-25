package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Incidente;
import entities.Usuario;

public class IncidenteDAO {

	Connection conn;
	
	public IncidenteDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void cadastrar(Incidente incidente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"insert into incidente (data, rodovia, km, tipo_incidente, token, id_usuario) values (?, ?, ?, ?, ?, ?)");
			
			st.setString(1, incidente.getData());
			st.setString(2, incidente.getRodovia());
			st.setInt(3, incidente.getKm());
			st.setInt(4, incidente.getTipoIncidente());
			st.setString(5, incidente.getToken());
			st.setInt(6, incidente.getId_usuario());
			
			st.executeQuery();
			
		}	finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public List<Incidente> buscarTodos() throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {

			st = conn.prepareStatement("select * from incidente order by nome");

			rs = st.executeQuery();

			List<Incidente> listaIncidentes = new ArrayList<>();

			while (rs.next()) {
				
				Incidente incidente = new Incidente();

				incidente.setData(rs.getString("data"));
				incidente.setRodovia(rs.getString("rodovia"));
				incidente.setKm(rs.getInt("km"));
				incidente.setTipoIncidente(rs.getInt("tipo_incidente"));
				incidente.setToken(rs.getString("token"));
				incidente.setId_usuario(rs.getInt("id_usuario"));
				incidente.setId_incidente(rs.getInt("id_incidente"));
				
				listaIncidentes.add(incidente);
			}

			return listaIncidentes;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
}
