package accenturebank.com.accentureBank.exceptions;


public class ClienteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(Object id) {
		super("Cliente não encontrado Id " + id);
	 
		
	}
}