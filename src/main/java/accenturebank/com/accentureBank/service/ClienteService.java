package accenturebank.com.accentureBank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.entities.Cliente;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.model.ClienteModel;
import accenturebank.com.accentureBank.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
    AgenciaService agenciaService;

	// RETORNA TODOS OS CLIENTE
	public List<Cliente> getAllCliente() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clienteRepository.findAll().forEach(cliente -> clientes.add(cliente));
		return clientes;
	}
	
    //RETORNA O CLIENTE PELO O ID
    public Cliente getClienteById(long id) throws ClienteNotFoundException {
        Optional<Cliente> clienteRetorno = clienteRepository.findById(id);
        if (clienteRetorno.isEmpty()) {
            throw new ClienteNotFoundException("Cliente não encontrado.");
        }
        return clienteRetorno.get();
    }
    
    //CRIA UM NOVO CLIENTE
    public Cliente saveOrUpdate(ClienteModel clienteModel) throws AgenciaNotFoundException {
    	Cliente cliente = new Cliente(null,clienteModel.getNome(),clienteModel.getCpf(),clienteModel.getFone());
    	
    	if (cliente.getNome().isEmpty() || cliente.getCpf().isEmpty() || cliente.getFone().isEmpty()) {
            throw new CampoObrigatorioEmptyException("Campo obrigatório vazio.");
        }

        Cliente clienteRetorno = clienteRepository.save(cliente);

       
        return clienteRetorno;
    }
    
    //DELETA UM CLIENTE PELO ID
    public Boolean delete(long id) throws ClienteNotFoundException {
        Optional<Cliente> clienteRetorno = clienteRepository.findById(id);
        if (clienteRetorno.isEmpty()) {
            throw new ClienteNotFoundException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);

        return true;

}}
