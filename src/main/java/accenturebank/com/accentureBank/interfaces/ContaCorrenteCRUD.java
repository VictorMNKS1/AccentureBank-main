package accenturebank.com.accentureBank.interfaces;

import java.util.List;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;

public interface ContaCorrenteCRUD {

	
	/*CRIAÇÃO DE INTERFACE PARA AS OPERAÇÕES
	* PRINCIPAIS DE UMA CONTA CORRENTE
	*/
	
	public List<ContaCorrente> getAllContasCorrentes();
	
	public ContaCorrente getContaCorrenteById(Long id);
	
	public void operacaoContaCorrente(long id, double resultadoOperacao, double valorOperacao,
			TipoDeOperacaoEnum operacao);


	public double getSaldoContaCorrenteByIdCliente(long id);
		
	
	public ContaCorrente save(ContaCorrenteDTO ContaCorrenteDTO);
	
	public void delete(long id);
	
	public ContaCorrente getContaCorrenteByCliente(Cliente cliente);
	
	public Double recalcularSaldo(long id);
	
	public Double sacar(Long id, double valorSaque);


	public Double depositar(Long id, double valorDeposito);
		
	
	public Double transferir(long idContaInicial, long idContaDestino, double valorTransferencia);
	
}
