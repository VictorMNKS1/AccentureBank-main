package accenturebank.com.accentureBank.dto;

import java.io.Serializable;

import accenturebank.com.accentureBank.domain.Agencia;

public class AgenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//private Long id;
	private String nomeAgencia;
    private String enderecoAgencia;
    private String foneAgencia;
    
    public AgenciaDTO () {
    	
    }

	public AgenciaDTO(Agencia obj) {
		super();
		//id = obj.getId();
		nomeAgencia = obj.getNome();
		enderecoAgencia = obj.getEndereco();
		foneAgencia = obj.getTelefone();
	}

	//public Long getId() {
		//return id;
	//}

	//public void setId(Long id) {
		//this.id = id;
	//}

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
