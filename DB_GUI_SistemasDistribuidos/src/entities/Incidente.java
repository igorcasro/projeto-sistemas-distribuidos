package entities;

public class Incidente {
	private Integer id_operacao = null;
	private String data = null;
	private String rodovia = null;
	private Integer km = null;
	private Integer tipo_incidente = null;
	private String token = null;
	private Integer id_usuario = null;
	private Integer id_incidente = null;
	private String faixa_km = null;
	private Integer periodo = null;
	
	public Incidente () {
		
	}

	public Integer getId_operacao() {
		return id_operacao;
	}

	public void setId_operacao(Integer id_operacao) {
		this.id_operacao = id_operacao;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getRodovia() {
		return rodovia;
	}

	public void setRodovia(String rodovia) {
		this.rodovia = rodovia;
	}

	public Integer getKm() {
		return km;
	}

	public void setKm(Integer km) {
		this.km = km;
	}

	public Integer getTipo_incidente() {
		return tipo_incidente;
	}

	public void setTipo_incidente(Integer tipo_incidente) {
		this.tipo_incidente = tipo_incidente;
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

	public Integer getId_incidente() {
		return id_incidente;
	}

	public void setId_incidente(Integer id_incidente) {
		this.id_incidente = id_incidente;
	}

	public String getFaixa_km() {
		return faixa_km;
	}

	public void setFaixa_km(String faixa_km) {
		this.faixa_km = faixa_km;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	@Override
	public String toString() {
		return "Incidente [id_operacao=" + id_operacao + ", data=" + data + ", rodovia=" + rodovia + ", km=" + km
				+ ", tipo_incidente=" + tipo_incidente + ", token=" + token + ", id_usuario=" + id_usuario
				+ ", id_incidente=" + id_incidente + ", faixa_km=" + faixa_km + ", periodo=" + periodo + "]";
	}

	
}