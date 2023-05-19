package entities;

import java.util.Date;
import java.util.List;

public class Retorno {

	private Integer codigo = null;
	private String token = null;
	private Integer id_usuario = null;
	private List<Incidente> lista_incidentes = null;
	private Date data = null;
	private String rodovia = null;
	private Integer km = null;
	private enums.TipoIncidente TipoIncidente = null;
	private Integer id_incidente = null;
	private String mensagem = null;
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public List<Incidente> getLista_incidentes() {
		return lista_incidentes;
	}
	public void setLista_incidentes(List<Incidente> lista_incidentes) {
		this.lista_incidentes = lista_incidentes;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
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
	public enums.TipoIncidente getTipoIncidente() {
		return TipoIncidente;
	}
	public void setTipoIncidente(enums.TipoIncidente tipoIncidente) {
		TipoIncidente = tipoIncidente;
	}
	public Integer getId_incidente() {
		return id_incidente;
	}
	public void setId_incidente(Integer id_incidente) {
		this.id_incidente = id_incidente;
	}
	
	@Override
	public String toString() {
		return "Retorno [codigo=" + codigo + ", token=" + token + ", id_usuario=" + id_usuario + "]";
	}
	
	
}
