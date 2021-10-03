package accenturebank.com.accentureBank.interfaces;

public interface OperacoesConta {

	
	/*CRIAÇÃO DE INTERFACE PARA AS OPERAÇÕES
	* PRINCIPAIS DE UMA CONTA 
	*/
	
	public Double sacar(Long id, double valorSaque);


	public Double depositar(Long id, double valorDeposito);
		
	
	public Double transferir(long idContaInicial, long idContaDestino, double valorTransferencia);
	
}
