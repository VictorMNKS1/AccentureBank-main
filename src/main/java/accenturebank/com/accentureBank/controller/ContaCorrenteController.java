package accenturebank.com.accentureBank.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.service.ContaCorrenteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ContaCorrenteController {

	@Autowired
	ContaCorrenteService contaCorrenteService;

	@GetMapping("/contascorrentes")
	private ResponseEntity<List<ContaCorrente>> getAllContasCorrentes() {
		List<ContaCorrente> list = contaCorrenteService.getAllContasCorrentes();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/contacorrente/{id}")
	public ResponseEntity<ContaCorrente> getContaCorrente(@PathVariable("id") long id) {
		ContaCorrente obj = contaCorrenteService.getContaCorrenteById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/contacorrente")
	public ResponseEntity<ContaCorrente> saveContaCorrente(@RequestBody ContaCorrenteDTO obj) {
		
		ContaCorrente contaCorrente = contaCorrenteService.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(contaCorrente);

	}

	@PutMapping("/contacorrente/deposito")
	public ResponseEntity<String> depositar(@RequestParam("id") long id, @RequestParam("valor") double valor) {

		contaCorrenteService.getContaCorrenteById(id);
		String depositar = contaCorrenteService.depositar(id, valor);
		return ResponseEntity.ok().body(depositar);

	}

	@PutMapping("/contacorrente/sacar")
	public ResponseEntity<String> sacar(@RequestParam("id") long id, @RequestParam("valor") double valor) {

		contaCorrenteService.getContaCorrenteById(id);

		String sacar = contaCorrenteService.sacar(id, valor);
		return ResponseEntity.ok().body(sacar);

	}

	@PutMapping("/contacorrente/transferir")
	public ResponseEntity<String> transferir(@RequestParam("idContaInicial") long idContaInicial,@RequestParam("idContaDestino") long idContaDestino, @RequestParam("valorTransferencia") double valorTransferencia) {
		contaCorrenteService.getContaCorrenteById(idContaInicial);

		String transferir = contaCorrenteService.transferir(idContaInicial, idContaDestino, valorTransferencia);
		return ResponseEntity.ok().body(transferir);

	}

	@GetMapping("/contacorrente/saldo/{id}")
	public ResponseEntity<Double> saldo(@PathVariable("id") long id) {
		contaCorrenteService.getContaCorrenteById(id);

		double saldo = contaCorrenteService.getSaldoContaCorrenteByIdCliente(id);
		return ResponseEntity.ok().body(saldo);

	}

	@GetMapping("/recalcularsaldocontacorrente/{id}")
	public ResponseEntity<String> recalcularSaldo(@PathVariable("id") long id) {
		return new ResponseEntity<String>(contaCorrenteService.recalcularSaldo(id), HttpStatus.OK);
	}

	@DeleteMapping("/contacorrente/{id}")
	public ResponseEntity<Boolean> deleteContaCorrente(@PathVariable("id") long id) {
		contaCorrenteService.getContaCorrenteById(id);

		Boolean delete = contaCorrenteService.deleteContaCorrente(id);
		return ResponseEntity.ok().body(delete);
	}

}