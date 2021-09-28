package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.entities.ContaCorrente;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ErrorModel;
import accenturebank.com.accentureBank.model.ContaCorrenteModel;
import accenturebank.com.accentureBank.service.ContaCorrenteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ContaCorrenteController {

	@Autowired
	ContaCorrenteService contaCorrenteService;

	@GetMapping("/contascorrentes")
	public ResponseEntity<List<ContaCorrente>> getAllContasCorrentes() {
		return new ResponseEntity<>(contaCorrenteService.getAllContasCorrentes(), HttpStatus.OK);
	}

	@GetMapping("/contascorrentes/{id}")
    public ResponseEntity<ContaCorrente> getContaCorrente(@PathVariable("id") long id) {
        try {
            ContaCorrente contaCorrente = contaCorrenteService.getIdContaCorrente(id);
            return new ResponseEntity<>(contaCorrente, HttpStatus.OK);
        } catch (ContaCorrenteNotFoundException e) {
        	return new ResponseEntity<>( new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch( Exception e) {
        	return new ResponseEntity<>( new ErrorModel("Campo Invalido"), HttpStatus.NOT_FOUND);
        }
	}
        
    @PostMapping("/contacorrente")
	public ResponseEntity<ContaCorrente> saveContaCorrente(@RequestBody ContaCorrenteModel contaCorrenteModel)  {
		 try {
	            ContaCorrente contaCorrente = contaCorrenteService.saveOrUpdate(contaCorrenteModel);
	            return new ResponseEntity<>(contaCorrente, HttpStatus.CREATED);
	        } catch (ClienteNotFoundException | AgenciaNotFoundException e) {
	            return new ResponseEntity<ContaCorrente>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
	        } catch (Exception e) {
	            return new ResponseEntity<ContaCorrente>(new ErrorModel("Cliente já possui uma conta corrente"), HttpStatus.NOT_FOUND);
	        }
	}
    
	
	}

