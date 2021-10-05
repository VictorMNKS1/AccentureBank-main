package accenturebank.com.accentureBank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.interfaces.ExtratoCRUD;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;
import accenturebank.com.accentureBank.repositories.ExtratoRepository;

@Service
public class ExtratoService implements ExtratoCRUD {

	@Autowired
	ExtratoRepository extratoRepository;
	@Autowired
	ClienteService clienteService;
	@Autowired
	ContaCorrenteService contaCorrenteService;
	@Autowired
	ContaCorrenteRepository contaCorrenteRepository;

	public List<Extrato> getAllExtrato() {
		return extratoRepository.findAll();
	}

	public List<Extrato> getAllExtratoporCliente(Long id) {
		Cliente cliente = clienteService.getClienteById(id);
		ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteByCliente(cliente);
		List<Extrato> extratoContaCorrenteId = extratoRepository.findByContaCorrente(contaCorrente);

		if (extratoContaCorrenteId.isEmpty() || extratoContaCorrenteId == null) {
			throw new ContaCorrenteNotFoundException("Extrato vazio.");
		} 
		return extratoContaCorrenteId;
	}

}
