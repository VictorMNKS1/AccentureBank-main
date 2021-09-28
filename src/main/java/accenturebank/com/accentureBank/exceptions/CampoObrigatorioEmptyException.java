package accenturebank.com.accentureBank.exceptions;

public class CampoObrigatorioEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CampoObrigatorioEmptyException(String msg) {
        super(msg);
    }
}