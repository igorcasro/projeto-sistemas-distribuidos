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
	
	public static String converteTipoIncidente(int tipo_incidente) {
		
		if(tipo_incidente == 1)
			return "Vento";
		if(tipo_incidente == 2)
			return "Chuva";
		if(tipo_incidente == 3)
			return "Nevoeiro";
		if(tipo_incidente == 4)
			return "Neve";
		if(tipo_incidente == 5)
			return "Gelo na Pista";
		if(tipo_incidente == 6)
			return "Granizo";
		if(tipo_incidente == 7)
			return "Trânsito parado";
		if(tipo_incidente == 8)
			return "Filas de trânsito";
		if(tipo_incidente == 9)
			return "Trânsito lento";
		if(tipo_incidente == 10)
			return "Acidente desconhecido";
		if(tipo_incidente == 11)
			return "Incidente desconhecido";
		if(tipo_incidente == 12)
			return "Trabalhos na estrada";
		if(tipo_incidente == 13)
			return "Bloqueio de pista";
		if(tipo_incidente == 14)
			return "Bloqueio de Estrada";
		
		return "NÃO RECONHECIDO";
	}

	@Override
	public String toString() {
		return "Id Incidente: " + this.getId_incidente() + " - Data: " + this.getData() + " - Rodovia: " + this.getRodovia() +
				" - KM: " + this.getKm() + " - Tipo Incidente: " + converteTipoIncidente(this.getTipo_incidente());
	}

	
}