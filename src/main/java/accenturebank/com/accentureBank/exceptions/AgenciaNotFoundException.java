package accenturebank.com.accentureBank.exceptions;


public class AgenciaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AgenciaNotFoundException(Object id) {
		super("Agencia não encontrada Id " + id);
	 
		
	}
}