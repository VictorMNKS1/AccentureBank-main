package accenturebank.com.accentureBank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import accenturebank.com.accentureBank.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	
}