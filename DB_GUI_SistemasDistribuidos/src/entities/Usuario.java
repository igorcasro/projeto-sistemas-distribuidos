package entities;

public class Usuario {
	private Integer id_operacao = null;
	private String nome = null;
	private String email = null;
	private String senha = null;
	private String token = null;
	private Integer id_usuario = null;	

	public Usuario () {	

	}

	public Integer getId_operacao() {
		return id_operacao;
	}

	public void setId_operacao(Integer id_operacao) {
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

	@Override
	public String toString() {
		return "Usuario [id_operacao=" + id_operacao + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", token=" + token + ", id_usuario=" + id_usuario + "]";
	}
	
	
}