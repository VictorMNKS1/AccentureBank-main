package accenturebank.com.accentureBank.exceptions;

public class AgenciaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AgenciaNotFoundException(String msg) {
        super(msg);
    }
}