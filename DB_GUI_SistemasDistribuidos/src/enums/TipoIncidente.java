package enums;

public enum TipoIncidente {
	VENTO("Vento"),
	CHUVA("Chuva"),
	NEVOEIRO("Nevoeiro"),
	NEVE("Neve"),
	GELO_NA_PISTA("Gelo na Pista"),
	GRANIZO("Granizo"),
	TRANSITO_PARADO("Trânsito parado"),
	FILAS_DE_TRANSITO("Filas de trânsito"),
	TRANSITO_LENTO("Trânsito lento"),
	ACIDENTE_DESCONHECIDO("Acidente desconhecido"),
	INCIDENTE_DESCONHECIDO("Incidente desconhecido"),
	TRABALHOS_NA_ESTRADA("Trabalhos na estrada"),
	BLOQUEIO_DE_PISTA("Bloqueio de pista"),
	BLOQUEIO_DE_ESTRADA("Bloqueio de Estrada");
	
	private String displayName;
	
	TipoIncidente(String displayName){
		this.displayName = displayName;
	}
	
	public String displayName() {
		return displayName;
	}
	
	public static String[] getNomes() {
		String[] nomes = new String[TipoIncidente.values().length];
		for (TipoIncidente tipoIncidenteNome : TipoIncidente.values()) {
			nomes[tipoIncidenteNome.ordinal()] = tipoIncidenteNome.displayName();
		}
		return nomes;
	}
}