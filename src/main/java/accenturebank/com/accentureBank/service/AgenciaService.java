package accenturebank.com.accentureBank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.entities.Agencia;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.model.AgenciaModel;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;

@Service
public class AgenciaService {

	@Autowired
	AgenciaRepository agenciaRepository;

	public Agencia getAgenciaById(long id) {
		Optional<Agencia> agenciaRetorno = agenciaRepository.findById(id);
		if (agenciaRetorno.isEmpty()) {
			throw new AgenciaNotFoundException("Agencia não encontrada");
		}
		return agenciaRetorno.get();
	}

	public List<Agencia> getAllAgencia() {
		List<Agencia> agencias = new ArrayList<>();
		agenciaRepository.findAll().forEach(agencia -> agencias.add(agencia));

		return agencias;
	}
	
	public Agencia saveOrUpdate(AgenciaModel agenciaModel) throws CampoObrigatorioEmptyException {
		Agencia agencia = new Agencia(null, agenciaModel.getNomeAgencia(), agenciaModel.getEnderecoAgencia(), agenciaModel.getFoneAgencia());
		
		if (agenciaModel.getNomeAgencia().isEmpty() || agenciaModel.getEnderecoAgencia().isEmpty() || agenciaModel.getFoneAgencia().isEmpty()) {
            throw new CampoObrigatorioEmptyException("Campo obrigatório vazio.");
        }
		
		agencia = agenciaRepository.save(agencia);
		
		return agencia;
		
	}
	
	
			
}
