package accenturebank.com.accentureBank.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.entities.Cliente;
import accenturebank.com.accentureBank.entities.ContaCorrente;
import accenturebank.com.accentureBank.entities.Extrato;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;
import accenturebank.com.accentureBank.repositories.ExtratoRepository;

@Service
public class ExtratoService {

	@Autowired
	ExtratoRepository extratoRepository;
	@Autowired
	ClienteService clienteService;
	@Autowired
	ContaCorrenteService contaCorrenteService;
	@Autowired
	ContaCorrenteRepository contaCorrenteRepository;

	public List<Extrato> getAllExtrato() {
		List<Extrato> extratoContaCorrente = new ArrayList<Extrato>();
		extratoRepository.findAll().forEach(extrato -> extratoContaCorrente.add(extrato));
		return extratoContaCorrente;
	}

	public List<Extrato> getAllExtratoporCliente(Long id) throws ContaCorrenteNotFoundException {
		Cliente cliente = clienteService.getClienteById(id);
		ContaCorrente contaCorrente = contaCorrenteService.getContaCorrenteByCliente(cliente);
		List<Extrato> extratoContaCorrenteId = extratoRepository.findByContaCorrente(contaCorrente);

        if (extratoContaCorrenteId.isEmpty()) {
            throw new ContaCorrenteNotFoundException("Extrato vazio.");
        }
        return extratoContaCorrenteId;
    }
		
	
	
}
