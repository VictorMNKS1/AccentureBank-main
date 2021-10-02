package accenturebank.com.accentureBank.exceptions;

public class CpfInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CpfInvalidoException(String msg) {
        super(msg);
    }
}