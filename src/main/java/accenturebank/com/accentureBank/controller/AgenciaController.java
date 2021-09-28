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
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ErrorModel;
import accenturebank.com.accentureBank.service.AgenciaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class AgenciaController {
	@Autowired
	AgenciaService agenciaService;
	
	
	@GetMapping("/agencias")
	public ResponseEntity<List<Agencia>> getallAgencia(){
		return new ResponseEntity<>(agenciaService.getAllAgencia(), HttpStatus.OK);
		
	}
	
	@GetMapping("/agencia/{id}")
	public ResponseEntity<Agencia> getAgenciaById(@PathVariable("id") long id) {
		try {
			Agencia agencia = agenciaService.getAgenciaById(id);
			return new ResponseEntity<>(agencia, HttpStatus.OK);
		} catch (AgenciaNotFoundException e) {
			return new ResponseEntity<>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/agencia")
	
	public ResponseEntity<Agencia> saveAgencia(@RequestBody AgenciaDTO agenciaDTO){
		try {
			Agencia agencia = agenciaService.saveOrUpdate(agenciaDTO);
			return new ResponseEntity<>(agencia, HttpStatus.OK);
		}catch (CampoObrigatorioEmptyException e ) {
			return new ResponseEntity<>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorModel("Campo Obrigatorio Invalido"), HttpStatus.NOT_FOUND);
		}
	}
	
}
