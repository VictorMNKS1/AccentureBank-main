package accenturebank.com.accentureBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import accenturebank.com.accentureBank.entities.Extrato;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.exceptions.ErrorModel;
import accenturebank.com.accentureBank.service.ExtratoService;

@RestController
public class ExtratoController {
	
	@Autowired
	ExtratoService extratoService;
	
	@GetMapping("/extrato")
    public ResponseEntity<List<Extrato>> getAllExtrato() {
        return new ResponseEntity<>(extratoService.getAllExtrato(), HttpStatus.OK);
    }
	
	@GetMapping("/extrato/{id}")
    public ResponseEntity<List<Extrato>> getAllExtratoById(@PathVariable("id") long id) {
        try {
            List<Extrato> extratoContaCorrente = extratoService.getAllExtratoporCliente(id);
            return new ResponseEntity<>(extratoContaCorrente, HttpStatus.OK);
        } catch (ContaCorrenteNotFoundException e) {
            return new ResponseEntity<List<Extrato>>(new ErrorModel(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<List<Extrato>>(new ErrorModel("Campo Inválido"), HttpStatus.NOT_FOUND);
        }
    }
	
	
}
    