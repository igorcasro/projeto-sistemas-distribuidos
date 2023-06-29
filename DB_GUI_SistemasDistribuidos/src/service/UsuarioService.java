package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;
import listener.UsuarioListener;

public class UsuarioService {

	Gson gson;
	
	private UsuarioListener usuarioListener;
	private List<Usuario> listaUsuariosLogados;
	
	public UsuarioService() {
		
		this.listaUsuariosLogados = new ArrayList<>();
		this.usuarioListener = new UsuarioListener();
	}
	
	public Retorno cadastrar(Usuario usuario) throws SQLException, IOException, GeneralErrorException {
		
		if(usuario.getId_operacao() == null ||  
				usuario.getNome() == null ||
				usuario.getEmail() == null ||
				usuario.getSenha() == null ) {
			throw new GeneralErrorException("Campos nulos.");
		}
		
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
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			return retorno;
		} else {
			
			throw new GeneralErrorException("Usuário já cadastrado.");
		}


	}
	
	public Retorno atualizarCadastro(Usuario usuario) throws SQLException, IOException, GeneralErrorException {
		
		if(usuario.getId_operacao() == null ||  
				usuario.getNome().isBlank() ||
				usuario.getEmail().isBlank() ||
				usuario.getSenha().isBlank() ||
				usuario.getToken().isBlank() ||
				usuario.getId_usuario() == null) {
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();
		Usuario usuarioRetorno = new UsuarioDAO(conn).buscarUsuario(usuario);
		
		if(usuarioRetorno == null || usuarioRetorno.getEmail().equals(usuario.getEmail()) || usuarioRetorno.getEmail() == null) {
			if(!this.verificaNome(usuario.getNome())) {
				throw new GeneralErrorException("Nome inválido.");
			}
			
			if(!this.verificaEmail(usuario.getEmail())){
				throw new GeneralErrorException("E-mail inválido.");
			}
			
			if(!this.verificaSenha(usuario.getSenha())) {
				throw new GeneralErrorException("Senha inválida.");
			}
			
			String token = gerarString24Caracteres();
			usuario.setToken(token);
			
			conn = BancoDados.conectar();
			new UsuarioDAO(conn).atualizarCadastro(usuario);
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			retorno.setToken(token);
			return retorno;
		} else if(usuarioRetorno.getEmail() != null) {
			
			throw new GeneralErrorException("E-mail já existe no sistema, favor tentar um novo.");
		} else {
			
			throw new GeneralErrorException("Não foi possível atualizar o usuário.");
		}
		
	}
	
	public Retorno logar(Usuario usuario) throws SQLException, IOException, GeneralErrorException{
		
		if(usuario.getId_operacao() == null ||
				usuario.getEmail() == null ||
				usuario.getSenha() == null ) {
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarios = new UsuarioDAO(conn).buscarTodos();


		int contador = 0;
		if(!listaUsuarios.isEmpty()) {
			
			for (Usuario usuariosRecuperados : listaUsuarios) {
				if(usuariosRecuperados.getEmail().equals(usuario.getEmail())) {	
					if(usuariosRecuperados.getSenha().equals(usuario.getSenha())) {
						if(usuariosRecuperados.getToken() == null) {
							String token = gerarString24Caracteres();
							
							usuario.setToken(token);
							
							conn = BancoDados.conectar();
							new UsuarioDAO(conn).logarUsuario(usuario);
							
							conn = BancoDados.conectar();
							Usuario usuarioLogado = new UsuarioDAO(conn).buscarUsuario(usuario);
			
//							usuarioListener.notifyUsuariosLogadosChanged();
							
							Retorno retorno = new Retorno();
							retorno.setCodigo(200);
							retorno.setToken(usuarioLogado.getToken());
							retorno.setId_usuario(usuarioLogado.getId_usuario());
							
							return retorno;
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
	
	public Retorno deslogar(Usuario usuario) throws SQLException, IOException, GeneralErrorException{
		
		if(usuario.getId_operacao() == null ||
				usuario.getToken().isBlank() ||
				usuario.getId_usuario() == null) {
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarios = new UsuarioDAO(conn).buscarTodos();
		
		int contador = 0;
		if(!listaUsuarios.isEmpty()) {
			for (Usuario usuariosRecuperados : listaUsuarios) {
				if((usuariosRecuperados.getToken() != null) && (usuariosRecuperados.getToken().equals(usuario.getToken()))) {	
					if(usuariosRecuperados.getId_usuario().equals(usuario.getId_usuario())) {
						if(usuariosRecuperados.getToken() != null) {
							usuario.setToken(null);
							
//							usuarioListener.notifyUsuariosLogadosChanged();
							
							conn = BancoDados.conectar();
							new UsuarioDAO(conn).deslogarUsuario(usuario);
							Retorno retorno = new Retorno();
							retorno.setCodigo(200);
							return retorno;
						} else {
							throw new GeneralErrorException("Usuário não está logado.");
						}
						
					} else {
						throw new GeneralErrorException("Id de usuário incorreto.");
					}
				}
				contador++;
				if(contador == listaUsuarios.size()) {
					throw new GeneralErrorException("Token ou id_usuario incorretos.");
				}
			}
		} else {
			
			throw new GeneralErrorException("Nenhum usuário cadastrado.");
		}
		
		
		Retorno retorno = new Retorno();
		retorno.setCodigo(500);
		return retorno;
	}
	
	public Retorno removerUsuario(Usuario usuario) throws SQLException, IOException, GeneralErrorException {

		if(usuario.getId_operacao() == null || 
				usuario.getEmail().isBlank() ||
				usuario.getSenha().isBlank() ||
				usuario.getToken().isBlank() ||
				usuario.getId_usuario() == null) {
			throw new GeneralErrorException("Campos nulos.");
		}		
		
		Connection conn = BancoDados.conectar();
		Usuario usuarioRetorno = new Usuario();
		usuarioRetorno = new UsuarioDAO(conn).buscarUsuario(usuario);
		
		if(usuarioRetorno != null) {
			if(usuario.getEmail().equals(usuarioRetorno.getEmail())) {
				if(usuario.getSenha().equals(usuarioRetorno.getSenha())) {
					if(usuario.getToken().equals(usuarioRetorno.getToken()) &&
							usuario.getId_usuario().equals(usuarioRetorno.getId_usuario())) {
						conn = BancoDados.conectar();
						int linhasManipuladas = new UsuarioDAO(conn).remover(usuario);
								
						if(linhasManipuladas > 0) {
							Retorno retorno = new Retorno();
							retorno.setCodigo(200);
									
							return retorno;
						} else {
							throw new GeneralErrorException("Não foi encontrado o usuário especificado para remoção.");
						}
					} else {
						throw new GeneralErrorException("Token ou Id de usuário incorretos.");
					}
					
				} else {
					throw new GeneralErrorException("Senha incorreta.");
				}
			} else {
				throw new GeneralErrorException("E-mail incorreto.");
			}
		} else {
			throw new GeneralErrorException("Usuário não encontrado");
		}
		
	}
	
	public List<Usuario> buscarUsuariosLogados() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		List<Usuario> listaUsuarioRetorno = new UsuarioDAO(conn).buscarTodos();
		
		if(listaUsuarioRetorno != null) {
			for(Usuario usuario : listaUsuarioRetorno) {
				if(usuario.getEmail() != null) {
					if(usuario.getToken() != null) {

						listaUsuariosLogados.add(usuario);
					}
				}		
			}
		}
		
		
		System.out.println(listaUsuariosLogados);
		
		return listaUsuariosLogados;
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
