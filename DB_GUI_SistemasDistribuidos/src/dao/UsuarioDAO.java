package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Usuario;

public class UsuarioDAO {

	Connection conn;
	
	public UsuarioDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void cadastrar(Usuario usuario) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"insert into usuario (nome, email, senha) values (?, ?, ?)");
			
			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			
			st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Usuario> buscarTodos() throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from usuario order by nome");

			rs = st.executeQuery();

			List<Usuario> listaUsuarios = new ArrayList<>();

			while (rs.next()) {
				
				Usuario usuario = new Usuario();

				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setToken(rs.getString("token"));
				usuario.setId_usuario(rs.getInt("id_usuario"));
				
				listaUsuarios.add(usuario);
			}

			return listaUsuarios;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	//need to test on UsuarioDAOTeste
	public void atualizarCadastro(Usuario usuario) throws SQLException {
		
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("update usuario set nome = ?, email = ?, senha = ? where id_usuario = ? and token = ?");

			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			st.setInt(4, usuario.getId_usuario());
			st.setString(5, usuario.getToken());

			st.executeUpdate();

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public Usuario buscarUsuario(Usuario usuario) throws SQLException {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("select * from usuario where email = ? and senha = ?");

			st.setString(1, usuario.getEmail());
			st.setString(2, usuario.getSenha());

			rs = st.executeQuery();

			if (rs.next()) {

				Usuario usuarioRetorno = new Usuario();
				
				usuarioRetorno.setNome(rs.getString("nome"));
				usuarioRetorno.setEmail(rs.getString("email"));
				usuarioRetorno.setSenha(rs.getString("senha"));
				usuarioRetorno.setToken(rs.getString("token"));
				usuarioRetorno.setId_usuario(rs.getInt("id_usuario"));
				
				return usuarioRetorno;
			}

			return null;

		} finally {

			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public void logarDeslogarUsuario(Usuario usuario) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update usuario set token = ? where email = ? and senha = ?");
			
			st.setString(1, usuario.getToken());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			
			st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
}
