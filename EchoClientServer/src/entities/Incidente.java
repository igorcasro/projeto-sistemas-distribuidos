package entities;

import java.util.Date;

public class Incidente {

	private Date data = null;
	private String rodovia = null;
	private Integer km = null;
	private enums.TipoIncidente TipoIncidente = null;
	private String token = null;
	private Integer id_usuario = null;
	private Integer id_incidente = null;
	//Falta id_incidente: dúvida sobre aparecimento dele.
	
	public Incidente () {
		
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

	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public enums.TipoIncidente getTipoIncidente() {
		return TipoIncidente;
	}

	public void setTipoIncidente(enums.TipoIncidente tipoIncidente) {
		TipoIncidente = tipoIncidente;
	}

	public String getToken() {
		return token;
	}

	public int getId_usuario() {
		return id_usuario;
	}
	
	public Integer getId_incidente() {
		return id_incidente;
	}

	public void setId_incidente(Integer id_incidente) {
		this.id_incidente = id_incidente;
	}

	@Override
	public String toString() {
		return "Incidente [data=" + data + ", rodovia=" + rodovia + ", km=" + km + ", TipoIncidente=" + TipoIncidente
				+ ", token=" + token + ", id_usuario=" + id_usuario + ", id_incidente=" + id_incidente + "]";
	}	
	
}