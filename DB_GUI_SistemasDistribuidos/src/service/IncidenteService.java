package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;

import dao.BancoDados;
import dao.IncidenteDAO;
import dao.UsuarioDAO;
import entities.Incidente;
import entities.Retorno;
import entities.Usuario;
import exceptions.GeneralErrorException;

public class IncidenteService {

	Gson gson;
	private List<Incidente> listaIncidentes;
	
	public IncidenteService() {
		
	}
	
	public Retorno cadastrar(Incidente incidente) throws SQLException, IOException, GeneralErrorException, ParseException {
		
		if(incidente.getId_operacao() == null ||
				incidente.getData().isBlank() ||
				incidente.getRodovia().isBlank() ||
				incidente.getKm() == null ||
				incidente.getTipo_incidente() == null ||
				incidente.getToken().isBlank() ||
				incidente.getId_usuario() == null) {
			
			throw new GeneralErrorException("Campos nulos.");
		}
		
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
	
	public Retorno atualizarIncidente(Incidente incidente) throws SQLException, IOException, GeneralErrorException {
		
		if(incidente.getId_operacao() == null ||
				incidente.getData().isBlank() ||
				incidente.getRodovia().isBlank() ||
				incidente.getKm() == null ||
				incidente.getTipo_incidente() == null ||
				incidente.getToken().isBlank() ||
				incidente.getId_incidente() == null ||
				incidente.getId_usuario() == null) {
			
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();		
		List<Usuario> usuarioRetorno = new UsuarioDAO(conn).buscarTodos();
		
		int contador = 0;
		
		for (Usuario user : usuarioRetorno) {
			if(incidente.getId_usuario().equals(user.getId_usuario()) && incidente.getToken().equals(user.getToken())) {
				break;
			}
			++contador;
			
			if(contador == usuarioRetorno.size()) {
				throw new GeneralErrorException("Id de usuário ou token não condizem com o registro no banco de dados.");
			}
		}
		
		if(incidente.getId_usuario() != null &&
				incidente.getToken() != null) {
			
			conn = BancoDados.conectar();
			new IncidenteDAO(conn).atualizarIncidente(incidente);
			
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			
			return retorno;
		} else {
			throw new GeneralErrorException("Erro ao editar incidente.");
		}
		
	}
	
	public Retorno buscarTodos(Incidente incidente) throws SQLException, IOException, GeneralErrorException, ParseException {
		
		if(incidente.getId_operacao() == null ||
				incidente.getData().isBlank() ||
				incidente.getRodovia().isBlank() ||
				incidente.getPeriodo() == null) {
			
			throw new GeneralErrorException("Campos nulos.");
		}
		
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
	
	public Retorno buscarPorIdUsuario(Usuario usuario) throws SQLException, IOException, GeneralErrorException {
		
		if(usuario.getId_operacao() == null ||
				usuario.getToken().isBlank() ||
				usuario.getId_usuario() == null) {
			
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();
		List<Usuario> usuarioRetorno = new UsuarioDAO(conn).buscarTodos();
		
		int contador = 0;
		
		for (Usuario user : usuarioRetorno) {
			if(usuario.getId_usuario().equals(user.getId_usuario()) && usuario.getToken().equals(user.getToken())) {
				break;
			}
			++contador;
			
			if(contador == usuarioRetorno.size()) {
				throw new GeneralErrorException("Id de usuário ou token não condizem com o registro no banco de dados.");
			}
		}
		
		if(usuario.getId_usuario() != null &&
				usuario.getToken() != null) {
			conn = BancoDados.conectar();
			this.listaIncidentes = new IncidenteDAO(conn).buscarPorIdUsuario(usuario);
			
		
			if(listaIncidentes == null) {
				throw new GeneralErrorException("Não há incidentes reportados.");
			}
			
			Retorno retorno = new Retorno();
			retorno.setCodigo(200);
			retorno.setLista_incidentes(listaIncidentes);
			
			return retorno;
		} else {
			throw new GeneralErrorException("Erro ao buscar usuário.");
		}
	}
	
	public Retorno removerIncidente(Usuario usuario, Incidente incidente) throws SQLException, IOException, GeneralErrorException {
		
		if(incidente.getId_operacao() == null ||
				incidente.getId_incidente() == null ||
				incidente.getToken().isBlank() ||
				incidente.getId_usuario() == null) {
			
			throw new GeneralErrorException("Campos nulos.");
		}
		
		Connection conn = BancoDados.conectar();
		List<Usuario> usuarioRetorno = new UsuarioDAO(conn).buscarTodos();
		
		int contador = 0;
		
		for (Usuario user : usuarioRetorno) {
			if(usuario.getId_usuario().equals(user.getId_usuario()) && usuario.getToken().equals(user.getToken())) {
				break;
			}
			++contador;
			
			if(contador == usuarioRetorno.size()) {
				throw new GeneralErrorException("Id de usuário ou token não condizem com o registro no banco de dados.");
			}
		}
		
		if(usuario.getId_usuario() != null &&
				usuario.getToken() != null) {
			
			conn = BancoDados.conectar();
			int linhasManipuladas = new IncidenteDAO(conn).remover(incidente);
			
			if(linhasManipuladas > 0) {
				Retorno retorno = new Retorno();
				retorno.setCodigo(200);
				
				return retorno;
			} else {
				throw new GeneralErrorException("Não foi encontrado o incidente enviado.");
			}
		} else {
			throw new GeneralErrorException("Erro ao buscar usuário.");
		}
		
	}
}
