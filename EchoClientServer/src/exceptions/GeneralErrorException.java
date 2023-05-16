package exceptions;

public class GeneralErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public GeneralErrorException(String mensagem) {
		
		super(mensagem);
	}
}
