package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import entities.Usuario;

public class UsuarioDAOTeste {

	public static void cadastrarUsuarioTeste() throws SQLException, IOException {
		
		Usuario usuario = new Usuario();
		usuario.setId_operacao(1);
		usuario.setNome("Tais Azevedo");
		usuario.setEmail("tais-azevedo@hotmail.com");
		usuario.setSenha("teste123");
		
		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).cadastrar(usuario);
		
		System.out.println("Cadastro realizado com sucesso.");
	}
	
	public static void buscarTodosUsuariosTeste() throws SQLException, IOException {

		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarios = new UsuarioDAO(conn).buscarTodos();

		for (Usuario usuario : listaUsuarios) {

			System.out.println(usuario.getNome() + " - " + usuario.getEmail() + " - " + usuario.getId_usuario());
		}
	}
	
	public static void buscarUsuarioTeste() throws SQLException, IOException {
		
		Usuario usuario = new Usuario();
		String email = "igorpfcastro@gmail.com";
		String senha = "teste123";
		String hashed = Usuario.hashed(senha);
		usuario.setEmail(email);
		usuario.setSenha(hashed);
		
		Connection conn = BancoDados.conectar();
		Usuario usuarioNovo = new UsuarioDAO(conn).buscarUsuario(usuario);

		if (usuarioNovo != null) {

			System.out.println(usuarioNovo.getNome() + " - " + usuarioNovo.getEmail() + " - " + usuario.getSenha() + " - " + usuarioNovo.getId_usuario());

		} else {

			System.out.println("Usuário não encontrado.");
		}
	}
	
	public static void logarUsuarioTeste() throws SQLException, IOException {
		
		
		Usuario usuario = new Usuario();
		String email = "igorpfcastro@gmail.com";
		String senha = "teste123";
		String token = Usuario.gerarString24Caracteres();
		String hashed = Usuario.hashed(senha);
		usuario.setEmail(email);
		usuario.setSenha(hashed);
		usuario.setToken(token);
		
		Connection conn = BancoDados.conectar();
		new UsuarioDAO(conn).logarUsuario(usuario);
		
		System.out.println("Usuário logado com sucesso.");
	}
	
	public static void main(String[] args) {

		try {

//			UsuarioDAOTeste.cadastrarUsuarioTeste();
			UsuarioDAOTeste.buscarTodosUsuariosTeste();
//			UsuarioDAOTeste.buscarUsuarioTeste();
//			UsuarioDAOTeste.logarUsuarioTeste();

		} catch (SQLException | IOException e) {

			System.out.println(e.getMessage());
		}
	}
}
