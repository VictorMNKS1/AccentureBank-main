package accenturebank.com.accentureBank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.dto.AgenciaDTO;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;

@Service
public class AgenciaService {

	@Autowired
	AgenciaRepository agenciaRepository;

	public AgenciaDTO getAgenciaById(long id) {
		Optional<Agencia> agenciaRetorno = agenciaRepository.findById(id);
		if (agenciaRetorno.isEmpty()) {
			throw new AgenciaNotFoundException("Agencia não encontrada");
		}
		return new AgenciaDTO(agenciaRetorno.get());

	}

	public List<Agencia> getAllAgencia() {
		List<Agencia> agencias = new ArrayList<>();
		agenciaRepository.findAll().forEach(agencia -> agencias.add(agencia));

		return agencias;
	}

	public Agencia saveOrUpdate(AgenciaDTO agenciaDTO) throws CampoObrigatorioEmptyException {
		Agencia agencia = new Agencia(null, agenciaDTO.getNomeAgencia(), agenciaDTO.getEnderecoAgencia(),
				agenciaDTO.getFoneAgencia());

		if (agenciaDTO.getNomeAgencia().isEmpty() || agenciaDTO.getEnderecoAgencia().isEmpty()
				|| agenciaDTO.getFoneAgencia().isEmpty()) {
			throw new CampoObrigatorioEmptyException("Campo obrigatório vazio.");
		}

		agencia = agenciaRepository.save(agencia);

		return agencia;

	}

}