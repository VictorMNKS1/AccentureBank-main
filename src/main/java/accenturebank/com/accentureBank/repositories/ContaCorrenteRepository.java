package accenturebank.com.accentureBank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import accenturebank.com.accentureBank.entities.Cliente;
import accenturebank.com.accentureBank.entities.ContaCorrente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {
	ContaCorrente findByCliente(Cliente cliente);
	
}