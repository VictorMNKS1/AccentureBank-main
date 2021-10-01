package accenturebank.com.accentureBank.interfaces;

public interface OperacoesConta {

	
	/*CRIAÇÃO DE INTERFACE PARA AS OPERAÇÕES
	* PRINCIPAIS DE UMA CONTA 
	*/
	
	public String sacar(Long id, double valorSaque);


	public String depositar(Long id, double valorDeposito);
		
	
	public String transferir(long idContaInicial, long idContaDestino, double valorTransferencia);
	
}
