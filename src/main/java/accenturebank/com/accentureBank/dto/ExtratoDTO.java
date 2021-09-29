package accenturebank.com.accentureBank.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;
import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;

public class ExtratoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private LocalDateTime dataHoraMovimento;
	private TipoDeOperacaoEnum operacao;
	private ContaCorrente contaCorrente;

	
	public ExtratoDTO() {
		
	}


	public ExtratoDTO(Extrato obj) {
		this.id = obj.getId();
		this.dataHoraMovimento = obj.getDataHoraMovimento();
		this.operacao = obj.getOperacao();
		this.contaCorrente = obj.getContaCorrente();
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
	
	
}
