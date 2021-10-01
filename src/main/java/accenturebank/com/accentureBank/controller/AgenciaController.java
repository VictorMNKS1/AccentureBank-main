package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.dto.AgenciaDTO;
import accenturebank.com.accentureBank.service.AgenciaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AgenciaController {
	@Autowired
	AgenciaService agenciaService;

	@GetMapping("/agencias")
	public ResponseEntity<List<Agencia>> getallAgencia() {
		return new ResponseEntity<>(agenciaService.getAllAgencia(), HttpStatus.OK);

	}

	@GetMapping("/agencia/{id}")
	public ResponseEntity<AgenciaDTO> getAgenciaById(@PathVariable("id") long id) {
			AgenciaDTO agencia = agenciaService.getAgenciaById(id);
			return new ResponseEntity<>(agencia, HttpStatus.OK);

	}

	@PostMapping("/agencia")
	public ResponseEntity<Agencia> saveAgencia(@RequestBody AgenciaDTO agenciaDTO) {
			Agencia agencia = agenciaService.saveOrUpdate(agenciaDTO);
			return new ResponseEntity<>(agencia, HttpStatus.OK);

	}

}