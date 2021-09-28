package accenturebank.com.accentureBank.model;

import java.io.Serializable;

public class ContaCorrenteModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private IdAgenciaModel idAgencia;

	private IdClienteModel idCliente;

	public ContaCorrenteModel() {

	}

	public ContaCorrenteModel(IdAgenciaModel idAgencia, IdClienteModel idCliente) {
		super();
		this.idAgencia = idAgencia;
		this.idCliente = idCliente;

	}

	public IdAgenciaModel getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(IdAgenciaModel idAgencia) {
		this.idAgencia = idAgencia;
	}

	public IdClienteModel getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(IdClienteModel idCliente) {
		this.idCliente = idCliente;
	}

}
