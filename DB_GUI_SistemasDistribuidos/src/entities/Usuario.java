package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import exceptions.GeneralErrorException;

public class Usuario {
	private int id_operacao;
	private String nome;
	private String email;
	private String senha;
	private String token;
	private Integer id_usuario;	
	private static Integer success;
	private static Integer error;
	private static Integer ID_USUARIO = 1;
	private Gson gson;
	private List<Usuario> listaUsuariosCadastrados = new ArrayList<Usuario>();
	private List<Usuario> listaUsuariosLogados = new ArrayList<Usuario>();
	
	
	public Usuario () {	
		
		success = 200;
		error = 500;
		gson = new Gson();
	}

	public int getId_operacao() {
		return id_operacao;
	}

	public void setId_operacao(int id_operacao) {
		this.id_operacao = id_operacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", email=" + email + ", senha=" + senha + ", token=" + token + ", id_usuario="
				+ id_usuario + "]";
	}

	public Usuario cadastrarCliente(BufferedReader stdIn) throws IOException {
		
		Usuario usuario = new Usuario();
		
		do {
			System.out.print("Insira seu nome: ");
			this.nome = stdIn.readLine();
		} while(!verificaNome(this.nome));
		
		do {
			System.out.print("Insira seu e-mail: ");
			this.email = stdIn.readLine();
		} while(!verificaEmail(this.email));
		
		do {
			System.out.print("Insira sua senha: ");
			senha = stdIn.readLine();
		} while(!verificaSenha(senha));
		String hashed = hashed(senha);
		
		
		this.id_operacao = 1;
		this.senha = hashed;

		return usuario;
	}

	public List<Usuario> cadastrarServidor(PrintWriter out, Usuario user) throws GeneralErrorException {
				
		Usuario usuario = user;
		
		for ( Usuario usuarioCadastrado : listaUsuariosCadastrados) {
			if(usuarioCadastrado.getEmail().equals(usuario.getEmail())) {
    			throw new GeneralErrorException("E-mail já cadastrado.");
			}
		}
		
		if (usuario.getNome().isBlank() ||
    			usuario.getEmail().isBlank() ||
    			usuario.getSenha().isBlank()) {
    			
    			throw new GeneralErrorException("Erro ao cadastrar usuário.");
    			
    	} else {
    		listaUsuariosCadastrados.add(usuario);
    			
    		System.out.println("Usuário cadastrado com sucesso.");
    			
    		String json = sucessoCadastro(gson);	
    		out.println(json);
    	}
		
		return listaUsuariosCadastrados;
	}
	
	public Usuario logarCliente(BufferedReader stdIn) throws IOException {
		
		Usuario usuario = new Usuario();
		
		do {
			System.out.print("Insira seu e-mail: ");
			this.email = stdIn.readLine();
		} while(!verificaEmail(this.email));
		
		do {
			System.out.print("Insira sua senha: ");
			senha = stdIn.readLine();
		} while(!verificaSenha(senha));
		String hashed = hashed(senha);
		
		this.id_operacao = 3;
		this.senha = hashed;
		
		return usuario;
	}
	
	public List<Usuario> logarServidor(PrintWriter out, Usuario user) throws GeneralErrorException {
		user.getEmail();
		user.getSenha();

		// Verifica se já está logado
		if(!listaUsuariosLogados.isEmpty()) {
			for (Usuario logado : listaUsuariosLogados) {
				if(user.getEmail().equals(logado.getEmail()) &&
						user.getSenha().equals(logado.getSenha())) {
					
        			throw new GeneralErrorException("Usuário já logado.");
				}
			}
		} 

		// Verifica se o usuário está cadastrado
		int index = 0;
		if(listaUsuariosCadastrados.isEmpty()) {
			throw new GeneralErrorException("Nenhum usuário cadastrado.");
		} else {
			for (Usuario usuarioVerifica : listaUsuariosCadastrados) {
				if(user.getEmail().equals(usuarioVerifica.getEmail()) &&
						user.getSenha().equals(usuarioVerifica.getSenha())) {
					
					listaUsuariosLogados.add(usuarioVerifica);
					
					System.out.println("Usuário logado com sucesso.");
					String json = sucessoLogin(gson, usuarioVerifica);
        			out.println(json);
					break;
				}
				index++;
				
				if(listaUsuariosCadastrados.size() == index) {
					System.out.println("Erro ao logar.");
        			throw new GeneralErrorException("Erro ao logar usuário.");
				}
			}
		}
		
		return listaUsuariosLogados;
	}
	
	public Usuario deslogarCliente(String tokenReceived, Integer idReceived) {
		
		Usuario usuario = new Usuario();
		
		this.id_operacao = 9;
		this.token = tokenReceived;
		this.id_usuario = idReceived;
		
		return usuario;
	}
	
	public List<Usuario> deslogarServidor(PrintWriter out, Usuario user) throws GeneralErrorException {
		
		user.getToken();
		user.getId_usuario();
		
		int index = 0;
		if(!listaUsuariosLogados.isEmpty()) {
			for(Usuario usuarioLogado : listaUsuariosLogados) {
				if( (user.getToken().equals(usuarioLogado.getToken())) &&
					(user.getId_usuario() == usuarioLogado.getId_usuario())
					) {
					
					listaUsuariosLogados.remove(usuarioLogado);
					
					System.out.println("Usuário deslogado com sucesso.");
					
					String json = sucessoLogout(gson);
					out.println(json);
					
					break;
				}
				
				index++;
				if(listaUsuariosLogados.size() == index) {
					System.out.println("Error ao deslogar.");
        			throw new GeneralErrorException("Erro ao deslogar usuário.");
				}
			}

			return listaUsuariosLogados;
			
		} else {
			throw new GeneralErrorException("Nenhum usuário logado.");
		}
	}
	
	
	// Funções auxiliares
	
	public static String sucessoCadastro(Gson gson) {
		
		Retorno successObject = new Retorno();
		successObject.setCodigo(success);
		
		String json = gson.toJson(successObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
	
	public static String sucessoLogin(Gson gson, Usuario usuario) {
		String token = gerarString24Caracteres();
		
		usuario.setId_usuario(ID_USUARIO);
		usuario.setToken(token);
		
		Retorno successObject = new Retorno();
		successObject.setCodigo(success);
		successObject.setToken(token);
		successObject.setId_usuario(ID_USUARIO);
		
		++ID_USUARIO;
		
		String json = gson.toJson(successObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
	
	public static String sucessoLogout(Gson gson) {
		
		Retorno successObject = new Retorno();
		successObject.setCodigo(success);
		
		String json = gson.toJson(successObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
	
	public static String erro(Gson gson) {
		String mensagem = "Problemas com a operação solicitada.";
		
		Retorno errorObject = new Retorno();
		errorObject.setCodigo(error);
		errorObject.setMensagem(mensagem);
		
		String json = gson.toJson(errorObject);
		System.out.println("Server sent: " + json);
		
		return json;
	}
	
	public static boolean verificaNome(String nome) {
		if(nome.length() < 3 || nome.length() > 32) {
			System.out.println("Insira um nome com tamanho entre 3 e 32 caracteres, por favor.");
			return false;
		}
		
		return true;		
	}
	
	public static boolean verificaEmail(String email) {
		boolean resultadoVerificacao = verificaArroba(email);
		
		if(!resultadoVerificacao) {
			System.out.println("E-mail não contém @, favor inserir um e-mail válido.");
			email = "";
		}
		
		if (email.length() < 16 || email.length() > 50) {
			System.out.println("Insira um e-mail com tamanho entre 16 e 50 caracteres, por favor.");			
			return false;
		}
		
		return true;		
	}
	
	public static boolean verificaSenha(String senha) {
		if(senha.length() < 8 || senha.length() > 32) {
			System.out.println("Insira uma senha com tamanho entre 8 e 32 caracteres, por favor.");
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
	
	public static String hashed(String pswd) {
    	
    	String hashed = "";
    	
        for (int i = 0; i < pswd.length(); i++) {
            char c = pswd.charAt(i);
            int asciiValue = (int) c;
            int novoAsciiValue = asciiValue + pswd.length();
            if (novoAsciiValue > 127) {
                novoAsciiValue = novoAsciiValue - 127 + 32;
            }
            char novoCaractere = (char) novoAsciiValue;
            hashed += novoCaractere;
        }
        return hashed;
    }
	
	public static String gerarString24Caracteres() {
		
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(24);
		
		for (int i = 0; i < 24; i++) {
			int index = (int)(caracteres.length() * Math.random());
			sb.append(caracteres.charAt(index));
		}
		
		return sb.toString();
	}
}