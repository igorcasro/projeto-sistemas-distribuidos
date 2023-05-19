package entities;

import java.util.Date;

public class Incidente {

	private Date data;
	private String rodovia;
	private Integer km;
	private enums.TipoIncidente TipoIncidente;
	private String token;
	private Integer id_usuario;
	private Integer id_incidente;
	
	public Incidente () {
		data = null;
		rodovia = null;
		km = null;
		TipoIncidente = null;
		token = null;
		id_usuario = null;
		id_incidente = null;
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