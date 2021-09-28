package accenturebank.com.accentureBank.exceptions;

public class ContaCorrenteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ContaCorrenteNotFoundException(String msg) {
        super(msg);
    }
}