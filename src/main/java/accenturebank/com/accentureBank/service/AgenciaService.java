package accenturebank.com.accentureBank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;

@Service
public class AgenciaService {

	@Autowired
	AgenciaRepository agenciaRepository;

	public List<Agencia> getAllAgencia() {
		return agenciaRepository.findAll();

	}

	public Agencia getAgenciaById(long id) {
		Optional<Agencia> obj = agenciaRepository.findById(id);
		return obj.orElseThrow(() -> new AgenciaNotFoundException(id));
	}

	public Agencia save(Agencia obj) throws CampoObrigatorioEmptyException {
		validate(obj);
		return agenciaRepository.save(obj);

	}

	private void validate(Agencia agencia) {

		if (agencia.getNome() == null || agencia.getNome().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo nome é obrigatorio");
		}
		if (agencia.getEndereco() == null || agencia.getEndereco().isEmpty()) {
			throw new CampoObrigatorioEmptyException("O campo endereco é obrigatorio");
		}

		if (agencia.getTelefone() == null || agencia.getTelefone().isEmpty()) {
			throw new CampoObrigatorioEmptyException("o campo fone é obrigatorio");
		}
	}

}