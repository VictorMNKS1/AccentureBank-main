package accenturebank.com.accentureBank.interfaces;

import java.util.List;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;

public interface ContaCorrenteCRUD {

	
	/*CRIAÇÃO DE INTERFACE PARA AS OPERAÇÕES
	* PRINCIPAIS DE UMA CONTA CORRENTE
	*/
	
	public List<ContaCorrente> getAllContaCorrente();


	public ContaCorrente getContaCorrenteById(long id);
		
	
	public ContaCorrente save(ContaCorrenteDTO ContaCorrenteDTO);
	
	public void delete(long id);
	
	public ContaCorrente getContaCorrenteByCliente(Cliente cliente);
	
	public Double sacar(Long id, double valorSaque);


	public Double depositar(Long id, double valorDeposito);
		
	
	public Double transferir(long idContaInicial, long idContaDestino, double valorTransferencia);
	
}
