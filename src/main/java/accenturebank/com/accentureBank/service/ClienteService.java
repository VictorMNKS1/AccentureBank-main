package accenturebank.com.accentureBank.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.exceptions.CpfInvalidoException;
import accenturebank.com.accentureBank.exceptions.DatabaseException;
import accenturebank.com.accentureBank.exceptions.ValidaCPF;
import accenturebank.com.accentureBank.interfaces.ClienteCRUD;
import accenturebank.com.accentureBank.repositories.ClienteRepository;

@Service
public class ClienteService implements ClienteCRUD {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	AgenciaService agenciaService;

	ValidaCPF validaCPF = new ValidaCPF();

	private void validate(Cliente cliente) {
	String cpf= cliente.getCpf();
		
		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo nome é obrigatorio");
		}
		if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo cpf é obrigatorio");
		}
		if (!validaCPF.isCPF(cpf)) {
			throw new CpfInvalidoException("CPF invalido");
		}
		if (cliente.getFone() == null || cliente.getFone().isEmpty()) {
			throw new CampoObrigatorioEmptyException("o campo fone é obrigatorio");
		}

	}

	// RETORNA TODOS OS CLIENTE
	public List<Cliente> getAllCliente() {
		return clienteRepository.findAll();
	}

	// RETORNA O CLIENTE PELO O ID
	public Cliente getClienteById(long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ClienteNotFoundException(id));
	}
	

	// CRIA UM NOVO CLIENTE
	public Cliente save(Cliente obj) {
		validate(obj);
		return clienteRepository.save(obj);
	
	}

	// DELETA UM CLIENTE PELO ID
	public void delete(long id) {	
		try {
			clienteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ClienteNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public Cliente update(long id, Cliente obj) {
		validate(obj);
		try {
			Cliente cliente = clienteRepository.getById(id);
			updateData(cliente, obj);
			return clienteRepository.save(cliente);
		} catch (EntityNotFoundException e) {
			throw new ClienteNotFoundException("Cliente nÃ£o encontrado");
		}
	}

	private void updateData(Cliente cliente, Cliente obj) {
		cliente.setNome(obj.getNome());
		cliente.setCpf(obj.getCpf());
		cliente.setFone(obj.getFone());
	}
	

}
