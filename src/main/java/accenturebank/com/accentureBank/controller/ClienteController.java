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

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.dto.ClienteDTO;
import accenturebank.com.accentureBank.service.ClienteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;
	
	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> getallClientes(){
		return new ResponseEntity<>(clienteService.getAllCliente(), HttpStatus.OK);
		}
	
	@GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable("id") long id) {
            Cliente cliente = clienteService.getClienteById(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
 
    }
	
	//DELETA O CLIENTE PELO ID
	@DeleteMapping("/cliente/{id}")
    public ResponseEntity<Boolean> deleteCliente(@PathVariable("id") long id) {
		clienteService.getClienteById(id);

		Boolean delete = clienteService.delete(id);
		return new ResponseEntity<Boolean>(delete, HttpStatus.OK);
	}

	// CRIACAO DO MAPEAMENTO DE POST QUE PUBLICA OS DETALHES DO CLIENTE NO BANCO DE DADOS
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO clienteDTO) {
            Cliente cliente = clienteService.save(clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
	
	@PutMapping("/clientes")
	private ResponseEntity<Cliente> updateCliente(@RequestBody ClienteDTO clienteDTO, @RequestParam("id") long id) 
	{
		Cliente cliente = clienteService.update(id, clienteDTO);
		return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
	}

}
