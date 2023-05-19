package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;
import exceptions.GeneralErrorException;

public class UsuarioService {

	Gson gson;
	
	public UsuarioService() {
		
	}
	
	public void cadastrar(Usuario usuario) throws SQLException, IOException, GeneralErrorException {
		
		Connection conn = BancoDados.conectar();
		Usuario usuarioRetorno = new UsuarioDAO(conn).buscarUsuario(usuario);
		
		if(usuarioRetorno == null || usuarioRetorno.getEmail() == null) {
			if(!this.verificaNome(usuario.getNome())) {
				throw new GeneralErrorException("Nome inválido.");
			}
			
			if(!this.verificaEmail(usuario.getEmail())){
				throw new GeneralErrorException("E-mail inválido.");
			}
			
			if(!this.verificaSenha(usuario.getSenha())) {
				throw new GeneralErrorException("Senha inválida.");
			}
			
			conn = BancoDados.conectar();
			new UsuarioDAO(conn).cadastrar(usuario);
		} else {
			
			throw new GeneralErrorException("Usuário já cadastrado.");
		}


	}
	
	public Usuario logar(Usuario usuario) throws SQLException, IOException, GeneralErrorException{
		
		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarios = new UsuarioDAO(conn).buscarTodos();


		int contador = 0;
		if(!listaUsuarios.isEmpty()) {
			
			for (Usuario usuariosRecuperados : listaUsuarios) {
				System.out.println(usuariosRecuperados);
				if(usuariosRecuperados.getEmail().equals(usuario.getEmail())) {	
					if(usuariosRecuperados.getSenha().equals(usuario.getSenha())) {
						if(usuariosRecuperados.getToken() == null) {
							String token = gerarString24Caracteres();
							
							usuario.setToken(token);
							
							conn = BancoDados.conectar();
							new UsuarioDAO(conn).logarDeslogarUsuario(usuario);
							
							conn = BancoDados.conectar();
							Usuario usuarioLogado = new UsuarioDAO(conn).buscarUsuario(usuario);
							return usuarioLogado;
						} else {
							throw new GeneralErrorException("Usuário já está logado.");
						}
						
					} else {
						throw new GeneralErrorException("Senha incorreta.");
					}
				}
				contador++;
				if(contador == (listaUsuarios.size())) {
					throw new GeneralErrorException("E-mail ou senha incorretos.");
				}
			}

			
			return null;
		} else {
			
			throw new GeneralErrorException("Nenhum usuário cadastrado.");
		}
		
	}
	
	public void deslogar(Usuario usuario) throws SQLException, IOException, GeneralErrorException{
		
		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarios = new UsuarioDAO(conn).buscarTodos();
		
		int contador = 0;
		if(!listaUsuarios.isEmpty()) {
			
			for (Usuario usuariosRecuperados : listaUsuarios) {
				if(usuariosRecuperados.getEmail().equals(usuario.getEmail())) {	
					if(usuariosRecuperados.getSenha().equals(usuario.getSenha())) {
						if(usuariosRecuperados.getToken() != null) {
							usuario.setToken(null);
							
							conn = BancoDados.conectar();
							new UsuarioDAO(conn).logarDeslogarUsuario(usuario);
							break;
						} else {
							throw new GeneralErrorException("Usuário não está logado.");
						}
						
					} else {
						throw new GeneralErrorException("Senha incorreta.");
					}
				}
				contador++;
				if(contador == listaUsuarios.size()) {
					throw new GeneralErrorException("E-mail ou senha incorretos.");
				}
			}
		} else {
			
			throw new GeneralErrorException("Nenhum usuário cadastrado.");
		}
		
	}
	
	//Verificações e Funções Extras
	
	private boolean verificaNome(String nome) {
		if(nome.length() < 3 || nome.length() > 32) {
			String messageNome = "Insira um nome com tamanho entre 3 e 32 caracteres.";
			System.out.println(messageNome);
			return false;
		}
		
		return true;		
	}
	
	private boolean verificaEmail(String email) {
		boolean resultadoVerificacao = verificaArroba(email);
		
		if(!resultadoVerificacao) {
			System.out.println("E-mail não contém @, favor inserir um e-mail válido.");
			email = "";
		}
		
		if (email.length() < 16 || email.length() > 50) {
			System.out.println("Insira um e-mail com tamanho entre 16 e 50 caracteres.");			
			return false;
		}
		
		return true;		
	}
	
	private boolean verificaSenha(String senha) {
		if(senha.length() < 8 || senha.length() > 32) {
			System.out.println("Insira uma senha com tamanho entre 8 e 32 caracteres.");
			return false;
		}
		
		return true;		
	}
	
	public static boolean verificaArroba(String email) {
    	
    	char compare = '@';
    	
    	for(int i = 0; i < email.length(); i++) {
    		
    		char c = email.charAt(i);
    		
    		if(c == compare) {
    			return true;
    		}
    	}
    	
    	return false;
    }
	
	private static String gerarString24Caracteres() {
		
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(24);
		
		for (int i = 0; i < 24; i++) {
			int index = (int)(caracteres.length() * Math.random());
			sb.append(caracteres.charAt(index));
		}
		
		return sb.toString();
	}
}
