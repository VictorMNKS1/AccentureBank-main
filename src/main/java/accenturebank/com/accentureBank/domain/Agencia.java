package accenturebank.com.accentureBank.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import accenturebank.com.accentureBank.dto.AgenciaDTO;


@Entity
public class Agencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column
	private String endereco;

	@Column
	private String telefone;

	public Agencia() {
	}

	public Agencia(Long id, String nome, String endereco, String telefone) {
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;

	}


	public Agencia(AgenciaDTO agenciaDTO) {
		this.id = agenciaDTO.getId() ;
		this.nome = agenciaDTO.getNomeAgencia();
		this.endereco = agenciaDTO.getEnderecoAgencia();
		this.telefone = agenciaDTO.getFoneAgencia();
	}
	
	

	public Agencia(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agencia other = (Agencia) obj;
		return Objects.equals(id, other.id);
	}

}
