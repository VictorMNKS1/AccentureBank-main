package accenturebank.com.accentureBank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.dto.ClienteDTO;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	AgenciaService agenciaService;

	private void validate(ClienteDTO cliente) {
		if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo nome é obrigatorio");
		}
		if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo cpf é obrigatorio");
		}
		if (cliente.getFone() == null || cliente.getFone().isEmpty()) {
			throw new CampoObrigatorioEmptyException("o campo fone é obrigatorio");
		}

	}

	// RETORNA TODOS OS CLIENTE
	public List<Cliente> getAllCliente() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clienteRepository.findAll().forEach(cliente -> clientes.add(cliente));
		return clientes;
	}

	// RETORNA O CLIENTE PELO O ID
	public Cliente getClienteById(long id) {
		Optional<Cliente> clienteRetorno = clienteRepository.findById(id);
		if (clienteRetorno.isEmpty()) {
			throw new ClienteNotFoundException("CLIENTE NÃO ENCONTRADO");
		}
		return clienteRetorno.get();
	}

	// CRIA UM NOVO CLIENTE
	public Cliente save(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getCpf(),
				clienteDTO.getFone());

		validate(clienteDTO);
		Cliente clienteRetorno = clienteRepository.save(cliente);

		return clienteRetorno;
	}

	// DELETA UM CLIENTE PELO ID
	public Boolean delete(long id) {
		clienteRepository.deleteById(id);
		return true;

	}

	public Cliente update(long id, ClienteDTO clientee) {
		validate(clientee);
		try {
			Cliente cliente = clienteRepository.getById(id);
			updateData(cliente, clientee);
			return clienteRepository.save(cliente);
		} catch (EntityNotFoundException e) {
			throw new ClienteNotFoundException("Cliente nÃ£o encontrado");
		}
	}

	private void updateData(Cliente cliente, ClienteDTO novo) {
		cliente.setNome(novo.getNome());
		cliente.setCpf(novo.getCpf());
		cliente.setFone(novo.getFone());
	}
}
