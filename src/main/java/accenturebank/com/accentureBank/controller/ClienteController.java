package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.dto.ClienteDTO;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ClienteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ErrorModel;
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
        try {
            Cliente cliente = clienteService.getClienteById(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<Cliente>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
	
	//DELETA O CLIENTE PELO ID
	@DeleteMapping("/cliente/{id}")
    public ResponseEntity<Integer> deleteCliente(@PathVariable("id") long id) {
        try {
            clienteService.delete(id);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<Integer>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

	// CRIACAO DO MAPEAMENTO DE POST QUE PUBLICA OS DETALHES DO CLIENTE NO BANCO DE DADOS
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.saveOrUpdate(clienteDTO);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (AgenciaNotFoundException e) {
            return new ResponseEntity<Cliente>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (CampoObrigatorioEmptyException e) {
            return new ResponseEntity<Cliente>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Cliente>(new ErrorModel("Campo Obrigatório Inválido"), HttpStatus.NOT_FOUND);
        }

    }
	

}
