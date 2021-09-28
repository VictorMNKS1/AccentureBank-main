package accenturebank.com.accentureBank.model;

import java.io.Serializable;

public class AgenciaModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeAgencia;
    private String enderecoAgencia;
    private String foneAgencia;
    
    public AgenciaModel () {
    	
    }

	public AgenciaModel(Long id, String nomeAgencia, String enderecoAgencia, String foneAgencia) {
		super();
		this.id = id;
		this.nomeAgencia = nomeAgencia;
		this.enderecoAgencia = enderecoAgencia;
		this.foneAgencia = foneAgencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	public String getEnderecoAgencia() {
		return enderecoAgencia;
	}

	public void setEnderecoAgencia(String enderecoAgencia) {
		this.enderecoAgencia = enderecoAgencia;
	}

	public String getFoneAgencia() {
		return foneAgencia;
	}

	public void setFoneAgencia(String foneAgencia) {
		this.foneAgencia = foneAgencia;
	}

    
}
