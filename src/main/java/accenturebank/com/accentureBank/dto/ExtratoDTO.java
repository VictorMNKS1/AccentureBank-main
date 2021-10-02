package accenturebank.com.accentureBank.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;
import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;

public class ExtratoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private double valorOperacao;
	private double saldo;
	private LocalDateTime dataHoraMovimento;
	private TipoDeOperacaoEnum operacao;
	private ContaCorrente contaCorrente;

	
	public ExtratoDTO() {
		
	}


	public ExtratoDTO(Extrato obj) {
		this.id = obj.getId();
		this.valorOperacao = obj.getValorOperacao();
		this.saldo = obj.getSaldo();
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

	public double getValorOperacao() {
		return valorOperacao;
	}


	public void setValorOperacao(double valorOperacao) {
		this.valorOperacao = valorOperacao;
	}


	public double getSaldo() {
		return saldo;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public ExtratoDTO(Long id, double valorOperacao, double saldo, LocalDateTime dataHoraMovimento,
			TipoDeOperacaoEnum operacao, ContaCorrente contaCorrente) {
		super();
		this.id = id;
		this.valorOperacao = valorOperacao;
		this.saldo = saldo;
		this.dataHoraMovimento = dataHoraMovimento;
		this.operacao = operacao;
		this.contaCorrente = contaCorrente;
	}
	
	
	
	
}
