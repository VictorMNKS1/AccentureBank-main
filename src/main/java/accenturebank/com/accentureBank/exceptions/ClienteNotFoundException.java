package accenturebank.com.accentureBank.exceptions;

public class ClienteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(String msg) {
        super(msg);
    }
}