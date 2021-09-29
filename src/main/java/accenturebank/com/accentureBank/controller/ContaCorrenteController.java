package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ErrorModel;
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
			return new ResponseEntity<>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorModel("Campo Invalido"), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/contacorrente")
	public ResponseEntity<ContaCorrente> saveContaCorrente(@RequestBody ContaCorrenteDTO contaCorrenteDTO) {
		try {
			ContaCorrente contaCorrente = contaCorrenteService.saveOrUpdate(contaCorrenteDTO);
			return new ResponseEntity<>(contaCorrente, HttpStatus.CREATED);
		} catch (ClienteNotFoundException | AgenciaNotFoundException e) {
			return new ResponseEntity<ContaCorrente>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<ContaCorrente>(new ErrorModel("Cliente já possui uma conta corrente"),
					HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/contacorrente/deposito")
	public ResponseEntity<String> Depositar(@RequestParam("id") long id, @RequestParam("valor") double valor) {
		try {
			String Depositar = contaCorrenteService.Depositar(id, valor);
			return new ResponseEntity<String>(Depositar, HttpStatus.OK);
		} catch (ContaCorrenteNotFoundException e) {
			return new ResponseEntity<String>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(new ErrorModel("Campo Inválido"), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/contacorrente/sacar")
	public ResponseEntity<String> sacar(@RequestParam("id") long id, @RequestParam("valor") double valor) {
		try {
			String sacar = contaCorrenteService.Saque(id, valor);
			return new ResponseEntity<>(sacar, HttpStatus.OK);
		} catch (ContaCorrenteNotFoundException e) {
			return new ResponseEntity<String>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(new ErrorModel("Campo Inválido"), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/contacorrente/transferir")
	public ResponseEntity<String> transferir(@RequestParam("idContaInicial") long idContaInicial,@RequestParam("idContaDestino") long idContaDestino,@RequestParam("valorTransferencia") double valorTransferencia) {
		try {
			String transferir = contaCorrenteService.transferencia(idContaInicial,idContaDestino, valorTransferencia);
			return new ResponseEntity<>(transferir,HttpStatus.OK);
		} catch (ContaCorrenteNotFoundException e) {
			return new ResponseEntity<String>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(new ErrorModel("Campo Inválido"), HttpStatus.NOT_FOUND);
		}

		
	}
}