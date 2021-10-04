package accenturebank.com.accentureBank.interfaces;

import java.util.List;

import accenturebank.com.accentureBank.domain.Extrato;

public interface ExtratoCRUD {

	
	/*CRIAÇÃO DE INTERFACE PARA AS OPERAÇÕES
	* PRINCIPAIS DE UM EXTRATO 
	*/
	
	public List<Extrato> getAllExtrato();

	public List<Extrato> getAllExtratoporCliente(Long id);

	
	
}
