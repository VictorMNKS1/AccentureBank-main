package accenturebank.com.accentureBank.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;


@Entity
public class Extrato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	private LocalDateTime dataHoraMovimento;
	
	private TipoDeOperacaoEnum operacao;
	
	@ManyToOne
	@JoinColumn(name = "conta_corrente_id")
	private ContaCorrente contaCorrente;
	
	
	public Extrato() {
		}

	public Extrato(Long id, LocalDateTime dataHoraMovimento, TipoDeOperacaoEnum operacao, ContaCorrente contaCorrente) {
		super();
		this.id = id;
		this.dataHoraMovimento = dataHoraMovimento;
		this.operacao = operacao;
		this.contaCorrente = contaCorrente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHoraMovimento() {
		return dataHoraMovimento;
	}

	public void setDataHoraMovimento(LocalDateTime dataHoraMovimento) {
		this.dataHoraMovimento = dataHoraMovimento;
	}

	public TipoDeOperacaoEnum getOperacao() {
		return operacao;
	}

	public void setOperacao(TipoDeOperacaoEnum operacao) {
		this.operacao = operacao;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
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
		Extrato other = (Extrato) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}