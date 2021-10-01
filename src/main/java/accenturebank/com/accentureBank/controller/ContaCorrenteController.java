package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.service.ContaCorrenteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ContaCorrenteController {

	@Autowired
	ContaCorrenteService contaCorrenteService;

	@GetMapping("/contascorrentes")
	private ResponseEntity<List<ContaCorrente>> getAllContasCorrentes() {
		List<ContaCorrente> contaCorrenteDtos = contaCorrenteService.getAllContasCorrentes();
		if (contaCorrenteDtos == null) {
			throw new ContaCorrenteNotFoundException("No record found");
		}
		return new ResponseEntity<List<ContaCorrente>>(contaCorrenteDtos, HttpStatus.OK);
	}

	@GetMapping("/contacorrente/{id}")
	public ResponseEntity<ContaCorrente> getContaCorrente(@PathVariable("id") long id) {

		ContaCorrente contaCorrente = contaCorrenteService.getIdContaCorrente(id);
		return new ResponseEntity<>(contaCorrente, HttpStatus.OK);
	}

	@PostMapping("/contacorrente")
	public ResponseEntity<ContaCorrente> saveContaCorrente(@RequestBody ContaCorrenteDTO contaCorrenteDTO) {

		ContaCorrente contaCorrente = contaCorrenteService.save(contaCorrenteDTO);
		return new ResponseEntity<>(contaCorrente, HttpStatus.CREATED);

	}

	@PutMapping("/contacorrente/deposito")
	public ResponseEntity<String> depositar(@RequestParam("id") long id, @RequestParam("valor") double valor) {

		contaCorrenteService.getIdContaCorrente(id);

		String Depositar = contaCorrenteService.depositar(id, valor);
		return new ResponseEntity<String>(Depositar, HttpStatus.OK);

	}

	@PutMapping("/contacorrente/sacar")
	public ResponseEntity<String> sacar(@RequestParam("id") long id, @RequestParam("valor") double valor) {

		contaCorrenteService.getIdContaCorrente(id);

		String sacar = contaCorrenteService.sacar(id, valor);
		return new ResponseEntity<>(sacar, HttpStatus.OK);

	}

	@PutMapping("/contacorrente/transferir")
	public ResponseEntity<String> transferir(@RequestParam("idContaInicial") long idContaInicial,
			@RequestParam("idContaDestino") long idContaDestino,
			@RequestParam("valorTransferencia") double valorTransferencia) {
		contaCorrenteService.getIdContaCorrente(idContaInicial);

		String transferir = contaCorrenteService.transferir(idContaInicial, idContaDestino, valorTransferencia);
		return new ResponseEntity<>(transferir, HttpStatus.OK);

	}

	@GetMapping("/contacorrente/saldo/{id}")
	public ResponseEntity<Double> saldo(@PathVariable("id") long id) {
		contaCorrenteService.getIdContaCorrente(id);

		double saldo = contaCorrenteService.getSaldoContaCorrenteByIdCliente(id);
		return new ResponseEntity<Double>(saldo, HttpStatus.OK);

	}

	@GetMapping("/recalcularsaldocontacorrente/{id}")
	public ResponseEntity<String> recalcularSaldo(@PathVariable("id") long id) {
		return new ResponseEntity<String>(contaCorrenteService.recalcularSaldo(id), HttpStatus.OK);
	}

	@DeleteMapping("/contacorrente/{id}")
	public ResponseEntity<Boolean> deleteContaCorrente(@PathVariable("id") long id) {
		contaCorrenteService.getIdContaCorrente(id);

		Boolean delete = contaCorrenteService.deleteContaCorrente(id);
		return new ResponseEntity<Boolean>(delete, HttpStatus.OK);
	}

}