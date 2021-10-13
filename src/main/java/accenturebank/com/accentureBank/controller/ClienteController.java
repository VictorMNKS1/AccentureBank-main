package accenturebank.com.accentureBank.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.service.ClienteService;


@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> getallClientes() {
		List<Cliente> list = clienteService.getAllCliente();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {
		Cliente obj = clienteService.getClienteById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable("id") long id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente obj) {
		obj = clienteService.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@PutMapping("/cliente/{id}")
    private ResponseEntity<Cliente> updateCliente(@RequestBody Cliente obj, @PathVariable("id") long id) {
        obj = clienteService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

}
