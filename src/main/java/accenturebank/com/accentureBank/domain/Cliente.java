package accenturebank.com.accentureBank.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import accenturebank.com.accentureBank.dto.ClienteDTO;

@JsonSerialize
@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column

	private String cpf;

	@Column
	private String fone;

	public Cliente() {
	}

	public Cliente(Long id, String nome, String cpf, String fone) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.fone = fone;

	}

	public Cliente(ClienteDTO clienteDTO) {
		this.id = clienteDTO.getId();
		this.nome = clienteDTO.getNome();
		this.cpf = clienteDTO.getCpf();
		this.fone = clienteDTO.getFone();
	}


	public Cliente(Long id) {
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
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
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

}