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

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.service.AgenciaService;

@RestController
public class AgenciaController {
	@Autowired
	private AgenciaService agenciaService;

	@GetMapping("/agencia")
	public ResponseEntity<List<Agencia>> getallAgencias() {
		List<Agencia> list = agenciaService.getAllAgencia();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/agencia/{id}")
	public ResponseEntity<Agencia> findById(@PathVariable Long id) {
		Agencia obj = agenciaService.getAgenciaById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping("/agencia")
	public ResponseEntity<Agencia> saveAgencia(@RequestBody Agencia obj) {
		obj = agenciaService.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping("/agencia/{id}")
	public ResponseEntity<Agencia> update(@RequestBody Agencia obj, @PathVariable Long id) {
		obj = agenciaService.update(id, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping("/agencia/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		agenciaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	
		

}

