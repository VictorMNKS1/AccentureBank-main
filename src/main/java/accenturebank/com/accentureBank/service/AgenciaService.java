package accenturebank.com.accentureBank.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.exceptions.DatabaseException;
import accenturebank.com.accentureBank.interfaces.AgenciaCRUD;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;

@Service
public class AgenciaService implements AgenciaCRUD {

	@Autowired
	AgenciaRepository agenciaRepository;

	public List<Agencia> getAllAgencia() {
		return agenciaRepository.findAll();

	}

	public Agencia getAgenciaById(long id) {
		Optional<Agencia> obj = agenciaRepository.findById(id);
		return obj.orElseThrow(() -> new AgenciaNotFoundException(id));
	}

	public Agencia save(Agencia obj) {
		validate(obj);
		return agenciaRepository.save(obj);
	}

	public void delete(long id) {
		try {
			agenciaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new AgenciaNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

	public Agencia update(long id, Agencia obj) {
		validate(obj);
		try {
			Agencia agencia = agenciaRepository.getById(id);
			updateData(agencia, obj);
			return agenciaRepository.save(agencia);
		} catch (EntityNotFoundException e) {
			throw new ClienteNotFoundException("Agencia nÃ£o encontrada");
		}

	}

	private void updateData(Agencia agencia, Agencia obj) {
		agencia.setNome(obj.getNome());
		agencia.setEndereco(obj.getEndereco());
		agencia.setTelefone(obj.getTelefone());
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