package accenturebank.com.accentureBank.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import accenturebank.com.accentureBank.domain.Agencia;

public class AgenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonProperty("Nome")
	private String nomeAgencia;
	@JsonProperty("Endereço")
	private String enderecoAgencia;
	@JsonProperty("Fone")
	private String foneAgencia;

	public AgenciaDTO() {

	}

	public AgenciaDTO(Agencia obj) {
		this.id = obj.getId();
		this.nomeAgencia = obj.getNome();
		this.enderecoAgencia = obj.getEndereco();
		this.foneAgencia = obj.getTelefone();
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
